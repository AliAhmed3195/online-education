package com.online.education.service.Impl;

import com.online.education.Repository.TradeFlowUserRepository;
import com.online.education.constant.OrderStatus;
import com.online.education.entity.Order;
import com.online.education.entity.OrderItem;
import com.online.education.entity.TradeFlowUser;
import com.online.education.repository.OrderRepository;
import com.online.education.request.OrderIdRequest;
import com.online.education.request.OrderRequestDTO;
import com.online.education.request.OrderSearchRequest;
import com.online.education.response.GenericResponse;
import com.online.education.response.PaginatedResponseDTO;
import com.online.education.service.OrderProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderProcessingServiceImpl implements OrderProcessingService {

    private static final String INVALID_REQUEST = "invalid.request";
    private static final String CREATE_ORDER_REQUEST_SUCCESS = "create.order.request.success";
    private static final String ORDER_SUCCESSFULLY_FETCH = "order.fetch.success";
    private static final String ORDER = "order";

    @Autowired
    private Environment environment;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TradeFlowUserRepository userRepository;

    private TradeFlowUser getPrincipal() {
        return (TradeFlowUser) SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public GenericResponse createOrder(OrderRequestDTO orderRequestDTO){
        Order order = createOrderEntity(orderRequestDTO);
        orderRepository.save( order );
        return GenericResponse.createSuccessResponse(environment.getProperty(CREATE_ORDER_REQUEST_SUCCESS));
    }

    @Override
    public GenericResponse fetchOrders(OrderSearchRequest orderSearchRequest){

        Page<Order> page = orderRepository.findAll(PageRequest.of(orderSearchRequest.getPageNumber()<=0 ? 0 : orderSearchRequest.getPageNumber()-1,
                orderSearchRequest.getPageSize()<=0 ? 10 : orderSearchRequest.getPageSize(),
                Sort.Direction.DESC));
        return GenericResponse.createSuccessResponse(
                environment.getProperty(ORDER_SUCCESSFULLY_FETCH), "orders",
                new PaginatedResponseDTO(page.getContent(), page.getTotalElements()));
    }

    @Override
    public GenericResponse fetchOrderById(OrderIdRequest orderIdRequest){
        Optional<Order> order = orderRepository.findById( orderIdRequest.getId() );
        if( order.isPresent() ){
            return GenericResponse.createSuccessResponse(
                    environment.getProperty(ORDER_SUCCESSFULLY_FETCH),ORDER,order);
        } else {
            return GenericResponse.createSuccessResponse(environment.getProperty(ORDER_SUCCESSFULLY_FETCH));
        }
    }

    @Override
    public GenericResponse fetchOrderByCustomerId( OrderIdRequest orderIdRequest){
        Optional<Order> order = orderRepository.findByCustomerId( orderIdRequest.getId() );
        if( order.isPresent() ){
            return GenericResponse.createSuccessResponse(
                    environment.getProperty(ORDER_SUCCESSFULLY_FETCH),ORDER,order);
        } else {
            return GenericResponse.createSuccessResponse(environment.getProperty(ORDER_SUCCESSFULLY_FETCH));
        }
    }

    public Order createOrderEntity( OrderRequestDTO orderRequestDTO){
        Order order = new Order();
        Optional<TradeFlowUser> user = userRepository.findById( getPrincipal().getId() );

        // Map the order items
        List<OrderItem> orderItems = orderRequestDTO.getItems().stream().map(itemDTO ->
                OrderItem.builder()
                        .order(order)
                        .quantity(itemDTO.getQuantity())
                        .price(itemDTO.getPrice())
                        .build()
        ).toList();
        // Set items to the order
        order.setItems(orderItems);

        if( user.isPresent() ){
            order.setCustomer( user.get() );
        } else {
            // Handle the case where the user is not found
            throw new IllegalArgumentException("Customer User not found");
        }
        order.setCreatedBy( getPrincipal().getUsername() );
        order.setCreatedOn( getPrincipal().getCreatedOn() );
        order.setStatus( OrderStatus.ORDER_PLACED );
        order.setTotalPrice( orderRequestDTO.getTotalPrice() );
        order.setOrderNumber( generateOrderNumber() );

        return order;
    }

    private String generateOrderNumber(){
        // Create a SimpleDateFormat object to format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        // Get the current date
        Date currentDate = new Date();
        // Format the date as required
        String formattedDate = dateFormat.format(currentDate);
        // Generate 6 random numbers
        Random random = new Random();
        StringBuilder randomNumbers = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            randomNumbers.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        return  "O" + formattedDate + getPrincipal().getCompanyId() + randomNumbers.toString();
    }
}
