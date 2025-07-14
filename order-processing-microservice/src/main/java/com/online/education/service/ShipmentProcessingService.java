package com.online.education.service;

import com.online.education.request.*;
import com.online.education.response.GenericResponse;

public interface ShipmentProcessingService {

    public GenericResponse createShipment(ShipmentRequestDTO shipmentRequestDTO);

    public GenericResponse getShipments(ShipmentSearchRequest shipmentSearchRequest);

//    public GenericResponse trackShipment(PaymentSearchRequest paymentSearchRequest);

    public GenericResponse fetchShipmentById(ShipmentIdRequest shipmentIdRequest );
}
