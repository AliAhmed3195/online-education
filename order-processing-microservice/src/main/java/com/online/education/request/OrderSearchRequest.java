package com.online.education.request;

import lombok.Data;

@Data
public class OrderSearchRequest extends GenericPageRequestDTO {
    public Long orderId;
}
