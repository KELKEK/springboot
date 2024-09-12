package com.shinhan.firstzone.repository3;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo4.PageRequestDTO;
import com.shinhan.firstzone.vo4.PageResultDTO;
import com.shinhan.firstzone.vo4.QWebBoardEntity;
import com.shinhan.firstzone.vo4.WebBoardDTO;
import com.shinhan.firstzone.vo4.WebBoardEntity;

public interface WebBoardService {
	//CRUD작업 제공
	//1, 입력
	Long register(WebBoardDTO dto);
	
	//2, 조회 - 여러 건이 return
	List<WebBoardDTO> getList();
	PageResultDTO<WebBoardDTO, WebBoardEntity> getList(PageRequestDTO pageRequestDTO);
	WebBoardDTO selectById(Long bno);
	
	//3, 수정
	void modify(WebBoardDTO dto);
	
	//4, 삭제
	void delete(Long bno);
	
	//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ아래는 공통적으로 적용하는 사항들ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	
	//5, 동적 SQL 만들기
	// t-> title, c -> content, w -> writer, tc -> title, content
	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QWebBoardEntity board = QWebBoardEntity.webBoardEntity;
		builder.and(board.bno.gt(0));
		//검색조건처리
		if(type==null) return builder;
		BooleanBuilder builder2 = new BooleanBuilder();
		switch (type) {
		case "t": builder2.and(board.title.like("%" + keyword + "%")); break;
		case "c": builder2.and(board.content.like("%" + keyword + "%")); break;
		case "w": builder2.and(board.writer.mname.like("%" + keyword + "%")); break;
		default: break;
		}
		builder.and(builder2);
		return builder;
		}

	//DTO -> Entity (DB에 반영하기 위함)
	//insert, update 시 사용
	default WebBoardEntity dtoToEntity(WebBoardDTO dto){ // interface의 구현 접근지정자는 default
		MemberEntity member = MemberEntity.builder().mid(dto.getMid()).build();
		WebBoardEntity entity = WebBoardEntity.builder()
				.bno(dto.getBno())
				.title(dto.getTitle())
				.content(dto.getContent())
				.writer(member)
				.build();
		return entity;
	}
	
	//Entity -> DTO (Data 전송을 위함, controller, service, view에서 작업)
	//select
	default WebBoardDTO entityToDTO(WebBoardEntity entity) {
		WebBoardDTO dto = WebBoardDTO.builder()
				.bno(entity.getBno())
				.title(entity.getTitle())
				.mid(entity.getWriter().getMid())
				.mname(entity.getWriter().getMname())
				.content(entity.getContent())
				.regdate(entity.getRegdate())
				.updatedate(entity.getUpdatedate())
				.replyCount(entity.getReplies().size())
				.build();
		return dto;
	}
}
