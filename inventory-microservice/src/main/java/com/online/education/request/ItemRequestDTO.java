package com.online.education.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.online.education.entity.ItemImage;
import com.online.education.entity.ItemVariant;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemRequestDTO {
    String name;
    String description;
    String sku;
    Long itemCategoryId;
    Long userTypeId;
    Long userId;
    List<ItemVariant> itemVariants;




}
