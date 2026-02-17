package com.lcncbe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "meta_relationships")
public class DataRelationship {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fkName;
    @ManyToOne
    @JoinColumn(name = "source_table_id")
    private DataTable sourceTable;

    // Mapping to the specific DataColumn entity for the source
    @ManyToOne
    @JoinColumn(name = "source_column_id")
    private DataColumn sourceColumn;

    @ManyToOne
    @JoinColumn(name = "target_table_id")
    private DataTable targetTable;

    // Mapping to the specific DataColumn entity for the target
    @ManyToOne
    @JoinColumn(name = "target_column_id")
    private DataColumn targetColumn;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFkName() {
		return fkName;
	}
	public void setFkName(String fkName) {
		this.fkName = fkName;
	}
	
	

	
	public DataTable getSourceTable() {
		return sourceTable;
	}
	public void setSourceTable(DataTable sourceTable) {
		this.sourceTable = sourceTable;
	}
	public DataTable getTargetTable() {
		return targetTable;
	}
	public void setTargetTable(DataTable targetTable) {
		this.targetTable = targetTable;
	}
	public DataColumn getSourceColumn() {
		return sourceColumn;
	}
	public void setSourceColumn(DataColumn sourceColumn) {
		this.sourceColumn = sourceColumn;
	}
	public DataColumn getTargetColumn() {
		return targetColumn;
	}
	public void setTargetColumn(DataColumn targetColumn) {
		this.targetColumn = targetColumn;
	}
	
    
}
