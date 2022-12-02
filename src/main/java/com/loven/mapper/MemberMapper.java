package com.loven.mapper;

import com.loven.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MemberMapper {
    public void memberJoin(User user);

    public void kakaoLogin(User vo);
    
    public List<User> userList(); // 유저 리스트 불러오기
    
    public void userDelete(String id); // 유저 삭제
}
