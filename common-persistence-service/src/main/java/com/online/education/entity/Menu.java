package com.online.education.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;



import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "MENU")
public class Menu extends BaseEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DISPLAY_NAME", nullable = false)
    private String displayName;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @OneToOne
    @JoinColumn(name = "PARENT_MENU_ID")
    private Menu parentMenu;

    @ManyToMany
    @JoinTable(name = "MENU_PERMISSION_GROUP",
            joinColumns = {@JoinColumn(name = "MENU_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "PERMISSION_GROUP_ID", referencedColumnName = "ID")})
    @JsonIgnore
    private List<PermissionGroup> permissionGroups = new ArrayList();

}
