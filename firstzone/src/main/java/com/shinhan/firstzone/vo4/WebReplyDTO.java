package com.shinhan.firstzone.vo4;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WebReplyDTO {

	private Long rno;
	String reply; //댓글내용
	String replyer; //댓글작성자
	private Timestamp regdate;
	private Timestamp updatedate;
	private Long boardBno;
}
