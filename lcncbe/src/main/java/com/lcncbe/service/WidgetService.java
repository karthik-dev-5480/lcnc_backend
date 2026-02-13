package com.lcncbe.service;

import com.lcncbe.model.Widgets;
import com.lcncbe.model.WidgetProperty;
import com.lcncbe.repository.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class WidgetService {

    @Autowired
    private WidgetRepository widgetRepository;

    /**
     * Fetch all widgets for a specific page
     */
    public List<Widgets> getWidgetsByPageId(Long pageId) {
        return widgetRepository.findByPageId(pageId);
    }

    /**
     * Create or update a widget with its properties
     */
    @Transactional
    public Widgets saveWidget(Widgets widget) {
        // Validate that pageId is set
        if (widget.getPageId() == null) {
            throw new IllegalArgumentException("pageId is required");
        }

        Widgets savedWidget;

        if (widget.getId() != null && widget.getId() > 0) {
            // Update existing widget
            Optional<Widgets> existing = widgetRepository.findById(widget.getId());
            if (existing.isPresent()) {
                Widgets existingWidget = existing.get();
                
                // Update widget properties
                existingWidget.setType(widget.getType());
                existingWidget.setLabel(widget.getLabel());
                existingWidget.setX(widget.getX());
                existingWidget.setY(widget.getY());
                existingWidget.setParentId(widget.getParentId());
                existingWidget.setWidgetOrder(widget.getWidgetOrder());
                
                // Handle properties - merge incoming properties with existing ones
                if (widget.getProperties() != null) {
                    // Get incoming property names
                    java.util.Set<String> incomingNames = new java.util.HashSet<>();
                    for (WidgetProperty prop : widget.getProperties()) {
                        if (prop.getPropertyName() != null && !prop.getPropertyName().trim().isEmpty()) {
                            incomingNames.add(prop.getPropertyName());
                        }
                    }
                    
                    // Remove properties that are not in the incoming list
                    existingWidget.getProperties().removeIf(p -> !incomingNames.contains(p.getPropertyName()));
                    
                    // Update or add properties from incoming data
                    for (WidgetProperty prop : widget.getProperties()) {
                        // Skip if property name is null or empty
                        if (prop.getPropertyName() == null || prop.getPropertyName().trim().isEmpty()) {
                            continue;
                        }
                        
                        // Find existing property with same name
                        WidgetProperty existingProp = existingWidget.getProperties().stream()
                            .filter(p -> p.getPropertyName().equals(prop.getPropertyName()))
                            .findFirst()
                            .orElse(null);
                        
                        if (existingProp != null) {
                            // Update existing property
                            existingProp.setPropertyValue(prop.getPropertyValue() != null ? prop.getPropertyValue() : "");
                        } else {
                            // Add new property
                            WidgetProperty newProp = new WidgetProperty();
                            newProp.setPropertyName(prop.getPropertyName());
                            newProp.setPropertyValue(prop.getPropertyValue() != null ? prop.getPropertyValue() : "");
                            newProp.setWidget(existingWidget);
                            existingWidget.getProperties().add(newProp);
                        }
                    }
                }
                
                savedWidget = widgetRepository.save(existingWidget);
            } else {
                savedWidget = createNewWidget(widget);
            }
        } else {
            // Create new widget
            savedWidget = createNewWidget(widget);
        }

        return savedWidget;
    }

    /**
     * Helper method to create a new widget
     */
    private Widgets createNewWidget(Widgets widget) {
        // Set back-reference for properties
        if (widget.getProperties() != null) {
            for (WidgetProperty prop : widget.getProperties()) {
                prop.setWidget(widget);
            }
        }
        return widgetRepository.save(widget);
    }

    /**
     * Delete a widget
     */
    @Transactional
    public void deleteWidget(Long id) {
        widgetRepository.deleteById(id);
    }

    /**
     * Get a single widget by ID
     */
    public Optional<Widgets> getWidgetById(Long id) {
        return widgetRepository.findById(id);
    }

    /**
     * Fix orphaned widgets by setting parentId to null if parent doesn't exist
     */
    @Transactional
    public String fixOrphanedWidgets(Long pageId) {
        List<Widgets> allWidgets = widgetRepository.findByPageId(pageId);
        int fixedCount = 0;

        for (Widgets widget : allWidgets) {
            if (widget.getParentId() != null) {
                // Check if parent exists
                Optional<Widgets> parent = widgetRepository.findById(widget.getParentId());
                if (!parent.isPresent()) {
                    // Parent doesn't exist, set parentId to null
                    widget.setParentId(null);
                    widgetRepository.save(widget);
                    fixedCount++;
                }
            }
        }

        return "Fixed " + fixedCount + " orphaned widgets";
    }
}
