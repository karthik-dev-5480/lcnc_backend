package com.lcncbe.repository;

import com.lcncbe.model.DataColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DataColumnRepository extends JpaRepository<DataColumn, Long> {
    // Find all columns belonging to a specific table, ordered by their display sequence
    List<DataColumn> findByTableIdOrderByColumnOrderAsc(Long tableId);
}