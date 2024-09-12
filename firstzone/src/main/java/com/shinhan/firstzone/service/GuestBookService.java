package com.shinhan.firstzone.service;

import java.util.List;
import java.util.stream.Collectors;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.shinhan.firstzone.vo2.GuestBookDTO;
import com.shinhan.firstzone.vo2.GuestBookEntity;
import com.shinhan.firstzone.vo2.QGuestBookEntity;

public interface GuestBookService {
	//기본적인 CRUD
	//1, create
	void create(GuestBookDTO dto);
	//2, Read
	List<GuestBookDTO> readAll();
	GuestBookDTO readById(Long gno);
	public List<GuestBookDTO> getSearch(String type, String keyword);
	//3, Update
	void update(GuestBookDTO dto);
	//4, Delete
	void delete(Long gno);
	
	
	//implement가 구현부, List로 치면 Arraylist, Vector, Linkedlist같은 구현부이다. 다 같이 쓰는 공통적인 부은 Service에서 관리하는 것이 낫다.
	//Entity -> DTO
	default GuestBookDTO entityToDTO(GuestBookEntity entity) {
		GuestBookDTO dto = GuestBookDTO.builder()
				.gno(entity.getGno())
				.title(entity.getTitle())
				.content(entity.getContent())
				.writer(entity.getWriter())
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.build();
		return dto;
	}
	//DTO -> Entity
	default GuestBookEntity dtoToEntity(GuestBookDTO dto) {
		GuestBookEntity entity = GuestBookEntity.builder()
				.title(dto.getTitle())
				.content(dto.getContent())
				.writer(dto.getWriter())
				.build();
		return entity;
	}
	//이러한 DTO->Entity와 같은 기능은 Service에서 수행한다.
	

}
