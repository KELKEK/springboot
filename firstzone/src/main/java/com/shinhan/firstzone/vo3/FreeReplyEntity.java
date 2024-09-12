package com.shinhan.firstzone.vo3;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@ToString (exclude = {"board"}) //selectByReply에서 reply를 출력하기 위해 쓰임
@EqualsAndHashCode(of= {"rno"})
@Entity
@Table(name = "t_freereply")
public class FreeReplyEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	String reply; //댓글내용
	String replyer; //댓글작성자
	@CreationTimestamp //insert 시에 자동으로 들어가게 하는 역할
	private Timestamp regdate;
	@UpdateTimestamp //insert, update 시 자동으로 반영
	private Timestamp updatedate;
	
	
	//하나의 board에 댓글이 여러 개 있다.(자식)
	@ManyToOne(fetch = FetchType.EAGER) //DB의 칼럼은 => board_bno
	private FreeBoardEntity board;
}
