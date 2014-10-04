package com.company.issuetracker.core.util;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

public class Wrapper<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<T> content;

	/*
	private long totalElements;

	private int currentPage;

	private int totalPages;

	private int pageSize;

	 */
	public Wrapper(){		
	}

	public Wrapper(Page<T> page) {
		this.setContent(page.getContent());
		/*		this.setTotalElements(page.getTotalElements());
		this.setCurrentPage(page.getNumber());
		this.setTotalPages(page.getTotalPages());
		this.setPageSize(page.getSize());*/
	}

	public Wrapper(List<T> content){
		this.content=content;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	/*
	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	 */
}
