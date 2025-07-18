package com.online.education.request;


import lombok.Data;

@Data
public class ItemSearchRequest extends GenericPageRequestDTO{
//    public Long companyId;
    public String name;
    public String sku;

    private Long userTypeId;

}

