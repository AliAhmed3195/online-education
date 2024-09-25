package com.online.education.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "TRADE_FLOW_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class TradeFlowUser extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", unique = true)
    @Nonnull
    private String username;


    @Column(name = "PASSWORD")
    @Nonnull
    private String password;


    @Column(name = "FIRST_NAME")
    private String firstName;


    @Column(name = "LAST_NAME")
    private String lastName;


    @Column(name = "EMAIL")
    private String email;


    @Column(name = "EMPLOYEE_ID")
    private String employeeId;

    @Column(name = "COMPANY_ID")
    private Long companyId;

    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_TYPE_ID")
    private UserType userType;
}
