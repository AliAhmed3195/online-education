package com.online.education.service;

import com.online.education.request.OrderIdRequest;
import com.online.education.request.OrderRequestDTO;
import com.online.education.request.OrderSearchRequest;
import com.online.education.response.GenericResponse;

public interface OrderProcessingService {

    public GenericResponse createOrder(OrderRequestDTO orderRequestDTO);

    public GenericResponse fetchOrders(OrderSearchRequest orderSearchRequest);

    public GenericResponse fetchOrderById(OrderIdRequest orderIdRequest);

    public GenericResponse fetchOrderByCustomerId( OrderIdRequest orderIdRequest);

}
