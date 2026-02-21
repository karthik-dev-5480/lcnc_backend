package com.lcncbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcncbe.model.DatasetParameter;

@Repository
public interface DatasetParameterRepository extends JpaRepository<DatasetParameter, Long> {
    List<DatasetParameter> findByDatasetId(Long datasetId);
}