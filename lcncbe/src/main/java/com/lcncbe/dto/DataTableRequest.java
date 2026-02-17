package com.lcncbe.dto;



import java.util.List;

public class DataTableRequest {
    private String tableName;
    private String description;
    private List<DataColumnRequest> columns;

    // Getters and Setters
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<DataColumnRequest> getColumns() { return columns; }
    public void setColumns(List<DataColumnRequest> columns) { this.columns = columns; }
}