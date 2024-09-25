package com.online.education.entity;

import jakarta.persistence.*;
import lombok.Data;

//import javax.persistence.Entity;


@Data
@Entity
@Table(name = "USER_TYPE")
public class UserType extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;
}
