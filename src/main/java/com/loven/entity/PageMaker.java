package com.loven.entity;

import lombok.Data;

@Data
public class PageMaker {
	private Criteria cri;
	private int totalCount; // 총 게시글의 수
	private int startPage; // 시작 페이지 번호
	private int endPage; // 끝 페이지 번호
	private boolean prev; // 이전 버튼 생성 여부
	private boolean next; // 다음 버튼 생성 여부
	private int displayPageNum=5; // 한 페이지에 보여줄 페이지 번호의 수
	
	public PageMaker(Criteria cri, int totalCount) {
		this.cri = cri;
		this.totalCount=totalCount;
		
		this.endPage = (int)(Math.ceil(cri.getPage()/(double)displayPageNum)*displayPageNum);
		
		this.startPage = (endPage-displayPageNum)+1;
		if(startPage<=0) {
			this.startPage=1;
		}
		int tempEndPage=(int)(Math.ceil(totalCount/(double)cri.getPerPageNum()));
		if(tempEndPage<endPage) {
			this.endPage =tempEndPage;
		}
		
		this.prev=(startPage==1) ? false: true;
		this.next=(endPage<tempEndPage) ? true:false;
		
	}
	
	
}
