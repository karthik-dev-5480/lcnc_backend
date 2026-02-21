package com.lcncbe.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "meta_dataset_joins")
public class DatasetJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dataset_id")
    private Dataset dataset;

    private String joinType; // INNER, LEFT, RIGHT

    @ManyToOne
    @JoinColumn(name = "left_dataset_table_id")
    private DatasetTable leftDatasetTable;

    @ManyToOne
    @JoinColumn(name = "left_column_id")
    private DataColumn leftColumn;

    @ManyToOne
    @JoinColumn(name = "right_dataset_table_id")
    private DatasetTable rightDatasetTable;

    @ManyToOne
    @JoinColumn(name = "right_column_id")
    private DataColumn rightColumn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public DatasetTable getLeftDatasetTable() {
		return leftDatasetTable;
	}

	public void setLeftDatasetTable(DatasetTable leftDatasetTable) {
		this.leftDatasetTable = leftDatasetTable;
	}

	public DataColumn getLeftColumn() {
		return leftColumn;
	}

	public void setLeftColumn(DataColumn leftColumn) {
		this.leftColumn = leftColumn;
	}

	public DatasetTable getRightDatasetTable() {
		return rightDatasetTable;
	}

	public void setRightDatasetTable(DatasetTable rightDatasetTable) {
		this.rightDatasetTable = rightDatasetTable;
	}

	public DataColumn getRightColumn() {
		return rightColumn;
	}

	public void setRightColumn(DataColumn rightColumn) {
		this.rightColumn = rightColumn;
	}
    
    
}
