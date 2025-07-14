package com.online.education.entity;

import com.online.education.constant.OrderStatus;
import com.online.education.constant.PaymentStatus;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "PAYMENTS")

public class Payment {


    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship with order entity
    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order orderId;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private TradeFlowUser customerId;

    @Column(name = "PAYMENT_DATE")
    @Nonnull
    private Date paymentDate;

//    private String paymentMethod; // e.g., CARD, UPI, PAYPAL

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "AMOUNT", nullable = false)
    private Double amount;

    }
