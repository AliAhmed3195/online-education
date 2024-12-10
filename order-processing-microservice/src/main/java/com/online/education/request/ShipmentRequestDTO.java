package com.online.education.request;

import lombok.Data;

import java.util.Date;

@Data
public class ShipmentRequestDTO {

    private Long orderId;
    private Long customerId;
    private String shipmentStatus;
    private Date shippedDate;
    private Date deliveryDate;
}
