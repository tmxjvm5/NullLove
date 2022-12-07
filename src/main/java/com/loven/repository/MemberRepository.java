package com.loven.repository;

import com.loven.entity.User;
import com.loven.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @Autowired
    MemberMapper memberMapper;

    public void memberJoin(User user){
        memberMapper.memberJoin(user);
    }

    public void kakaoLogin(User vo) {
        memberMapper.kakaoLogin(vo);
    }

//    public String idCheck(String insert_id) {
    }
//}
