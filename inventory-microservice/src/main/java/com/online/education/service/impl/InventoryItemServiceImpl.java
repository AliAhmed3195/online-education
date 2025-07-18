package com.online.education.service.impl;


import com.online.education.entity.Item;
import com.online.education.entity.ItemVariant;
import com.online.education.entity.TradeFlowUser;
import com.online.education.repository.ItemRepository;

import com.online.education.Repository.TradeFlowUserRepository;
import com.online.education.Repository.UserTypeRepository;
import com.online.education.entity.*;
import com.online.education.repository.ItemCategoryRepository;
import com.online.education.repository.ItemImageRepository;
import com.online.education.repository.ItemRepository;
import com.online.education.repository.ItemVariantRepository;
import com.online.education.request.ItemIdRequest;
import com.online.education.request.ItemRequestDTO;
import com.online.education.request.ItemSearchRequest;
import com.online.education.response.GenericResponse;
import com.online.education.response.PaginatedResponseDTO;
import com.online.education.service.InventoryItemService;
import com.online.education.util.SpecificationUtility;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class InventoryItemServiceImpl implements InventoryItemService {

    private static final String INVALID_REQUEST = "invalid.request";
    private static final String CREATE_ITEM_REQUEST_SUCCESS = "create.item.request.success";
    private static final String ITEM_SUCCESSFULLY_FETCH = "item.fetch.success";
    private static final String ITEM = "item";

    @Autowired
    private Environment environment;

    @Autowired
    private ItemRepository itemRepository;


    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Autowired
    private TradeFlowUserRepository tradeFlowUserRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private ItemImageRepository itemImageRepository;

    @Autowired
    private ItemVariantRepository itemVariantRepository;


    private TradeFlowUser getPrincipal() {
        return (TradeFlowUser) SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    @Transactional

    public GenericResponse createItem(ItemRequestDTO itemRequestDTO, List<MultipartFile> files) {
        validateItemRequestObj( itemRequestDTO );
        createItemEntity(itemRequestDTO, files);

        return GenericResponse.createSuccessResponse(environment.getProperty(CREATE_ITEM_REQUEST_SUCCESS));
    }

    @Override
    public GenericResponse listItem( ItemSearchRequest itemSearchRequest ){
//        if(getPrincipal().getUserType().getId() == 1 || getPrincipal().getUserType().getId() == 3){
//
//        } else {
//
//        }
        Specification<Item> specification = commonSearchItemSpecification(itemSearchRequest);
        Page<Item> page = itemRepository.findAll(specification, PageRequest.of(itemSearchRequest.getPageNumber()<=0 ? 0 : itemSearchRequest.getPageNumber()-1,
                itemSearchRequest.getPageSize()<=0 ? 10 : itemSearchRequest.getPageSize(),
                Sort.Direction.DESC, "id"));

        return GenericResponse.createSuccessResponse(
                environment.getProperty(ITEM_SUCCESSFULLY_FETCH), "items",
                new PaginatedResponseDTO(page.getContent(), page.getTotalElements()));
    }

    @Override
    public GenericResponse findByItemId(ItemIdRequest itemIdRequest){
        Optional<Item> item = itemRepository.findById( itemIdRequest.getItemId() );
        if( item.isPresent() ){
            return GenericResponse.createSuccessResponse(
                    environment.getProperty(ITEM_SUCCESSFULLY_FETCH),ITEM,item);
        } else {
            return GenericResponse.createSuccessResponse(environment.getProperty(ITEM_SUCCESSFULLY_FETCH));
        }
    }







    
    private void validateItemRequestObj( ItemRequestDTO itemRequestDTO ) {
        if (itemRequestDTO == null) {
            log.error("ItemRequestDTO is null");
            throw new IllegalArgumentException(environment.getProperty("invalid.request", "Invalid request: Item data is missing"));
        }

        StringBuilder errors = new StringBuilder();
        if (itemRequestDTO.getName() == null || itemRequestDTO.getName().trim().isEmpty()) {
            errors.append("Name is required and cannot be empty; ");
        }
        if (itemRequestDTO.getItemCategoryId() == null || itemRequestDTO.getItemCategoryId() <= 0) {
            errors.append("Item Category ID is required and must be a positive number; ");
        }
        if (itemRequestDTO.getSku() == null || itemRequestDTO.getSku().trim().isEmpty()) {
            errors.append("SKU is required and cannot be empty; ");
        }
        if (itemRequestDTO.getUserId() == null || itemRequestDTO.getUserId() <= 0) {
            errors.append("User ID is required and must be a positive number; ");
        }
        if (itemRequestDTO.getUserTypeId() == null || itemRequestDTO.getUserTypeId() <= 0) {
            errors.append("User Type ID is required and must be a positive number; ");
        }
        // Validate itemVariants if present
        if (itemRequestDTO.getItemVariants() != null && !itemRequestDTO.getItemVariants().isEmpty()) {
            int index = 0;
            for (ItemVariant variant : itemRequestDTO.getItemVariants()) {
                if (variant.getPriceAdditional() == null || variant.getPriceAdditional() < 0) {
                    errors.append("Variant at index " + index + ": Price is required and must be non-negative; ");
                }
                index++;
            }
        }

        if (!errors.isEmpty()) {
            log.error("Validation failed for ItemRequestDTO: {}", errors.toString());
            throw new IllegalArgumentException(errors.toString());
        }
    }

    private void createItemEntity(ItemRequestDTO itemRequestDTO, List<MultipartFile> files) {
        Item item = new Item();
        item.setName(itemRequestDTO.getName());
        item.setDescription(itemRequestDTO.getDescription());

        ItemCategory itemCategory = itemCategoryRepository
                .findById(itemRequestDTO.getItemCategoryId())
                .orElseThrow(() -> new RuntimeException("Item category not found"));
        item.setItemCategory(itemCategory);
        item.setSku(itemRequestDTO.getSku());

        TradeFlowUser tradeFlowUser = tradeFlowUserRepository
                .findById(itemRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Trade flow user not found"));
        item.setTradeFlowUser(tradeFlowUser);

        UserType userType = userTypeRepository
                .findById(itemRequestDTO.getUserTypeId())
                .orElseThrow(() -> new RuntimeException("User type not found"));
        item.setUserType(userType);

        itemRepository.save(item); // Save item first

        // Process files
        if (files != null && !files.isEmpty()) {
            String uploadDir = environment.getProperty("file.upload-dir", "C:/Users/HP/IdeaProjects/online-education/Uploads/");
            String accessUrlPrefix = environment.getProperty("file.access-url", "/Uploads/");

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    log.error("Failed to create directory: {}", uploadDir);
                    throw new RuntimeException("Cannot create upload directory: " + uploadDir);
                }
            }

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    log.warn("Skipping empty file: {}", file.getOriginalFilename());
                    continue;
                }

                String originalFileName = file.getOriginalFilename();
                if (originalFileName == null || originalFileName.contains("..")) {
                    log.error("Invalid file name: {}", originalFileName);
                    throw new RuntimeException("Invalid file name: " + originalFileName);
                }

                // Sanitize filename
                String cleanName = new File(originalFileName).getName().replaceAll("[^a-zA-Z0-9.-]", "_");
                String extension = "";
                int dotIndex = cleanName.lastIndexOf('.');
                if (dotIndex >= 0) {
                    extension = cleanName.substring(dotIndex);
                    cleanName = cleanName.substring(0, dotIndex);
                }

                // Create unique filename
                String uniqueFileName = cleanName + "_" + UUID.randomUUID().toString() + extension;
                String filePath = uploadDir + File.separator + uniqueFileName;

                try {
                    log.info("Saving file: {}, size: {} bytes", uniqueFileName, file.getSize());
                    file.transferTo(new File(filePath));

                    // Save relative access path in DB
                    String fileAccessUrl = accessUrlPrefix + uniqueFileName;

                    ItemImage itemImage = new ItemImage();
                    itemImage.setItem(item);
                    itemImage.setImagePath(fileAccessUrl); // e.g., /Uploads/filename.jfif
                    itemImageRepository.save(itemImage);

                } catch (IOException e) {
                    log.error("Failed to upload file: {}", uniqueFileName, e);
                    throw new RuntimeException("Failed to upload file: " + uniqueFileName, e);
                }
            }
        }

        // Process item variants
        if (itemRequestDTO.getItemVariants() != null && !itemRequestDTO.getItemVariants().isEmpty()) {
            for (ItemVariant variantDTO : itemRequestDTO.getItemVariants()) {
                ItemVariant itemVariant = new ItemVariant();
                itemVariant.setPriceAdditional(variantDTO.getPriceAdditional());
                itemVariant.setColor(variantDTO.getColor());
                itemVariant.setSize(variantDTO.getSize());
                itemVariant.setItem(item);
                itemVariantRepository.save(itemVariant);
            }
        }
    }


    private static Specification<Item> commonSearchItemSpecification( ItemSearchRequest itemSearchRequest) {
        Specification<Item> specification =
                        SpecificationUtility.equalsValue("isActive", true);

        if( itemSearchRequest.getName() != null && itemSearchRequest.getName() != "" ){
            specification = specification.and(SpecificationUtility.equalsValue("name", itemSearchRequest.getName()));
        }
        if (itemSearchRequest.getSku() != null && itemSearchRequest.getSku() != "") {
            specification = specification.and(SpecificationUtility.equalsValue("sku", itemSearchRequest.getSku()));
        }
        if (itemSearchRequest.getUserTypeId() != null) {
            specification = specification.and(SpecificationUtility.equalsValue("userType","id", itemSearchRequest.getUserTypeId()));
        }
        return specification;
    }
}
