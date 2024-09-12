package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shinhan.firstzone.vo2.PDSBoardEntity;

public interface PDSBoardRepository extends JpaRepository<PDSBoardEntity, Long> {

	// 2, 규칙에 맞는 함수 정의
	List<PDSBoardEntity> findByWriter(String writer);

	// 3, SQL 문법을 직접 쓰기, 특정 것들만 불러오고 싶을 때 주로 쓰임
	@Query("select b.pname, b.writer, f.pdsfilename " + " from #{#entityName} b left outer join b.files f "
			+ " where b.pid = ?1 order by b.pid ")
	public List<Object[]> getFilesInfo(long pid);

	@Query(value = "select f.pdsno, count(f.pdsno) from t_pdsboard b "
			+ " left outer join t_pdsfile f on(b.pid=f.pdsno) group by f.pdsno order by 1 ", nativeQuery = true)
	public List<Object[]> getFilesCount();

	//@Query는 기본적으로 Select만 지원. DML(insert, delete, update)을 원하면 @Modifying 추가
	@Modifying 
	@Query("UPDATE PDSFileEntity f set f. pdsfilename = ?2 WHERE f.fno = ?1 ") 
	int updatePDSFile(Long fno, String newFileName); 

}
