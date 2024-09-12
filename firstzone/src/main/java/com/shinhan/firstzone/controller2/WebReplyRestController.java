package com.shinhan.firstzone.controller2;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.repository3.WebReplyService;
import com.shinhan.firstzone.vo4.WebReplyDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController //일단 테스트용으로 많이 쓰임
@RequestMapping("/replies")
@RequiredArgsConstructor //final인 field를 @Autowired
@Tag(name = "댓글list", description = "여기에서는 webReply CRUD작성됨")
public class WebReplyRestController {
	final WebReplyService replyService;

	
	@GetMapping("/list/{bno}")
	List<WebReplyDTO> list(@PathVariable("bno") Long bno) {
		return replyService.getList(bno);
	}
	
	@PostMapping("/register")
	Long insert(@RequestBody WebReplyDTO dto) {
		return replyService.register(dto);
	}
	
	@PutMapping("/modify")
	String update(@RequestBody WebReplyDTO dto) {
		replyService.modify(dto);
		return "success";
	}
	
	@DeleteMapping("/delete/{rno}")
	String delete(@PathVariable("rno") Long rno) {
		System.out.println(rno);
		replyService.delete(rno);
		return "success";
	}
}
