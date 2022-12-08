package com.loven.entity;

import lombok.Data;

@Data
public class PageMaker {
	private Criteria cri;
	private int totalCount; // 총 게시글의 수
	private int startPage; // 시작 페이지 번호
	private int endPage; // 끝 페이지 번호
	private boolean prev; // 이전 버튼 생성 여부(true, false)
	private boolean next; // 다음 버튼 생성 여부
	private int displayPageNum=5; // 한 페이지에 보여줄 페이지 번호의 수
	
	public void setTotalCount(int totalCount) {
		this.totalCount=totalCount;
		calcPaging();
	}
	
	private void calcPaging() {
		// 1. 화면에 보여질 마지막 페이지 번호
		endPage=(int)(Math.ceil(cri.getPage()/(double)displayPageNum)*displayPageNum);
		// 2. 화면에 보여질 시작 페이지 번호
		startPage=(endPage-displayPageNum)+1;
		if(startPage<=0) {
			startPage=1;
		}
		// 3. 전체 마지막 페이지 계산
		int tempEndPage=(int)(Math.ceil(totalCount/(double)cri.getPerPageNum()));
		// 4. 화면에 보여질 마지막 페이지의 유효성 체크
		if(tempEndPage<endPage) {
			endPage=tempEndPage;
		}
		// 5. 이전페이지 버튼 체크
		prev=(startPage==1) ? false: true;
		// 6. 다음페이지 버튼 체크
		next=(endPage<tempEndPage) ? true:false;
	}
	
}
