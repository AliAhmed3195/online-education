package com.online.education.entity;

import com.online.education.constant.ShipmentStatus;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SHIPMENT")
public class Shipment {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ORDER")
    private Order orderId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ShipmentStatus shipmentStatus;

    @Column(name = "SHIPPED_DATE")
    @Nonnull
    private Date shippedDate;

    @Column(name = "DELIVERY_DATE")
    @Nonnull
    private Date deliveryDate;
}
