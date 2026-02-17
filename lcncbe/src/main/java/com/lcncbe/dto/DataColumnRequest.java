package com.lcncbe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataColumnRequest {
    private String columnName;
    private String dataType;
    private Integer length;
    private Integer precision;
    private Integer scale;
    @JsonProperty("isPrimaryKey")  // <--- ADD THIS
    private boolean isPrimaryKey;

    @JsonProperty("isIdentity")    // <--- Do this for other "is" boolean fields too
    private boolean isIdentity;

    @JsonProperty("isRequired")
    private boolean isRequired;

    @JsonProperty("isUnique")
    private boolean isUnique;
    private int columnOrder;

    // Getters and Setters
    public String getColumnName() { return columnName; }
    public void setColumnName(String columnName) { this.columnName = columnName; }
    public String getDataType() { return dataType; }
    public void setDataType(String dataType) { this.dataType = dataType; }
    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }
    public Integer getPrecision() { return precision; }
    public void setPrecision(Integer precision) { this.precision = precision; }
    public Integer getScale() { return scale; }
    public void setScale(Integer scale) { this.scale = scale; }
    public boolean isPrimaryKey() { return isPrimaryKey; }
    public void setPrimaryKey(boolean isPrimaryKey) { this.isPrimaryKey = isPrimaryKey; }
    public boolean isIdentity() { return isIdentity; }
    public void setIdentity(boolean isIdentity) { this.isIdentity = isIdentity; }
    public boolean isRequired() { return isRequired; }
    public void setRequired(boolean isRequired) { this.isRequired = isRequired; }
    public boolean isUnique() { return isUnique; }
    public void setUnique(boolean isUnique) { this.isUnique = isUnique; }
    public int getColumnOrder() { return columnOrder; }
    public void setColumnOrder(int columnOrder) { this.columnOrder = columnOrder; }
}
