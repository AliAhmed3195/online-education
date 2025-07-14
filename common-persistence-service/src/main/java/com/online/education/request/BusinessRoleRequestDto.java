package com.online.education.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class BusinessRoleRequestDto {


    private String roleName;
    private String description;
    private Collection<Long> permissionGroups = new ArrayList<>();
//    private long companyId;
//    private String companyName;
    private String userTypeName;
}
