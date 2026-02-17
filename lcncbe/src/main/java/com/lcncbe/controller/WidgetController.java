package com.lcncbe.controller;


import com.lcncbe.model.Widgets;
import com.lcncbe.service.WidgetService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/widgets")

public class WidgetController {

    private static final Logger logger = Logger.getLogger(WidgetController.class.getName());

    @Autowired
    private WidgetService widgetService;

    
    @GetMapping
    public List<Widgets> getWidgets(@RequestParam Long pageId) {
        return widgetService.getWidgetsByPageId(pageId);
    }

    
    @PostMapping("/sync")
    @Transactional
    public ResponseEntity<?> syncWidget(@RequestBody Widgets widgetDto) {
        try {
            if (widgetDto == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Widget data is required");
                return ResponseEntity.badRequest().body(error);
            }
            if (widgetDto.getPageId() == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "pageId is required");
                return ResponseEntity.badRequest().body(error);
            }
            Widgets saved = widgetService.saveWidget(widgetDto);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            logger.severe("Error syncing widget: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    
    @DeleteMapping("/{id}")
    public void deleteWidget(@PathVariable Long id) {
        widgetService.deleteWidget(id);
    }

   
    @PostMapping("/fix-orphaned/{pageId}")
    public String fixOrphanedWidgets(@PathVariable Long pageId) {
        return widgetService.fixOrphanedWidgets(pageId);
    }
}
