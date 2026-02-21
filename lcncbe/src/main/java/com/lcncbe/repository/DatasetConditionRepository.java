package com.lcncbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcncbe.model.DatasetCondition;

@Repository
public interface DatasetConditionRepository extends JpaRepository<DatasetCondition, Long> {
    List<DatasetCondition> findByDatasetId(Long datasetId);
}