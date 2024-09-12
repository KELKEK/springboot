package com.shinhan.firstzone.vo3;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@ToString (exclude = {"replies"})
@EqualsAndHashCode(of= {"bno"})
@Entity
@Table(name = "t_freeboard")
public class FreeBoardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //시퀀스같은 역할
	private Long bno;
	private String title;
	private String writer;
	private String content;
	@CreationTimestamp //insert 시에 자동으로 들어가게 하는 역할
	private Timestamp regdate;
	@UpdateTimestamp //insert, update 시 자동으로 반영
	private Timestamp updatedate;
	
	@JsonIgnore
	
	//하나의 board에 댓글이 여러 개 있다. (부모)
	//@BatchSize(size = 100) // N+1 방법 중 하나. Join 사용 시 대상entity에 갯수만큼 상대 entity가 select되는 문제를 예방(in을 사용하여 Batchsize만큼 한꺼번에 select 가능(OneToMany에서만 가능)
	@OneToMany(mappedBy = "board", cascade = 
			CascadeType.ALL, //모든 전의
			fetch = FetchType.LAZY) //즉시로딩
	List<FreeReplyEntity> replies;
}
