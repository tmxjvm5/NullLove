package com.loven.jy.controller;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.loven.jy.entity.Boast;
import com.loven.jy.entity.Boast_cmt;
import com.loven.jy.mapper.ImgBoardMapper;
import com.loven.jy.service.ImgBoardService;
import com.loven.jy.service.ImgCmtService;

@Controller
public class ImgBoardCtrl {
	
	@Autowired
	ImgBoardService service;
	@Autowired
	ImgCmtService cmtService;
	
	@RequestMapping("/imgBoardList")
	public String list(Model model) {
		List<Boast> list=service.getLists();
		model.addAttribute("list",list);
		return "jy/imgBoardList"; //templates / board / list.html
	}
	// 글쓰기 폼
	@RequestMapping("/imgBoardForm")
	public String imgBoardForm() {
		return "jy/imgBoardWrite";
	}
	
	//글쓰기
	@ResponseBody
	@RequestMapping(value = "/imgBoardWrite", method = RequestMethod.POST)
	public String fileUpload(
			@RequestParam("article_file") List<MultipartFile> multipartFile
			, HttpServletRequest request, Boast vo) {
		
		String strResult = "{ \"result\":\"FAIL\" }";
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\uploadImg\\";
		String fileRoot;
		int cnt=1;
		try {
			// 파일이 있을때 탄다.
			if(multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {
				
				for(MultipartFile file:multipartFile) {
//					fileRoot = contextRoot + "resources/upload/";
					fileRoot = projectPath;
					System.out.println(fileRoot);
					
					String originalFileName = file.getOriginalFilename();	//오리지날 파일명
					String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
					String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
					
					File targetFile = new File(fileRoot + savedFileName);	
					try {
						InputStream fileStream = file.getInputStream();
						FileUtils.copyInputStreamToFile(fileStream, targetFile); //파일 저장
						if(cnt==1) {
							vo.setFile1(savedFileName);
						}if(cnt==2) {
							vo.setFile2(savedFileName);
						}if(cnt==3) {
							vo.setFile3(savedFileName);
						}if(cnt==4) {
							vo.setFile4(savedFileName);
						}if(cnt==5) {
							vo.setFile5(savedFileName);
						}
						cnt++;
						
					} catch (Exception e) {
						//파일삭제
						FileUtils.deleteQuietly(targetFile);	//저장된 현재 파일 삭제
						e.printStackTrace();
						break;
					}
				}
				service.imgBoardInsert(vo);
				service.imgFileInsert(vo);
				System.out.println(vo);
				strResult = "{ \"result\":\"OK\" }";
			}
			// 파일 아무것도 첨부 안했을때 탄다.(게시판일때, 업로드 없이 글을 등록하는경우)
			else
				strResult = "{ \"result\":\"OK\" }";
		}catch(Exception e){
			e.printStackTrace();
		}
		return strResult;
//		return "reidrect:imgBoardList";
				
	}
	
	//상세view
	@RequestMapping("/imgBoardView")
	public String imgBoardView(@RequestParam("seq") int seq, Model model) {
		Boast vo = service.imgBoardView(seq);
		List<Boast_cmt> cmt = cmtService.imgCmtList(seq);
		model.addAttribute("vo",vo);
		model.addAttribute("cmt",cmt);
		service.cntPlus(seq);
		return "jy/imgBoardView";
	}
	// 게시판 수정 폼
	@GetMapping("/imgBoardUpdateForm/{seq}")
	public String imgBoardUpdateForm(Model model,@PathVariable int seq) {
		Boast vo = service.imgBoardView(seq);
		model.addAttribute("vo", vo);
		return "jy/imgBoardUpdate";
	}
	
	//게시판 수정
	@PostMapping("/imgBoardUpdate")
	public String imgBoardUpdate(Boast vo) {
		System.out.println(vo);
		service.imgBoardUpdate(vo);
		
		return "redirect:imgBoardList";
	}
	
	// 게시판 삭제
	@GetMapping("/imgBoardDelete/{seq}")
	public String imgBoardDelete(@PathVariable int seq) {
		service.imgBoardDelete(seq);
		return "redirect:/imgBoardList";
	}
	
	
}
	
