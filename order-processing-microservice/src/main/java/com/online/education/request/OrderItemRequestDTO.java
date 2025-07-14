package com.online.education.request;

import lombok.Data;

@Data
public class OrderItemRequestDTO {

    private Long itemId; // ID of the item being ordered

    private Integer quantity; // Quantity of the item

    private Double price; // Price of the item (optional, can be derived)
}
