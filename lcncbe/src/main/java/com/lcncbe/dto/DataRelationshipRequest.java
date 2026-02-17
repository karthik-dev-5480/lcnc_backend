package com.lcncbe.dto;

public class DataRelationshipRequest {
    private String fkName;
    private Long sourceTableId;
    private Long sourceColumnId;
    private Long targetTableId;
    private Long targetColumnId;

    // Getters and Setters
    public String getFkName() { 
        return fkName; 
    }
    
    public void setFkName(String fkName) { 
        this.fkName = fkName; 
    }

    public Long getSourceTableId() {
        return sourceTableId;
    }

    public void setSourceTableId(Long sourceTableId) {
        this.sourceTableId = sourceTableId;
    }

    public Long getSourceColumnId() {
        return sourceColumnId;
    }

    public void setSourceColumnId(Long sourceColumnId) {
        this.sourceColumnId = sourceColumnId;
    }

    public Long getTargetTableId() {
        return targetTableId;
    }

    public void setTargetTableId(Long targetTableId) {
        this.targetTableId = targetTableId;
    }

    public Long getTargetColumnId() {
        return targetColumnId;
    }

    public void setTargetColumnId(Long targetColumnId) {
        this.targetColumnId = targetColumnId;
    }
}