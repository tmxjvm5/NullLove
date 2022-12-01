package com.loven.service;

import com.loven.entity.User;
import com.loven.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired(required = false)
    MemberRepository memberRepository;
    public void memberJoin(User user) {
        memberRepository.memberJoin(user);
    }

    public void kakaoLogin(User vo) {
        memberRepository.kakaoLogin(vo);
    }
}
