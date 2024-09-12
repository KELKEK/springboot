package com.shinhan.firstzone.vo2;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "t_member")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity //JPA가 관리
public class MemberEntity {
	@Id //Primary Key
	String mid;
	String mpassword;
	String mname;
	@Enumerated(EnumType.STRING) //기본은 순서original이 입력됨
	MemberRole mrole; //role에는 USER, MANAGER, ADMIN 3개의 권한만 있다 <- Enum으로 만들어보기
}
