package com.loven.mapper;

import com.loven.domain.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public void memberJoin(UserVo user);

    public void kakaoLogin(UserVo vo);
}
