package com.online.education.service.impl;

import com.online.education.entity.Item;
import com.online.education.entity.ItemVariant;
import com.online.education.entity.TradeFlowUser;
import com.online.education.repository.ItemRepository;
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

import java.util.Optional;


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

    private TradeFlowUser getPrincipal() {
        return (TradeFlowUser) SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    @Transactional
    public GenericResponse createItem(ItemRequestDTO itemRequestDTO) {
        validateItemRequestObj( itemRequestDTO );
        createItemEntity(itemRequestDTO);
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




    private void validateItemRequestObj( ItemRequestDTO itemRequestDTO){
        if ( itemRequestDTO ==null) {
            throw new IllegalArgumentException(environment.getProperty(INVALID_REQUEST));
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            if(itemRequestDTO.getName() == null ){
                stringBuffer.append((stringBuffer.isEmpty() ? "" : ", ") + "Name is required");
            }
            if(itemRequestDTO.getItemCategoryId() == null ){
                stringBuffer.append((stringBuffer.isEmpty() ? "" : ", ") + "Item Category is required");
            }
            if(itemRequestDTO.getSku() == null ){
                stringBuffer.append((stringBuffer.isEmpty() ? "" : ", ") + "Sku is required");
            }
            if(!stringBuffer.isEmpty()){
               throw new IllegalArgumentException(stringBuffer.toString());
            }
        }
    }

    private void createItemEntity( ItemRequestDTO itemRequestDTO ){
        Item item = new Item();
        item.setName( itemRequestDTO.getName() );
        item.setDescription( item.getDescription() );
        item.setItemCategory( item.getItemCategory() );
        item.setSku( itemRequestDTO.getSku() );
        if( !itemRequestDTO.getItemVariants().isEmpty() ){
            int index = 0;
            for(ItemVariant itemVariant : itemRequestDTO.getItemVariants()){
                itemVariant.setPriceAdditional(itemRequestDTO.getItemVariants().get(index).getPriceAdditional());
                itemVariant.setColor(itemRequestDTO.getItemVariants().get(index).getColor());
                itemVariant.setSize(itemRequestDTO.getItemVariants().get(index).getSize());
                itemVariant.setItem(itemRequestDTO.getItemVariants().get(index).getItem());
                index++;
            }
        }
        itemRepository.save( item );
    }


    private static Specification<Item> commonSearchItemSpecification( ItemSearchRequest itemSearchRequest) {
        Specification<Item> specification =
                        SpecificationUtility.equalsValue("isActive", true);
        if( itemSearchRequest.getName() != null ){
            specification = specification.and(SpecificationUtility.equalsValue("name", itemSearchRequest.getName()));
        }
        if (itemSearchRequest.getSku() != null) {
            specification = specification.and(SpecificationUtility.equalsValue("sku", itemSearchRequest.getSku()));
        }
        return specification;
    }
}
