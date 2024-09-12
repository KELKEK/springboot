package com.shinhan.firstzone.controller2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shinhan.firstzone.repository2.FreeBoardRepository;
import com.shinhan.firstzone.vo2.MemberEntity;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller  //@RestController는 @ResponseBody + @Controller이므로 요청을 하면 결과값이 반영되어야 한다.
@RequestMapping("/coffee")
@RequiredArgsConstructor //자동으로 Autowired한 효과
public class CoffeeController {
	
	final FreeBoardRepository boardRepo; //이쪽으로 autowired함을 확정
	
	@GetMapping("/layout1")
	String f7() {
		return "layout/layout1";
	}
	
	@GetMapping("/exlayout1")
	String f6() {
		return "layout/exLayout1";
	}
	
	@GetMapping("/detail")
	void f5(Long bno, Model model) {
		model.addAttribute("board", boardRepo.findById(bno).get());//있을 수도 있고 없을 수도 있으므로 .get해줌
	}
	
	@GetMapping("/list")
	void f4(@RequestParam("keyword") String keyword2, Model model, HttpSession session) {
		System.out.println("keyword : " + keyword2);
		model.addAttribute("blist", boardRepo.findAll());
		MemberEntity member = MemberEntity.builder()
				.mid("zz")
				.mname("admin")
				.build();
		session.setAttribute("loginUser", member);
	}
	
	@GetMapping("/page3")
	public String f3(Model model) {
		model.addAttribute("menu", "아메리카노");
		MemberEntity member = MemberEntity.builder()
				.mid("zz")
				.mname("admin")
				.build();
		model.addAttribute("member", member);
		return "coffee/page1";
	}
	
	@GetMapping("/page2")
	public String f2() {
		return "coffee/page1";
	}
	
	@GetMapping("/page1")
	public void f1() {
		
	}
}
