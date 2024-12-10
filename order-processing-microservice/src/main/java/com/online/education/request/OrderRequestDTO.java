package com.online.education.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private Long customerId; // ID of the customer placing the order

    private List<OrderItemRequestDTO> items; // List of items in the order

    private Double totalPrice;
}
