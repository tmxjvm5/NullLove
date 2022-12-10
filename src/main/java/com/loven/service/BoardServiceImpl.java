package com.loven.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loven.entity.BlindVO;
import com.loven.entity.Company;
import com.loven.entity.Criteria;
import com.loven.entity.User;
import com.loven.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	BoardMapper mapper;
	// 일반회원가입
	@Override
	public void joinInsert(User vo) {
		mapper.joinInsert(vo);
		
	}
	// 일반 로그인
	@Override
	public User loginCheck(User vo) {
		User mvo = mapper.loginCheck(vo);
		return mvo;
	}
	// 아이디중복체크
	@Override
	public int idCheck(String id) {
		int cnt = mapper.idCheck(id);
		System.out.println("cnt: " + cnt);
		return cnt;
	}
	//기업 회원가입
	@Override
	public void cInsert(Company vo) {
		
		mapper.cInsert(vo);
		
	}
	@Override
	public Company companyCheck(User vo) {
		return mapper.companyCheck(vo);
		
	}
	@Override
	public void memberUpdate(User vo) {
		mapper.memberUpdate(vo);
		
	}
	// 블라인드 게시판 리스트
	@Override
	public List<BlindVO> blindList(Criteria cri) {
		return mapper.blindList(cri);
		
	}

	// 블라인드 게시판 공지사항 리스트
	@Override
	public List<BlindVO> ablindList(Criteria cri) {
		return mapper.ablindList(cri);
	}

	// 블라인드 게시판 글쓰기
	@Override
	public void blindInsert(BlindVO vo) {
		mapper.blindInsert(vo);
		
	}
	// 블라인드 게시판 상세보기
	@Override
	public BlindVO blindView(int seq) {
		BlindVO vo= mapper.blindView(seq);
		return vo;
	}
	// 게시글 수정
	@Override
	public void blindUpdate(BlindVO vo) {
		mapper.blindUpdate(vo);
		
	}
	// 게시글 삭제
	@Override
	public void blindDelete(int seq) {
		mapper.blindDelete(seq);
		
	}
	// 조회수증가
	@Override
	public boolean plusCnt(int seq) {
		  return mapper.plusCnt(seq);
		}
	
	// 블라인드 게시글의 총 개수
	@Override
	public int cntBlind(Criteria cri) {
		int cnt = mapper.cntBlind(cri);
		
		return cnt;
	}
	
	// 관리자 로그인
	@Override
	public User loginAdmin(User vo) {
		
		User mvo = mapper.loginAdmin(vo);
		
		return mvo;
	}
	
}
