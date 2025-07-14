package com.online.education.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ROLE")
public class Role extends BaseEntity {

    public Role(long id) {
        this.id = id;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_TYPE_ID")
    private UserType userType;

    @ManyToMany
    @JoinTable(name = "ROLE_PERMISSION_GROUP",
            joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "PERMISSION_GROUP_ID", referencedColumnName = "ID")})
    @JsonIgnore
    @JsonManagedReference
    private Set<PermissionGroup> permissionGroups;

//    @ManyToOne
//    @JoinColumn(name="COMPANY_ID", nullable = false)
//    private Company company;
}
