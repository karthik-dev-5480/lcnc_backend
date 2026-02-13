package com.lcncbe.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.lcncbe.model.Widgets;

import java.util.List;

public interface WidgetRepository extends JpaRepository<Widgets, Long> {
    // Fetches all widgets for a specific page to build the design tree
    List<Widgets> findByPageId(Long pageId);
}