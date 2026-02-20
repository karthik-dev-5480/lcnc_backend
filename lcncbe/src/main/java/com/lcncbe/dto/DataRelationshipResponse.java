package com.lcncbe.dto;

import com.lcncbe.model.DataColumn;

public class DataRelationshipResponse {
    private Long id;
    private String fkName;
    private String sourceTable;
    private String sourceColumn;
    private String targetTable;
    private String targetColumn;

    
	public DataRelationshipResponse(Long id, String fkName, String sourceTable, String sourceColumn, String targetTable,
			String targetColumn) {
		super();
		this.id = id;
		this.fkName = fkName;
		this.sourceTable = sourceTable;
		this.sourceColumn = sourceColumn;
		this.targetTable = targetTable;
		this.targetColumn = targetColumn;
	}
	// Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFkName() { return fkName; }
    public void setFkName(String fkName) { this.fkName = fkName; }
    public String getSourceTable() { return sourceTable; }
    public void setSourceTable(String sourceTable) { this.sourceTable = sourceTable; }
    public String getSourceColumn() { return sourceColumn; }
    public void setSourceColumn(String sourceColumn) { this.sourceColumn = sourceColumn; }
    public String getTargetTable() { return targetTable; }
    public void setTargetTable(String targetTable) { this.targetTable = targetTable; }
    public String getTargetColumn() { return targetColumn; }
    public void setTargetColumn(String targetColumn) { this.targetColumn = targetColumn; }
}
