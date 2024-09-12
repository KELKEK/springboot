package com.shinhan.firstzone.vo2;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//하나의 Board에 첨부file이 여러 개 있다.
@Table(name = "t_pdsboard")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity //JPA가 관리
//@ToString(exclude = "files")
public class PDSBoardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long pid;
	String pname;
	String writer;
	
	//cascade : 전의, 나의 변화가 나와 연관관계가 이쓴 곳에 전의된다.(부모가 삭제되면 자식도 삭제된다.)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //fetch는 조회 시 따라오는 역할(Eager는 따라옴, Lazy는 따라오지 않음) , default는 lazy
	@JoinColumn(name = "pdsno")//PDSFileEntity의 테이블에 pdsno라는 칼럼이 생김//만약에 이걸 넣지 않고 Join칼럼이 생략되면 중간테이블이 생성된다.(PDSBoard키, PDSFileEntity키를 칼럼을 갖는 중간테이블 만들어짐) <- 불편함
	List<PDSFileEntity> files;
}
