package com.online.education.resource;

import com.online.education.request.ShipmentIdRequest;
import com.online.education.request.ShipmentRequestDTO;
import com.online.education.request.ShipmentSearchRequest;
import com.online.education.response.GenericResponse;
import com.online.education.service.ShipmentProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/shipments")
public class ShipmentResource {

    @Autowired
    private ShipmentProcessingService shipmentService;
//
    @PostMapping("/create")
    public GenericResponse createShipment(@RequestBody ShipmentRequestDTO shipmentRequestDTO){
        return shipmentService.createShipment(shipmentRequestDTO);
    }

//    @PostMapping("/track")
//    public GenericResponse trackShipment(ShipmentIdRequest shipmentIdRequest){
//        return shipmentService.trackShipment(shipmentIdRequest);
//    }

    @PostMapping("/list")
    public GenericResponse getShipments(@RequestBody ShipmentSearchRequest shipmentSearchRequest){
        return shipmentService.getShipments(shipmentSearchRequest);
    }

    @PostMapping("/view-detail")
    public GenericResponse getShipmentsById(@RequestBody ShipmentIdRequest shipmentIdRequest){
        return shipmentService.fetchShipmentById(shipmentIdRequest);
    }
}
