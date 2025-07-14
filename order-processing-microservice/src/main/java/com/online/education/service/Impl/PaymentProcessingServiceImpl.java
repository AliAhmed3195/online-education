package com.online.education.service.Impl;

import com.online.education.Repository.TradeFlowUserRepository;
import com.online.education.constant.OrderStatus;
import com.online.education.constant.PaymentStatus;
import com.online.education.entity.Order;
import com.online.education.entity.Payment;
import com.online.education.entity.TradeFlowUser;
import com.online.education.repository.OrderRepository;
import com.online.education.repository.PaymentRepository;
import com.online.education.request.*;
import com.online.education.response.GenericResponse;
import com.online.education.response.PaginatedResponseDTO;
import com.online.education.service.PaymentProcessingService;
import com.online.education.util.SpecificationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class PaymentProcessingServiceImpl implements PaymentProcessingService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TradeFlowUserRepository tradeFlowUserRepository;

    private static final String INVALID_REQUEST = "invalid.request";
    private static final String CREATE_PAYMENT_REQUEST_SUCCESS = "create.payment.request.success";
    private static final String PAYMENT_SUCCESSFULLY_FETCH = "payment.fetch.success";
    private static final String PAYMENT = "PAYMENT";

    @Autowired
    private Environment environment;

    private TradeFlowUser getPrincipal() {
        return (TradeFlowUser) SecurityContextHolder.getContext().getAuthentication();
    }

    public GenericResponse processPayment(@RequestBody  PaymentRequestDTO paymentRequestDTO) {
        // Fetch the order by ID
        Order order = orderRepository.findById(paymentRequestDTO.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + paymentRequestDTO.getOrderId()));
        Payment payment = new Payment();
        // Check if payment amount matches the order total
        if (!order.getTotalPrice().equals(paymentRequestDTO.getAmount())) {
            throw new IllegalArgumentException("Payment amount does not match the order total.");
        } else {

        }
        payment.setAmount( paymentRequestDTO.getAmount());
        payment.setCustomerId( paymentRequestDTO.getCustomerId());
        payment.setStatus(PaymentStatus.PAYMENT_SUCCESSFUL);

        // Save the payment in the database
        paymentRepository.save(payment);

        // Update order status to PAID
        order.setStatus(OrderStatus.PAYMENT_SUCCESSFUL);
        orderRepository.save(order);
        return GenericResponse.createSuccessResponse(environment.getProperty(CREATE_PAYMENT_REQUEST_SUCCESS));
    }

    @Override
    public GenericResponse fetchPayments(@RequestBody PaymentSearchRequest paymentSearchRequest) {
        Specification<Payment> specification = commonSearchPaymentSpecification(paymentSearchRequest);
        Page<Payment> page = paymentRepository.findAll(specification, PageRequest.of(paymentSearchRequest.getPageNumber()<=0 ? 0 : paymentSearchRequest.getPageNumber()-1,
                paymentSearchRequest.getPageSize()<=0 ? 10 : paymentSearchRequest.getPageSize(),
                Sort.Direction.DESC, "id"));

        return GenericResponse.createSuccessResponse(
                environment.getProperty(PAYMENT_SUCCESSFULLY_FETCH), "payments",
                new PaginatedResponseDTO(page.getContent(), page.getTotalElements()));
    }

    @Override
    public GenericResponse fetchPaymentById(@RequestBody PaymentIdRequest paymentIdRequest){
        Optional<Payment> payment = paymentRepository.findById( paymentIdRequest.getId() );
        if( payment.isPresent() ){
            return GenericResponse.createSuccessResponse(
                    environment.getProperty(PAYMENT_SUCCESSFULLY_FETCH),PAYMENT,payment);
        } else {
            return GenericResponse.createSuccessResponse(environment.getProperty(PAYMENT_SUCCESSFULLY_FETCH));
        }
    }


    private static Specification<Payment> commonSearchPaymentSpecification( PaymentSearchRequest paymentSearchRequest) {
        Specification<Payment> specification =
                SpecificationUtility.equalsValue("isActive", true);
        if( paymentSearchRequest.getOrderNumber() != null ){
            specification = specification.and(SpecificationUtility.equalsValue("orderNumber", paymentSearchRequest.getOrderNumber()));
        }
        if (paymentSearchRequest.getCustomerId() != null) {
            specification = specification.and(SpecificationUtility.equalsValue("customerId", paymentSearchRequest.getCustomerId()));
        }
        return specification;
    }
}
