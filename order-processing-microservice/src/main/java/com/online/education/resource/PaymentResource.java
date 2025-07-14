package com.online.education.resource;

import com.online.education.entity.Payment;
import com.online.education.request.*;
import com.online.education.response.GenericResponse;
import com.online.education.service.Impl.PaymentProcessingServiceImpl;
import com.online.education.service.PaymentProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentResource {

    @Autowired
    private PaymentProcessingService paymentService;
//
    @PostMapping("/process")
    public GenericResponse processPayment( PaymentRequestDTO paymentRequestDTO){
        return paymentService.processPayment(paymentRequestDTO);
    }

//    @PostMapping("/view-transaction")
//    public GenericResponse getTransactionDetails(TransactionIdRequest transactionIdRequest){
////        return paymentService.getTransactionDetails(transactionIdRequest);
//    }

    @PostMapping("/list")
    public GenericResponse getPayments( PaymentSearchRequest paymentSearchRequest ){
        return paymentService.fetchPayments( paymentSearchRequest );
    }

    @PostMapping("/view-detail")
    public GenericResponse getPaymentById(PaymentIdRequest paymentIdRequest){
        return paymentService.fetchPaymentById( paymentIdRequest );
    }
}
