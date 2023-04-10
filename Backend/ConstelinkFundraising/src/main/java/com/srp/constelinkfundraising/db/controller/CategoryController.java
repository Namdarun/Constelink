package com.srp.constelinkfundraising.db.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.srp.constelinkfundraising.db.dto.enums.CategorySortType;
import com.srp.constelinkfundraising.db.dto.request.CategoryAddRequest;
import com.srp.constelinkfundraising.db.dto.request.CategoryDeleteRequest;
import com.srp.constelinkfundraising.db.entity.Category;
import com.srp.constelinkfundraising.db.service.CategoryService;
import com.srp.constelinkfundraising.jwt.JWTParser;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "category", description = "카테고리 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;
	private final JWTParser jwtParser;

	@Operation(summary = "카테고리 추가/삭제", description = "categoryName = 카테고리 이름")
	@PostMapping("")
	public String addCategory(
		@RequestBody CategoryAddRequest categoryAddRequest
	) {
		return categoryService.addCategory(categoryAddRequest.getCategoryName());
	}

	// @Operation(summary = "카테고리 삭제", description = "categoryId = 카테고리 Id")
	// @DeleteMapping("")
	// public ResponseEntity<String> deleteCategory(
	// 	@RequestBody CategoryDeleteRequest categoryDeleteRequest
	// ) {
	// 	return ResponseEntity.ok(categoryService.deleteCategory(categoryDeleteRequest.getCategoryId()));
	// }

	@Operation(summary = "카테고리 열람", description = "page = 페이지, size = 한 페이지당 데이터 수, sortBy = 정렬 타입")
	@GetMapping("")
	public ResponseEntity<Page<Category>> getCategories(
		@RequestParam(name = "page", defaultValue = "1", required = false) int page,
		@RequestParam(name = "size", defaultValue = "5", required = false) int size,
		@RequestParam(name = "sortBy", defaultValue = "NAME_ASC", required = false) CategorySortType sortType
	) {
		Page<Category> categories = categoryService.getCategories(page - 1, size, sortType);
		return ResponseEntity.ok(categories);
	}

}
