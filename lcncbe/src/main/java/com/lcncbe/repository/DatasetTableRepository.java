package com.lcncbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcncbe.model.DatasetTable;

@Repository
public interface DatasetTableRepository extends JpaRepository<DatasetTable, Long> {
    List<DatasetTable> findByDatasetId(Long datasetId);
}