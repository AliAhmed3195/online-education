package com.online.education.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Clob;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OAUTH_ACCESS_TOKEN")
public class OAuthAccessToken extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    @Lob
    @Column(length = 100000)
    private Clob token;
    @Lob
    @Column(length = 100000)
    private Clob refreshToken;

    private String clientId;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "UUID")
    private String uuid;
}
