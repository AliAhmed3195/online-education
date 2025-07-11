package com.online.education.request;

import lombok.Data;

@Data
public class UserSearchRequest extends GenericPageRequestDTO{

    public Long companyId;
    public String employeeId;
    public String username;
    private Long userTypeId;
}
