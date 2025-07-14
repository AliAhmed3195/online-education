package com.online.education.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "ITEM")
public class Item extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ITEM_CATEGORY_ID", nullable = false)
    private ItemCategory itemCategory;

    @Column(name = "sku")
    private String sku;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_TYPE_ID")
    private UserType userType;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private TradeFlowUser tradeFlowUser;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ItemVariant> itemVariants = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ItemImage> itemImages;

}

