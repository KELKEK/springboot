package com.shinhan.firstzone.vo2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "t_guestbook")
@Entity //JPA가 관리하는 객체(DB의 객체와 JAVA의 객체를 Mapping)
@Builder //이걸 쓸 때는 AllArgsConstructor 어노테이션이 있어야 함
@NoArgsConstructor
@AllArgsConstructor
//@Data //getter/setter/toString/equalsandhashcode
@Getter@Setter
public class GuestBookEntity extends BaseEntity {
	
	@Id //PK
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gno;
	@Column(length = 100, nullable = false)
	private String title;
	@Column(length = 2000)
	private String content;
	@Column(length = 50)
	private String writer;
	
	@Override
	public String toString() {
		return "GuestBookEntity [gno=" + gno + ", title=" + title + ", content=" + content + ", writer=" + writer
				+ ", regDate=" + regDate + ", modDate=" + modDate + "]";
	}

	
	
}
