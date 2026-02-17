package com.lcncbe.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "pages")
public class Pages {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "background_color")
    private String backgroundColor;
    
    @OneToMany(mappedBy = "pageId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Widgets> widgets = new ArrayList<>();

	public Pages() {
    	
    }

	public Pages(String name) {
		this.name = name;
	}

	public Pages(String name, String backgroundColor) {
		this.name = name;
		this.backgroundColor = backgroundColor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public List<Widgets> getWidgets() {
		return widgets;
	}

	public void setWidgets(List<Widgets> widgets) {
		this.widgets = widgets;
	}
	
}
