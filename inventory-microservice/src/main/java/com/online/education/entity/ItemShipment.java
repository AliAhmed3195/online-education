package com.online.education.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "ITEM_SHIPMENT")
public class ItemShipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    // Relationship with Item entity
    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private ShipmentCountry country;

    // Shipment charges for the item in this specific country
    @Column(name = "SHIPMENT_CHARGES", nullable = false)
    private Double shipmentCharges;
}
