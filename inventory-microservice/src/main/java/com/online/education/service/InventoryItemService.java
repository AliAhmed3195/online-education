package com.online.education.service;

import com.online.education.request.ItemIdRequest;
import com.online.education.request.ItemRequestDTO;
import com.online.education.request.ItemSearchRequest;
import com.online.education.response.GenericResponse;

public interface InventoryItemService {

    GenericResponse createItem( ItemRequestDTO itemRequestDTO );

    GenericResponse listItem(ItemSearchRequest itemSearchRequest);

    GenericResponse findByItemId(ItemIdRequest itemIdRequest);
}
