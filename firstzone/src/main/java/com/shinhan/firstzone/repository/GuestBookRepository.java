package com.shinhan.firstzone.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shinhan.firstzone.vo.BoardEntity;
import com.shinhan.firstzone.vo2.GuestBookEntity;

public interface GuestBookRepository
	extends CrudRepository<GuestBookEntity, Long>,
	PagingAndSortingRepository<GuestBookEntity, Long>,
	QuerydslPredicateExecutor<GuestBookEntity>{
	//1, 기본CRUD함수 제공, PagingAndSorting 가능
	//2, 규칙에 맞는 함수 추가 가능
	List<GuestBookEntity> findByRegDateIsNull();
	//3, @Query 추가 가능
	@Query("select b from #{#entityName} b where b.regDate is null and b.gno > ?1")
	List<GuestBookEntity> findByRegDateIsNull2(Long gno);
	//4, @Query, nativeQuery 가능
	@Query(value = "select * from t_guestbook b where b.regDate is null", nativeQuery = true)
	List<GuestBookEntity> findByRegDateIsNull3();
}
