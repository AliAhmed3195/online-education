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

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class SupplierInventoryItemResource {

    @Autowired
    private InventoryItemService itemService;


    @PostMapping(value = "/supplier/item/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public GenericResponse addItemRequest(
            @RequestPart("item") ItemRequestDTO itemRequestDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        return itemService.createItem(itemRequestDTO, files);
    }
    @PostMapping("/supplier/item/list")
    public GenericResponse itemList( @RequestBody ItemSearchRequest itemSearchRequest ) throws AccessDeniedException {
        return itemService.listItem( itemSearchRequest );
    }

    @PostMapping("/supplier/item/view-details")
    public GenericResponse getDetails(@RequestBody ItemIdRequest request) {
        return itemService.findByItemId(request);
    }
}
