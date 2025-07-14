package com.online.education.service.Impl;

import com.online.education.constant.ShipmentStatus;
import com.online.education.entity.Order;
import com.online.education.entity.Shipment;
import com.online.education.repository.OrderRepository;
import com.online.education.repository.ShipmentRepository;
import com.online.education.request.*;
import com.online.education.response.GenericResponse;
import com.online.education.response.PaginatedResponseDTO;
import com.online.education.service.ShipmentProcessingService;
import com.online.education.util.SpecificationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class ShipmentProcessingServiceImpl implements ShipmentProcessingService {

    private static final String INVALID_REQUEST = "invalid.request";
    private static final String CREATE_SHIPMENT_REQUEST_SUCCESS = "create.shipment.request.success";
    private static final String SHIPMENT_SUCCESSFULLY_FETCH = "shipment.fetch.success";
    private static final String SHIPMENT = "shipment";

    @Autowired
    private Environment environment;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private OrderRepository orderRepository;


    public GenericResponse createShipment(@RequestBody ShipmentRequestDTO shipmentRequestDTO) {
        Order order = orderRepository.findById(shipmentRequestDTO.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + shipmentRequestDTO.getOrderId()));
        Shipment shipment = new Shipment();
        shipment.setOrderId( order );
        shipment.setCustomerId( shipmentRequestDTO.getCustomerId());
        shipment.setShipmentStatus(ShipmentStatus.PENDING);
        // Save the payment in the database
        shipmentRepository.save(shipment);

        return GenericResponse.createSuccessResponse(environment.getProperty(CREATE_SHIPMENT_REQUEST_SUCCESS));
    }



    @Override
    public GenericResponse getShipments(@RequestBody ShipmentSearchRequest shipmentSearchRequest) {
        Specification<Shipment> specification = commonSearchShipmentSpecification(shipmentSearchRequest);
        Page<Shipment> page = shipmentRepository.findAll(specification, PageRequest.of(shipmentSearchRequest.getPageNumber()<=0 ? 0 : shipmentSearchRequest.getPageNumber()-1,
                shipmentSearchRequest.getPageSize()<=0 ? 10 : shipmentSearchRequest.getPageSize(),
                Sort.Direction.DESC, "id"));

        return GenericResponse.createSuccessResponse(
                environment.getProperty(SHIPMENT_SUCCESSFULLY_FETCH), "payments",
                new PaginatedResponseDTO(page.getContent(), page.getTotalElements()));
    }

    @Override
    public GenericResponse fetchShipmentById(@RequestBody ShipmentIdRequest shipmentIdRequest){
        Optional<Shipment> shipment = shipmentRepository.findById( shipmentIdRequest.getId() );
        if( shipment.isPresent() ){
            return GenericResponse.createSuccessResponse(
                    environment.getProperty(SHIPMENT_SUCCESSFULLY_FETCH),SHIPMENT,shipment);
        } else {
            return GenericResponse.createSuccessResponse(environment.getProperty(SHIPMENT_SUCCESSFULLY_FETCH));
        }
    }


    private static Specification<Shipment> commonSearchShipmentSpecification(ShipmentSearchRequest shipmentSearchRequest) {
        Specification<Shipment> specification =
                SpecificationUtility.equalsValue("isActive", true);
        if( shipmentSearchRequest.getOrderNumber() != null ){
            specification = specification.and(SpecificationUtility.equalsValue("orderId", "orderNumber", shipmentSearchRequest.getOrderNumber()));
        }
        if (shipmentSearchRequest.getCustomerId() != null) {
            specification = specification.and(SpecificationUtility.equalsValue("customerId", shipmentSearchRequest.getCustomerId()));
        }
        return specification;
    }
}
