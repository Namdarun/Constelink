package com.srp.constelinkfundraising.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.srp.constelinkfundraising.db.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findCategoryById(Long id);

	Category findCategoryByCategoryName(String name);

	Boolean existsCategoryByCategoryName(String name);

	Page<Category> findAll(Pageable pageable);

	List<Category> findAll();

}
