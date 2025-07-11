package com.online.education.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "ITEM_IMAGE")
public class ItemImage extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    @JsonBackReference
    private Item item;

    @Column(name = "IMAGE_PATH", nullable = false)
    private String imagePath; // Stores file path or URL (e.g., "/uploads/item_1_image.jpg")


}