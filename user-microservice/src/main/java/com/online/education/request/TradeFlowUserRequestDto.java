package com.online.education.request;

import lombok.Data;

@Data
public class TradeFlowUserRequestDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String employeeId;
    private Long companyId;
    private String mobileNo;
    private String userType;
    private Long userRoleId;
}
