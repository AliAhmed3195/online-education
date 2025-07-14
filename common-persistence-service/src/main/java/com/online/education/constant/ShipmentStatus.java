package com.online.education.constant;

public enum ShipmentStatus {
    PENDING,        // Shipment is created but not yet processed
    PACKING,        // Shipment is being packed
    READY_TO_SHIP,  // Shipment is packed and ready to be handed over to the carrier
    IN_TRANSIT,     // Shipment is on its way to the destination
    DELIVERED,      // Shipment has been delivered to the customer
    RETURNED,       // Shipment was returned by the customer
    CANCELLED,      // Shipment was cancelled
    FAILED          // Shipment failed due to an issue (e.g., incorrect address)
}
