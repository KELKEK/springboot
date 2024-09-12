package com.shinhan.firstzone.vo2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "t_profile")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity //JPA가 관리
public class ProfileEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long fno;
	String fname;
	boolean currentYn;
	
	//여러 개의 profile은 하나의 Member 소유이다.
	@ManyToOne //하나의 Member는 여러 개의 Profile을 갖는다.
	MemberEntity member;
	//칼럼 이름은 filed이름_키field
	//member_mid 칼럼이 생성된다.
}
