package com.loven.repository;

import com.loven.entity.UserVo;
import com.loven.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @Autowired
    MemberMapper memberMapper;

    public void memberJoin(UserVo user){
        memberMapper.memberJoin(user);
    }

    public void kakaoLogin(UserVo vo) {
        memberMapper.kakaoLogin(vo);
    }
}
