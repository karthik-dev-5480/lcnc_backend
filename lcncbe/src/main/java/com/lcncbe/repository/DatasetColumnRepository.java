package com.lcncbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcncbe.model.DatasetColumn;

@Repository
public interface DatasetColumnRepository extends JpaRepository<DatasetColumn, Long> {
    List<DatasetColumn> findByDatasetId(Long datasetId);
}