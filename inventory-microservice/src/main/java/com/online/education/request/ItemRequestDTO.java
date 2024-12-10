package com.online.education.request;

import com.online.education.entity.ItemVariant;
import lombok.Data;

import java.util.List;

@Data
public class ItemRequestDTO {
    String name;
    String description;
    String sku;
    Long itemCategoryId;
    List<ItemVariant> itemVariants;
}
