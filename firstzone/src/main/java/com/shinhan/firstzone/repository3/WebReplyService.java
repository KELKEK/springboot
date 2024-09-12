package com.shinhan.firstzone.repository3;

import java.util.List;

import com.shinhan.firstzone.vo4.WebBoardEntity;
import com.shinhan.firstzone.vo4.WebReplyDTO;
import com.shinhan.firstzone.vo4.WebReplyEntity;

public interface WebReplyService {

	//CRUD
	//Create
	Long register(WebReplyDTO dto);
	
	//Read
	List<WebReplyDTO> getList(Long bno);
	
	//update
	void modify(WebReplyDTO dto);
	
	//Delete
	void delete(Long rno);
	
	//DTO -> Entity (DB에 반영하기 위함)
	//insert, update 시 사용
	default WebReplyEntity dtoToEntity(WebReplyDTO dto){ // interface의 구현 접근지정자는 default
		WebBoardEntity board = WebBoardEntity.builder().bno(dto.getBoardBno()).build();
		WebReplyEntity entity = WebReplyEntity.builder()
				.rno(dto.getRno())
				.reply(dto.getReply())
				.replyer(dto.getReplyer())
				.board(board)
				.build();
		return entity;
	}
	
	//Entity -> DTO (Data 전송을 위함, controller, service, view에서 작업)
	//select
	default WebReplyDTO entityToDTO(WebReplyEntity entity) {
		WebReplyDTO dto = WebReplyDTO.builder()
				.rno(entity.getRno())
				.reply(entity.getReply())
				.replyer(entity.getReplyer())
				.boardBno(entity.getBoard().getBno())
				.regdate(entity.getRegdate())
				.updatedate(entity.getUpdatedate())
				.build();
		return dto;
	}
}
