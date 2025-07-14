package com.online.education.request;

import lombok.Data;

@Data
public class BusinessRoleSearchRequest extends GenericPageRequestDTO {
    private Long roleId;
    public String roleName;
}
