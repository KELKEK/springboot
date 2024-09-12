package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.shinhan.firstzone.repository.BoardRepository;
import com.shinhan.firstzone.vo.BoardEntity;

import lombok.extern.log4j.Log4j2;
@Log4j2
@SpringBootTest //Junit(단위Test)으로 test
public class BoardCRUDTest {
	@Autowired
	BoardRepository bRepo;
	
	@Test
	void f9() {
		//1, bRepo.jpqlTest1("요일", "짝수").forEach(b->log.info(b));
		//jpqlTest2, jpqlTest3도 같게 적용시키면 됨
		// bRepo.jpqlTest2("요일", "짝수").forEach(b->log.info(b));
		//bRepo.jpqlTest4("요일", "짝수").forEach(arr->log.info(Arrays.toString(arr)));
		bRepo.jpqlTest5("%요일%", "%제발%").forEach(b->log.info(b));
		//db가 다를 수도 있으므로 이렇게 여기에 설정해놓는게 낫다.
	}
	
	//@Test
	void f8() {
		//Pageable pageable = PageRequest.of(0, 5); //(pageNumber, Entity갯수)
		Pageable pageable = PageRequest.of(1, 5, Sort.Direction.DESC, new String[]{"writer", "bno"});
		// order by writer desc, bno desc limit ?, ?
		// 조금 다르게 썼지만, 아래도 같은 표현이다.
		// = Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").descending());
		// = Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "bno");
		
		Page<BoardEntity> result = bRepo.findAll(pageable);
		
		List<BoardEntity> blist = result.getContent();
		
		log.info("getNumber : " + result.getNumber());
		log.info("getSize : " + result.getSize());
		log.info("getTotalElements : " + result.getTotalElements());
		log.info("getTotalPages : " + result.getTotalPages());
		blist.forEach(board->log.info(board));
	}
	
	//@Test
	void f7() {
		//where bno > 10 order by bno desc ==> 15개(11~26)
		//0 : 28~24
		//1 : 23~19
		//2 : 18~14
		//3 : 13~10
		Pageable pageable = PageRequest.of(1, 5); //(pageNumber, Entity갯수)
		bRepo.findByBnoGreaterThanOrderByBnoDesc(10L, pageable).forEach(b->{
			log.info(b);
		});
	}
	
	//@Test
	void f6() {
		bRepo.findByBnoBetweenOrderByBnoDesc(10L, 15L).forEach(b->{
			log.info(b);
		});
	}
	
	//@Test
	void f5() {
		bRepo.findByBnoBetween(10L, 15L).forEach(b->{
			log.info(b);
		});
	}
	
	//@Test
	void f4() {
		bRepo.findByTitleContaining("1").forEach(b->{
			log.info(b);
		});
	}
	
	//@Test
	void f3() {
		bRepo.findByTitleEndingWith("1").forEach(b->{
			log.info(b);
		});
	}
	
	//@Test
	void f2() {
		bRepo.findByTitleStartingWith("월").forEach(b->{
			log.info(b);
		});
	}
	
	//@Test
	void f1() {
		bRepo.findByWriter("NADA1").forEach(b->{
			log.info(b);
		});
	}
	
	//건수 확인
	//@Test
	void boardCount() {
		Long cnt = bRepo.count(); //select count(*) from t_board;과 같다.
		log.info(cnt+"건");
	}
	
	//@Test
	void delete() {
		bRepo.deleteById(2L);
		bRepo.findById(2L).ifPresentOrElse(b->{
			log.info(b);
		}, ()->{
			log.info("Not found");
		});
	}
	
	//@Test
	void update() {
		bRepo.findById(1L).ifPresent(board->{
			log.info("before : " + board);
			board.setContent("~~~수정~~~");
			board.setTitle("springboot");
			board.setWriter("manager");
			BoardEntity updateBoard = bRepo.save(board);
			log.info(updateBoard);
		});
	}
	
	
	//@Test
	void detail() {
		bRepo.findById(5L).ifPresentOrElse(board->{
			log.info(board);
		}, ()->{
			log.info("해당 데이터가 존재하지 않습니다.");
		});
	}
	
	//@Test
	void select() {
		bRepo.findAll().forEach(board->{
			log.info(board);
		});
	}
	
	//@Test
	void insert() {
		IntStream.range(11, 20).forEach(i->{
			BoardEntity entity = BoardEntity.builder()
					.title("금요일"+i)
					.content("빨리 금욜이 오면 좋겠다."+ (i%2==0?"짝수":"홀수"))
					.writer("NADA"+i%5)
					.build();
			bRepo.save(entity); //insert를 10번 적용
		});
	}
}
