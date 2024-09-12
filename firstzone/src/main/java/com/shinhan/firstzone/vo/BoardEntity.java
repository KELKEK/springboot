package com.shinhan.firstzone.vo;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter setter다 들어감
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity //JPA가 관리하는 대상 ...DB의 table과 Mapping할 때 사용	
@Table(name = "t_board") // 엔티티와 매핑할 데이터베이스 테이블의 이름을 지정
public class BoardEntity {
	@Id //PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	@Column(length = 50, nullable = false)
	private String title;
	
	@Column(length = 2000)
	private String content;
	private String writer;
	
	@Column(name = "regdate")
	@CreationTimestamp //insert 시 입력
	private Timestamp regDate;
	@Column(name = "updatedate")
	@UpdateTimestamp //insert 시 입력, 수정 시 변경
	private Timestamp updateDate;
	
}
