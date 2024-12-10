package com.online.education.entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "PERMISSION_GROUP_PARENT")
public class PermissionGroupParent extends BaseEntity {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

//    @ManyToOne
//    @JoinColumn(name = "USER_TYPE_ID")
//    private UserType userType;

    @Column(name = "ORDER_ID", nullable = false)
    private int orderId;

}
