package com.lcncbe.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lcncbe.model.Pages;
import com.lcncbe.model.Widgets;
import com.lcncbe.repository.PageRepository;
import com.lcncbe.repository.WidgetRepository;

@Service
public class PageServiceImplementation implements PageService{
	
	private PageRepository pageRepository;
	private WidgetRepository widgetRepository;
	
	public PageServiceImplementation(PageRepository pageRepository,WidgetRepository widgetRepository) {
		this.pageRepository=pageRepository;
		this.widgetRepository=widgetRepository;
	}


	// new helper that sets backgroundColor when provided
	public Pages createPage(String name, String backgroundColor) {
		Pages newPage = new Pages();
		newPage.setName(name);
		newPage.setBackgroundColor(backgroundColor);
		Pages savedPage = pageRepository.save(newPage);
		return savedPage;
	}


	@Override
	public Object findById(Long id) {
		// TODO Auto-generated method stub
		return pageRepository.findById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	}


	@Override
	public @Nullable Object findAll() {
		// TODO Auto-generated method stub
		return pageRepository.findAll();
	}


	@Override
	public void deletePage(Long id) {
		// TODO Auto-generated method stub
		List<Widgets> widgets = widgetRepository.findByPageId(id);
        widgetRepository.deleteAll(widgets);

        // 2. Delete the page itself
        pageRepository.deleteById(id);
		
	}

}
