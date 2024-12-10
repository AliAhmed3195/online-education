package com.online.education.resource;

import com.online.education.request.OrderIdRequest;
import com.online.education.request.OrderRequestDTO;
import com.online.education.request.OrderSearchRequest;
import com.online.education.response.GenericResponse;
import com.online.education.service.OrderProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderResource {

    @Autowired
    private OrderProcessingService orderProcessingService;

    @PostMapping("/create")
    public GenericResponse createOrder( @RequestBody  OrderRequestDTO orderRequestDTO){
        return orderProcessingService.createOrder( orderRequestDTO );
    }

    @PostMapping("/list")
    public GenericResponse getOrders(@RequestBody OrderSearchRequest orderSearchRequest ){
        return orderProcessingService.fetchOrders( orderSearchRequest );
    }

    @PostMapping("/view-detail")
    public GenericResponse getOrderById(@RequestBody OrderIdRequest orderIdRequest){
        return orderProcessingService.fetchOrderById( orderIdRequest );
    }

    @PostMapping("/get-customer-id")
    public GenericResponse getOrdersByCustomerId(@RequestBody OrderIdRequest orderIdRequest){
        return orderProcessingService.fetchOrderByCustomerId( orderIdRequest );
    }
}
