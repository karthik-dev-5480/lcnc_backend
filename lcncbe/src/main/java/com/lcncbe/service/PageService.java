package com.lcncbe.service;

import org.jspecify.annotations.Nullable;

import com.lcncbe.model.Pages;

public interface PageService {

	Pages createPage(String name,String bg);

	Object findById(Long id);

	@Nullable
	Object findAll();

	void deletePage(Long id);

}
