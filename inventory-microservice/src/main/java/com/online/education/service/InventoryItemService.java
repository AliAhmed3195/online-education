package com.online.education.service;

import com.online.education.request.ItemIdRequest;
import com.online.education.request.ItemRequestDTO;
import com.online.education.request.ItemSearchRequest;
import com.online.education.response.GenericResponse;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface InventoryItemService {

    GenericResponse createItem(ItemRequestDTO itemRequestDTO, List<MultipartFile> files);

    GenericResponse listItem(ItemSearchRequest itemSearchRequest) throws AccessDeniedException;

    GenericResponse findByItemId(ItemIdRequest itemIdRequest);
}
