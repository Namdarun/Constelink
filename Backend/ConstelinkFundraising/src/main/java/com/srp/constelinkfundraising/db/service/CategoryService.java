package com.srp.constelinkfundraising.db.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srp.constelinkfundraising.db.dto.enums.CategorySortType;
import com.srp.constelinkfundraising.db.entity.Category;
import com.srp.constelinkfundraising.db.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
	private final CategoryRepository categoryRepository;

	@Transactional
	public String addCategory(String categoryName) {
		Category category = categoryRepository.findCategoryByCategoryName(categoryName);
		if (category == null) {
			category = new Category();
			category.setCategoryName(categoryName);
			categoryRepository.saveAndFlush(category);
			return "카테고리 추가 성공";
		} else {
			categoryRepository.delete(category);
			return "카테고리 삭제 성공";
		}

	}

	// @Transactional
	// public String deleteCategory(Long categoryId) {
	// 	categoryRepository.deleteById(categoryId);
	// 	return "카테고리 삭제 성공";
	// }

	public Page<Category> getCategories(int page, int size, CategorySortType sortType) {
		Page<Category> categories;
		switch (sortType) {
			case NAME_ASC:
				categories = categoryRepository.findAll(
					PageRequest.of(page, size, Sort.by("categoryName").ascending()));
				break;
			case NAME_DESC:
				categories = categoryRepository.findAll(
					PageRequest.of(page, size, Sort.by("categoryName").descending()));
				break;
			case ALL:
				categories = categoryRepository.findAll(
					PageRequest.of(0, Integer.MAX_VALUE, Sort.by("categoryName").ascending()));
				break;
			default:
				categories = categoryRepository.findAll(PageRequest.of(page, size));
				break;
		}
		return categories;
	}
}
