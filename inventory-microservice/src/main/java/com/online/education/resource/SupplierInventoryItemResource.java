package com.online.education.resource;

import com.online.education.request.ItemIdRequest;
import com.online.education.request.ItemRequestDTO;
import com.online.education.request.ItemSearchRequest;
import com.online.education.response.GenericResponse;
import com.online.education.service.InventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SupplierInventoryItemResource {

    @Autowired
    private InventoryItemService itemService;

    @PostMapping("/supplier/item/create")
    public GenericResponse addItemRequest(@RequestBody ItemRequestDTO itemRequestDTO ){
        return itemService.createItem( itemRequestDTO );
    }

    @PostMapping("/supplier/item/list")
    public GenericResponse itemList( @RequestBody ItemSearchRequest itemSearchRequest ){
        return itemService.listItem( itemSearchRequest );
    }

    @PostMapping("/supplier/item/view-details")
    public GenericResponse getDetails(@RequestBody ItemIdRequest request) {
        return itemService.findByItemId(request);
    }
}
