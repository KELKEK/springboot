package com.shinhan.firstzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shinhan.firstzone.vo2.PDSFileEntity;
import java.util.List;


public interface PDSFileRepository  extends JpaRepository<PDSFileEntity, Long>{
	
	PDSFileEntity findByPdsfilename(String pdsfilename);
}
