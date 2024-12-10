package com.online.education.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ITEM_VARIANT")
public class ItemVariant {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ITEM_ID", nullable = false)
    private Item item;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "SIZE")
    private String size;

    @Column(name= "PRICE_ADDITIONAL")
    private Long priceAdditional;
}
