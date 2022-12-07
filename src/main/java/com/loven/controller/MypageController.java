package com.loven.controller;

import com.loven.entity.BlindVO;
import com.loven.entity.PostVO;
import com.loven.entity.User;
import com.loven.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MypageController {

    @Autowired
    MypageService mypageService;

    // 회원탈퇴
    @GetMapping("/mypage/delete/{id}")
    public String deleteUser(@PathVariable("id") String id, Model model) {
        System.out.println(id);
        mypageService.userDelete(id);
        return "redirect:/logout.do";
    }

    // 마이페이지 게시글 리스트
    @RequestMapping("/myPage.do")
    public String postList(HttpSession session,Model model) {

        User vo = (User) session.getAttribute("mvo");
        //System.out.println(vo);
        if(vo!=null) {
            String id = vo.getId();
            //System.out.println(id);
            List<BlindVO> list = mypageService.postList(id);
            //System.out.println(list);
            model.addAttribute("list", list);
        }


        return "mypage";


    }

}
