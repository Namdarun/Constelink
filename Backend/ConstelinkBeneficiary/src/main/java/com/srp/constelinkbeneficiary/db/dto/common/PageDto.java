package com.srp.constelinkbeneficiary.db.dto.common;

public class PageDto {
	private int currentPage = 0;
	private int startPage = 0;
	private int endPage = 0;
	private int itemsPerPage;
	private boolean prev, next;
	private int total = 0;
}
