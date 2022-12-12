package com.loven.service;

import com.loven.entity.BlindVO;
import com.loven.entity.Comment;
import com.loven.entity.PostVO;
import com.loven.entity.User;

import java.util.List;

public interface MypageService {

	public void userDelete(String id);

	public List<BlindVO> postList(String id);
	/* public List<BlindVO> cmtList(String id); */

	public void update_vo(User vo);

	public void mypageDelete(String seq);

	public List<Comment> cmtList(String id);

	public void mypagecmtDelete(String cmt_seq);
}
