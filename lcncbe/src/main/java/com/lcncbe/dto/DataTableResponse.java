package com.lcncbe.dto;

import java.util.List;

public class DataTableResponse {
    private Long id;
    private String tableName;
    private String description;
    private List<DataColumnResponse> columns;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<DataColumnResponse> getColumns() { return columns; }
    public void setColumns(List<DataColumnResponse> columns) { this.columns = columns; }
}