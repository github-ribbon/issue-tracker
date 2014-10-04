package com.company.issuetracker.core.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Pager{

	private Integer page;

	private Integer size;	

	private String sortParam;

	private String sort;

	private Sort.Direction sortDirection;

	private Long totalElements;

	private Integer totalPages;

	public Pager(){
	}

	public Pager(Pageable pageable) {

		this.page = pageable.getPageNumber();
		this.size = pageable.getPageSize();

		//			assumed that only one sorting parameter may be sent
		resolveSort(pageable.getSort());			
	}

	public Pager(Integer page, Integer size, Integer totalPages, Long totalElements, Sort sort){
		this.totalElements=totalElements;
		this.page=page;
		this.totalPages=totalPages;
		this.size=size;

		resolveSort(sort);		
	}

	private void resolveSort(Sort s){

		try{
			Order order=s.iterator().next();

			sortDirection=order.getDirection();
			sortParam=order.getProperty();

			if((sortParam==null || sortParam.isEmpty())||(sortDirection==null)){
				sort="";
			}else{
				sort=sortParam+","+sortDirection;
			}	
		}catch(NullPointerException e){
		}
	}

	public String getSort(){
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@JsonIgnore
	public  Pageable getPageable(){
		Sort s;

		if((sortParam==null || sortParam.isEmpty())||(sortDirection==null)){
			s=null;
		}else{			
			s=new Sort(new Order(sortDirection, sortParam));
		}

		return new PageRequest(page, size, s);		
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
}
