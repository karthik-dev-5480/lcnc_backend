package com.lcncbe.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "meta_dataset_conditions")
public class DatasetCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dataset_id")
    private Dataset dataset;

    @ManyToOne
    @JoinColumn(name = "left_dataset_table_id")
    private DatasetTable leftDatasetTable;

    @ManyToOne
    @JoinColumn(name = "left_column_id")
    private DataColumn leftColumn;

    private String operator;
    private String rightOperandType;
    
    @Column(name = "right_operand_value", length = 1000)
    private String rightOperandValue;

    @Column(name = "condition_order")
    private Integer conditionOrder;

    @Column(name = "logical_operator")
    private String logicalOperator;

    @Column(name = "open_paren_count")
    private Integer openParenCount = 0;

    @Column(name = "close_paren_count")
    private Integer closeParenCount = 0;

    // --- CONSTRUCTORS ---

    // Default No-Args Constructor (Required by JPA)
    public DatasetCondition() {
    }

    // Full Constructor (excluding ID, as it is usually auto-generated)
    public DatasetCondition(Dataset dataset, DatasetTable leftDatasetTable, DataColumn leftColumn, 
                            String operator, String rightOperandType, String rightOperandValue, 
                            Integer conditionOrder, String logicalOperator, 
                            Integer openParenCount, Integer closeParenCount) {
        this.dataset = dataset;
        this.leftDatasetTable = leftDatasetTable;
        this.leftColumn = leftColumn;
        this.operator = operator;
        this.rightOperandType = rightOperandType;
        this.rightOperandValue = rightOperandValue;
        this.conditionOrder = conditionOrder;
        this.logicalOperator = logicalOperator;
        this.openParenCount = openParenCount;
        this.closeParenCount = closeParenCount;
    }

    // --- GETTERS AND SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Dataset getDataset() { return dataset; }
    public void setDataset(Dataset dataset) { this.dataset = dataset; }

    public DatasetTable getLeftDatasetTable() { return leftDatasetTable; }
    public void setLeftDatasetTable(DatasetTable leftDatasetTable) { this.leftDatasetTable = leftDatasetTable; }

    public DataColumn getLeftColumn() { return leftColumn; }
    public void setLeftColumn(DataColumn leftColumn) { this.leftColumn = leftColumn; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getRightOperandType() { return rightOperandType; }
    public void setRightOperandType(String rightOperandType) { this.rightOperandType = rightOperandType; }

    public String getRightOperandValue() { return rightOperandValue; }
    public void setRightOperandValue(String rightOperandValue) { this.rightOperandValue = rightOperandValue; }

    public Integer getConditionOrder() { return conditionOrder; }
    public void setConditionOrder(Integer conditionOrder) { this.conditionOrder = conditionOrder; }

    public String getLogicalOperator() { return logicalOperator; }
    public void setLogicalOperator(String logicalOperator) { this.logicalOperator = logicalOperator; }

    public Integer getOpenParenCount() { return openParenCount; }
    public void setOpenParenCount(Integer openParenCount) { this.openParenCount = openParenCount; }

    public Integer getCloseParenCount() { return closeParenCount; }
    public void setCloseParenCount(Integer closeParenCount) { this.closeParenCount = closeParenCount; }
}