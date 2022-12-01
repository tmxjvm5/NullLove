package com.loven.mapper;

import com.loven.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MemberMapper {
    public void memberJoin(User user);

    public void kakaoLogin(User vo);
}
