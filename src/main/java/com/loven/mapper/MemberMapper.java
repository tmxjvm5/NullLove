package com.loven.mapper;

import com.loven.entity.BlindVO;
import com.loven.entity.PostVO;
import com.loven.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MemberMapper {
    public void memberJoin(User user);

    public void kakaoLogin(User vo);
    
    public List<User> userList(); // 유저 리스트 불러오기
    
    public List<User> searchList1(String id); // 유저 검색(아이디)
    public List<User> searchList2(String email);  // 이메일
    public List<User> searchList3(String company_name);  // 기업명
    
    public void userDelete(String id); // 유저 삭제
    public void disableFk();
    public void enableFk();

	public void userPostDelete(String id); // 유저 게시글 삭제

	public void userCommentDelete(String id); // 유저 댓글 삭제
    
    List<BlindVO> postList(String id); // 마이페이지 게시글 리스트
}
