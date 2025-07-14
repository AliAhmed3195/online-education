package com.online.education.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginatedResponseDTO<T> {
    private T page;
    private long totalRows;
}
