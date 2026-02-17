package com.lcncbe.repository;

import com.lcncbe.model.DataTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTableRepository extends JpaRepository<DataTable, Long> {
}