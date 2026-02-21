package com.lcncbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcncbe.model.DatasetJoin;

@Repository
public interface DatasetJoinRepository extends JpaRepository<DatasetJoin, Long> {
    List<DatasetJoin> findByDatasetId(Long datasetId);
}