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
@Table(name = "PERMISSION")
public class Permission extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "URI")
    private String uri;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "ACTIVITY_ID")
//    private ActivityType activityType;

    @ManyToMany
    @JoinTable(name = "PERMISSION_GROUP_PERMISSION",
            joinColumns = {@JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "PERMISSION_GROUP_ID", referencedColumnName = "ID")})
    private List<PermissionGroup> permissionGroups;
}
