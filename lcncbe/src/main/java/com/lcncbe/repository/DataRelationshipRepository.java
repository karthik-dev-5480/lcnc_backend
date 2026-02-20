package com.lcncbe.repository;

import com.lcncbe.model.DataRelationship;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRelationshipRepository extends JpaRepository<DataRelationship, Long> {
	List<DataRelationship> findBySourceTableId(Long sourceTableId);
}