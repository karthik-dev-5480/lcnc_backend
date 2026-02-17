package com.lcncbe.service;

import com.lcncbe.dto.DataColumnRequest;
import com.lcncbe.dto.DataColumnResponse;
import java.util.List;

public interface DataColumnService {

    // Use specific DTOs instead of Object
    DataColumnResponse addColumn(Long tableId, DataColumnRequest request);

    List<DataColumnResponse> getColumnsByTable(Long tableId);

    DataColumnResponse updateColumn(Long columnId, DataColumnRequest request);

    void deleteColumn(Long columnId);
}