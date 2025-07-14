package com.online.education.request;

import lombok.Data;

@Data
public class PaymentSearchRequest extends GenericPageRequestDTO {

    public String orderNumber;
    public Long customerId;
}
