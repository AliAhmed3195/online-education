package com.online.education.request;

import com.online.education.entity.TradeFlowUser;
import lombok.Data;

@Data
public class PaymentRequestDTO {

    private Long orderId;
    private Double amount;
    private String paymentMethod;
    private String transactionReference;
    private TradeFlowUser customerId;
}
