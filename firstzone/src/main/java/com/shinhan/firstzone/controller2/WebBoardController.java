package com.shinhan.firstzone.controller2;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shinhan.firstzone.repository3.WebBoardService;
import com.shinhan.firstzone.security.MemberService;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo4.PageRequestDTO;
import com.shinhan.firstzone.vo4.WebBoardDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller //요청이 들어오면 서비스를 수행하고 data가 page에 return됨(자바 내에서 만든 page로 return, page는 thymeleaf template 사용중)
//@RestController // 요청이 들어오면 서비스를 수행하고 data가 return (ReactJS에서 만든 뷰로 넘기는 용도로 사용될 예정)
@RequestMapping("/webboard")
@RequiredArgsConstructor //필수칼럼은 생성자 자동생성
@Log4j2
public class WebBoardController {
	final WebBoardService boardService;
	final MemberService memService;
	
	@GetMapping("/register")
	void registerForm() {
		//templates는 application.properties에서 설정함
		//forward : templates/webboard/register.html
	}
	
	@PostMapping("/register")
	String register(WebBoardDTO dto, RedirectAttributes attr,
			Principal principal, Authentication authentication, HttpSession session) {
		
		System.out.println("방법1 principal : " + principal);
	    Object principal2 =	authentication.getPrincipal();
	    System.out.println("방법2 authentication : " + principal2);
	    
	    Object principal3 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    System.out.println("방법3 authentication : " + principal3);
	    
	    UserDetails userDetails = (UserDetails) principal3;
	    System.out.println("userDetails : " + userDetails);
	    //****로그인 한 멤버 id가 Board의 mid로 입력된다.
	    String mid = principal.getName();
	    //
	    UserDetails userDetails2 = memService.loadUserByUsername(mid);
	    System.out.println("방법4 loadUserByUsername : " + userDetails2);
	    
	    MemberEntity loginUser = (MemberEntity)session.getAttribute("user");
	    System.out.println("방법5 session에서 읽기 : " + loginUser);
	    
	    dto.setMid(mid);
		
		log.info(dto);
		Long bno = boardService.register(dto);
		log.info(bno + "번 게시글이 등록됨");
		attr.addFlashAttribute("msg", "등록완료"); //등록 메시지 alert창
		return "redirect:list";
	}
	
	@GetMapping("/list")
	String getList(Model model, PageRequestDTO pageRequestDTO) {
		//List<WebBoardDTO> blist = boardService.getList();
		//model.addAttribute("blist", blist);
		
		if(pageRequestDTO.getSize() == 0) { //페이징 구현
			pageRequestDTO.setPage(1);
			pageRequestDTO.setSize(10);
//			pageRequestDTO.setType("ctw"); //content, title, writer
//			pageRequestDTO.setKeyword("집"); //페이지에 맞는 단어를 적으면 조건에 맞게 검색된다.
		}
		
		model.addAttribute("result", boardService.getList(pageRequestDTO));
		model.addAttribute("pageRequestDTO", pageRequestDTO);
		return "webboard/boardlist";
	}
	
	@GetMapping("/detail")
	String detail(Long bno, Model model) {
		WebBoardDTO board = boardService.selectById(bno);
		model.addAttribute("board", board);
		return "webboard/update";
	}
	
	@PostMapping("/update")
	String update(WebBoardDTO dto) {
		log.info(dto);
		boardService.modify(dto);
		log.info(dto.getBno() + "번 게시글이 수정됨");
		return "redirect:list";
	}
	
	@PostMapping("/delete")
	String delete(Long bno) {
		boardService.delete(bno);
		return "redirect:list";
	}
}
