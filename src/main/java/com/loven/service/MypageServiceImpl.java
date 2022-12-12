package com.loven.service;

import com.loven.entity.BlindVO;
import com.loven.entity.Comment;
import com.loven.entity.PostVO;
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

    @Override
    public List<BlindVO> postList(String id) {
        List<BlindVO> list = mapper.postList(id);
        return list;
    }

	@Override
	public void update_vo(User vo) {
		mapper.update_vo(vo);
		
	}

	

	

	/*
	 * @Override public void mypageDelete(int seq) { mapper.mypageDelete(seq);
	 * 
	 * }
	 */
	@Override
	public void mypageDelete(String seq) {
		mapper.mypageDelete(seq);
		
	}

	@Override
	public List<Comment> cmtList(String id) {
		List<Comment> list = mapper.cmtList(id);
        return list;
	}

	@Override
	public void mypagecmtDelete(String cmt_seq) {
		mapper.mypagecmtDelete(cmt_seq);
		
	}





}
