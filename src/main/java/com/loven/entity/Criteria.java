package com.loven.entity;

import lombok.Data;

@Data
public class Criteria {
	private int page;
	private int perPageNum;
	private int pageStart;
	
	
	public Criteria(int page) { // 생성자
		this.page = page;
		this.perPageNum = 15; // 한 페이지에 보여줄 개수
		this.pageStart = getPageStart(page)+1;
	}
	
	public int getPageStart(int page) { // 현재 페이지의 게시글의 시작번호
		
		return ((page-1)*perPageNum-1); 
	}
	
	
	
}
