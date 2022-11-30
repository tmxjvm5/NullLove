package com.loven.service;

import com.loven.domain.UserVo;
import com.loven.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired(required = false)
    MemberRepository memberRepository;
    public void memberJoin(UserVo user) {
        memberRepository.memberJoin(user);
    }

    public void kakaoLogin(UserVo vo) {
        memberRepository.kakaoLogin(vo);
    }
}
