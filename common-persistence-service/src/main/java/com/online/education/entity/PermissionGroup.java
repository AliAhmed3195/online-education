package com.online.education.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PERMISSION_GROUP")
public class PermissionGroup extends BaseEntity {

    public PermissionGroup(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @ManyToMany
    @JoinTable(name = "ROLE_PERMISSION_GROUP",
            joinColumns = {@JoinColumn(name = "PERMISSION_GROUP_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private PermissionGroupParent parent;

//    @ManyToOne
//    @JoinColumn(name = "USER_TYPE_ID")
//    private UserType userType;
}
