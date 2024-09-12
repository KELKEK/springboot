package com.shinhan.firstzone.repository3;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo4.WebBoardEntity;
//join : n + 1문제
//joinfetch : n + 1문제 해결(select문이 여러 번 나오는 문제 해결)
//1, JpaRepository, QuerydslP~ : 기본제공함수
//2, 규칙이 있는 함수 정의 : findby~~
//3, JPQL : select -> @Query DML -> @Modifying @Query 
public interface WebBoardRepository extends JpaRepository<WebBoardEntity, Long>
											, QuerydslPredicateExecutor<WebBoardEntity>{
	Page<WebBoardEntity> findByWriter(MemberEntity member, Pageable paging);
	//Page<WebBoardEntity> -> List<WebBoardEntity>
}
