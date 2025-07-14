package com.online.education.request;

import lombok.Data;

@Data
public class ShipmentSearchRequest extends GenericPageRequestDTO {

    public String orderNumber;
    public Long customerId;
}
