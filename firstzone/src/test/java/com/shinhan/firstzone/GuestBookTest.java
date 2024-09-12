package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.shinhan.firstzone.repository.GuestBookRepository;
import com.shinhan.firstzone.service.GuestBookService;
import com.shinhan.firstzone.vo2.GuestBookDTO;
import com.shinhan.firstzone.vo2.GuestBookEntity;
import com.shinhan.firstzone.vo2.QGuestBookEntity;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class GuestBookTest {
	
	@Autowired
	GuestBookRepository gRepo;
	
	@Autowired
	GuestBookService gService;
	
	@Test
	void f5() {
		String type = "tcw"; //t->title, c->content, w->writer, tc->title, content
		String keyword = "요일";
		
//		String[] arr = type.split("");
//		System.out.println(Arrays.toString(arr));
		//t, c, w로 분리
		String[] arr = type.split("");
		BooleanBuilder builder = new BooleanBuilder();
		QGuestBookEntity entity = QGuestBookEntity.guestBookEntity;
		
		BooleanExpression expression = entity.gno.gt(0L); // gno > 0
		builder.and(expression); // select b from GuestBookEntity b where gno > 0 이라는 표현 생성
		
		BooleanBuilder builder2 = new BooleanBuilder();
		if(type.contains("t")) {
			builder2.or(entity.title.contains(keyword));
		}
		if(type.contains("c")) {
			builder2.or(entity.content.contains(keyword));
		}
		if(type.contains("w")) {
			builder2.or(entity.writer.contains(keyword));
		}
		builder.and(builder2); //and ( title like ? or content like ? or writer like ?) , '요일'이라는 단어가 포함되어있는가??
		System.out.println(builder);
		gRepo.findAll(builder).forEach(entity2->{ //메서드 내 다른 곳에서 entity를 쓰고 있는 곳이 있어서 여기에선 entity2라 씀
			log.info(entity2);
		});
	}
	
	//동적 SQL의 처리
	//@Test
	void f4() {
		String aa = "요일";
		String bb = "사용자2";
		BooleanBuilder builder = new BooleanBuilder();
		QGuestBookEntity book = QGuestBookEntity.guestBookEntity; //객체가 하나 들어옴
		//여기까진 select * from GuestBookEntity가 되어있다고 보면 됨
		
		builder.and(book.title.like("%" + aa + "%")); //aa는 그냥 변수
		builder.and(book.writer.eq(bb));
		builder.and(book.gno.gt(0L));
		//builder = where guestBookEntity.title like '%요일%' and guestBookEntity.writer = '작성자2' and guestBookEntity.gno > 0
		
		System.out.println(builder);
		gRepo.findAll(builder).forEach(entity->{
			log.info(entity);
		});
	}
	
	
	//@Test
	void f3() {
		List<GuestBookDTO> list = gService.readAll();
		list.forEach(dto->{
			log.info(dto);
		});
	}

	
	//@Test
	void f2() {
		//Entity -> DTO 반대경우
		GuestBookEntity entity = gRepo.findById(1L).orElse(null);
		if(entity!=null) {
			GuestBookDTO dto = GuestBookDTO.builder()
					.gno(entity.getGno())
					.title(entity.getTitle())
					.content(entity.getContent())
					.writer(entity.getWriter())
					.regDate(entity.getRegDate())
					.modDate(entity.getModDate())
					.build();
			log.info(dto);
		}
	}
	
	
	//@Test
	void f1() {
		//DTO -> Entity DTO가 들어오면 Entity로 바꾸기 (Browser -> Java)
		GuestBookDTO dto = GuestBookDTO.builder()
							.title("타이틀")
							.content("내용")
							.writer("작성자")
							.build();
		
		GuestBookEntity entity = GuestBookEntity.builder()
									.title(dto.getTitle())
									.content(dto.getContent())
									.writer(dto.getWriter())
									.build();
		GuestBookEntity updateBook = gRepo.save(entity);
		log.info(updateBook);
	}
	
	//@Test
	void select3() {
		gRepo.findByRegDateIsNull2(13L).forEach(entity->{
			log.info(entity);
		});
	}
	
	
	//등록일이 NULL인 data조회
	//@Test
	void select2() {
		gRepo.findByRegDateIsNull3().forEach(entity->{
			log.info(entity);
		});
	}
	
	
	//@Test
	void select() {
		gRepo.findAll().forEach(entity->{   //new Consumer<GuestBookEntity>()쓰는 것보다 편안
			log.info(entity);
		});
	}
	
	//@Test
	void insert() {
		IntStream.rangeClosed(21, 25).forEach(i->{
			GuestBookEntity entity = GuestBookEntity.builder()
					.title("화요일" + i)
					.content("집에 보내줘")
					.writer("사용자" + i)
					.build();//new로 객체를 생성하는 다른 방법들도 있지만 이게 더 깔끔하다 하심
			gRepo.save(entity); //@EnableJpaAuditing를 추가 안하면 추상클래스로 지정한 regdate, moddate가 감지되지 않아 NULL값으로 생성된다.
		});
	}
	
	
}
