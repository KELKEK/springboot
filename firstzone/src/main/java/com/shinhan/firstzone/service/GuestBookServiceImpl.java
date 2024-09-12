package com.shinhan.firstzone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.shinhan.firstzone.repository.GuestBookRepository;
import com.shinhan.firstzone.vo2.GuestBookDTO;
import com.shinhan.firstzone.vo2.GuestBookEntity;
import com.shinhan.firstzone.vo2.QGuestBookEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 필수 field를 constructor를 통해 injection
public class GuestBookServiceImpl implements GuestBookService{
	
//	@Autowired
//	GuestBookRepository gRepo;와 바로 아래는 같은 방식
	final GuestBookRepository gRepo; // final은 반드시 넣어야 한다.
	final GuestBookRepository gRepo2;
	

	@Override
	public void create(GuestBookDTO dto) {
		// TODO Auto-generated method stub
		GuestBookEntity entity = dtoToEntity(dto);
		gRepo.save(entity);
	}

	@Override
	public List<GuestBookDTO> readAll() {
		// TODO Auto-generated method stub
		List<GuestBookEntity> entityList = (List<GuestBookEntity>) gRepo.findAll();
		List<GuestBookDTO> dtoList = entityList.stream()
				.map(entity->entityToDTO(entity))
				.collect(Collectors.toList());//collect를 통해 한건한건을 리스트로 바꿔줌
		return dtoList;
	}

	@Override
	public GuestBookDTO readById(Long gno) {
		// TODO Auto-generated method stub
		GuestBookEntity entity = gRepo.findById(gno).orElse(null);
		return entityToDTO(entity);
	}

	@Override
	public void update(GuestBookDTO dto) {
		// TODO Auto-generated method stub
		gRepo.findById(dto.getGno()).ifPresent(book->{
			book.setContent(dto.getContent());
			book.setTitle(dto.getTitle());
			book.setWriter(dto.getWriter());
			gRepo.save(book);
		});		
	}

	@Override
	public void delete(Long gno) {
		// TODO Auto-generated method stub
		gRepo.deleteById(gno);
	}
	
	//조회 조건을 갖고 조회
	public List<GuestBookDTO> getSearch(String type, String keyword) {
		//t->title, c->content, w->writer, tc->title,content, tcw->title,content,writer
		
		BooleanBuilder builder  = new BooleanBuilder();
		QGuestBookEntity entity = QGuestBookEntity.guestBookEntity;
		BooleanExpression expression = entity.gno.gt(0L); //gno > 0
		builder.and(expression); //select b from GuestBookEntity b where b.gno > 0		
		BooleanBuilder builder2  = new BooleanBuilder();
		if(type.contains("t")) {
			builder2.or(entity.title.contains(keyword));
		}
		if(type.contains("c")) {
			builder2.or(entity.content.contains(keyword));
		}
		if(type.contains("w")) {
			builder2.or(entity.writer.contains(keyword));
		}
		builder.and(builder2); //and ( title like ? or  content like ? or  writer like ?) 문장이 builder 뒤에 붙음
		 
		
		List<GuestBookEntity> entityList = (List<GuestBookEntity>)
				gRepo.findAll(builder);
		List<GuestBookDTO> dtoList = 
				entityList.stream().map(e->entityToDTO(e))
				                   .collect(Collectors.toList());
		return dtoList;
		
		
		 
	}
}
