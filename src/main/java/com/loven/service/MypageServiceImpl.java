package com.loven.service;

import com.loven.entity.User;
import com.loven.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MypageServiceImpl implements MypageService{

    @Autowired
    MemberMapper mapper;

    @Override
    public void userDelete(String id) {
        mapper.disableFk();
        mapper.userDelete(id);
        mapper.enableFk();
    }


}
