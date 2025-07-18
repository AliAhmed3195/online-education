package com.online.education.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemVariantDTO {

    private String color;
    private String size;
    private Double priceAdditional;

}
