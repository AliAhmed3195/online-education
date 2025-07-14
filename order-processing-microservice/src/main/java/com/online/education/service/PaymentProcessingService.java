package com.online.education.service;

import com.online.education.request.OrderRequestDTO;
import com.online.education.request.PaymentIdRequest;
import com.online.education.request.PaymentRequestDTO;
import com.online.education.request.PaymentSearchRequest;
import com.online.education.response.GenericResponse;

public interface PaymentProcessingService {

    public GenericResponse processPayment(PaymentRequestDTO paymentRequestDTO);

    public GenericResponse fetchPayments(PaymentSearchRequest paymentSearchRequest);

    public GenericResponse fetchPaymentById(PaymentIdRequest paymentIdRequest );
}
