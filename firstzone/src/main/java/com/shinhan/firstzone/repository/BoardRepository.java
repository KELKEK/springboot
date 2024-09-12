package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.firstzone.vo.BoardEntity;

//Repository 설계
//JPA -> interface기반으로 구현class를 만듦
public interface BoardRepository extends CrudRepository<BoardEntity, Long>,
										 PagingAndSortingRepository<BoardEntity, Long> {//Long은 PK의 타입을 적음

	//1, 기본 CRUD는 제공 : findAll(), findById(), save(), count(), deleteById()
	//2, 규칙에 맞는 함수를 정의
	//https://docs.spring.io/spring-data/jpa/docs/2.5.1/reference/html/#jpa.query-methods
	
	//CrudRepository : 기본 CRUD
	//PagingAndSorting : 페이징
	List<BoardEntity> findByWriter(String writer); //특정 writer가 만든 것만 다 나와라
	List<BoardEntity> findByTitleStartingWith(String title); //title로 시작되는 것 'A%' 
	List<BoardEntity> findByTitleEndingWith(String title); //title로 끝나는 것 '%A'
	List<BoardEntity> findByTitleContaining(String title); // title이 포함되는 것 '%A%'
	List<BoardEntity> findByBnoBetween(Long bno1, Long bno2);
	List<BoardEntity> findByBnoBetweenOrderByBnoDesc(Long bno1, Long bno2);
	List<BoardEntity> findByContentLikeAndBnoBetweenOrderByBnoDesc(String content, Long bno1, Long bno2); //이정도도 가능
	
	//paging 추가
	//where bno > ? order by bno desc
	List<BoardEntity> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable paging);// paging추가

	//3, JPQL(JPA Query Language)이용하기 : 복잡한 SQL문 생성 시 이용한다.
	//주의 : nativeQuery와 비슷하지만 같지는 않다. ex) select * from('*' 불가)
	@Query("select b from BoardEntity b "
			+ "where b.title like %?1% and b.content like %?2% order by bno desc " )
	List<BoardEntity> jpqlTest1(String title, String content);
	
	@Query("select b from BoardEntity b "
			+ "where b.title like %:tt% and b.content like %:cc% order by bno desc " )
	List<BoardEntity> jpqlTest2(@Param("tt") String title, @Param("cc") String content);
	
	@Query("select b from #{#entityName} b "
			+ "where b.title like %:tt% and b.content like %:cc% order by bno desc " )
	List<BoardEntity> jpqlTest3(@Param("tt") String title, @Param("cc") String content);
	
	
	@Query("select b.title, b.content, b.writer from #{#entityName} b "
			+ "where b.title like %:tt% and b.content like %:cc% order by bno desc " ) // #{#entityName} = 여기에선 BoardEntity
	List<Object[]> jpqlTest4(@Param("tt") String title, @Param("cc") String content);

	@Query(value = "select * from t_board as b "
			+ "where b.title like ?1 and b.content like ?2 order by bno desc "
			, nativeQuery = true)
	List<BoardEntity> jpqlTest5(@Param("tt") String title, @Param("cc") String content);
}


