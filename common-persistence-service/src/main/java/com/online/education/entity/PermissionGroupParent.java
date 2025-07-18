package com.online.education.entity;



import jakarta.persistence.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


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
