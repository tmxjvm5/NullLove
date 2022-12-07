package com.loven.service;

import com.loven.entity.BlindVO;
import com.loven.entity.PostVO;
import com.loven.entity.User;

import java.util.List;

public interface MypageService {

    public void userDelete(String id);



    List<BlindVO> postList(String id);
}
