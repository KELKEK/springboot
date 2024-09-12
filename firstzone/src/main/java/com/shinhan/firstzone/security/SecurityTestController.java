package com.shinhan.firstzone.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/security")
public class SecurityTestController {

	@GetMapping("/all")
	public void f1() {
		log.info("모든 사용자가 접근 가능한 페이지");
	}
	@GetMapping("/user")
	public void f2() {
		log.info("유저 권한을 가진 member가 접근 가능한 페이지");
	}
	@GetMapping("/admin")
	public void f3() {
		log.info("관리자 권한을 가진 member가 접근 가능한 페이지");
	}
	@GetMapping("/manager")
	public void f4() {
		log.info("매니저 권한을 가진 member가 접근 가능한 페이지");
	}
}
