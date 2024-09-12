package com.shinhan.firstzone.vo4;

import java.sql.Timestamp;

import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.shinhan.firstzone.vo2.MemberEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


//DTO : Data Transfer Object
//VO : Value Object
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter@Setter
@ToString(exclude = "replies") //양방향일 때, Board에 대한 것만 찍힐 수 있게 exclude를 설정함
@Entity
@Table(name = "t_webboard")
public class WebBoardEntity {
	//springMVC수업
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	private String title;
	@ManyToOne
	private MemberEntity writer; //writer_mid
	private String content;
	@CreationTimestamp //insert 시에 자동으로 들어가게 하는 역할
	private Timestamp regdate;
	@UpdateTimestamp //insert, update 시 자동으로 반영
	private Timestamp updatedate;
	
	@OneToMany(mappedBy = "board", cascade = 
			CascadeType.ALL, //모든 전의
			fetch = FetchType.LAZY) //즉시로딩, LAZY로 불러오려면 getter를 써야한다., 둘 다 EAGER일 경우 계속 서로를 부르게 되어 무한루프가 생긴다.
	//삭제를 위해 fetch 타입을 LAZY로 바꿈.
	List<WebReplyEntity> replies;
}
