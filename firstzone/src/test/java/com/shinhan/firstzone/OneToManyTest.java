package com.shinhan.firstzone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository.PDSBoardRepository;
import com.shinhan.firstzone.repository.PDSFileRepository;
import com.shinhan.firstzone.vo2.PDSBoardEntity;
import com.shinhan.firstzone.vo2.PDSFileEntity;

@SpringBootTest //Test하기 전에 꼭!!!!!!!!!!!!! 적어주기
public class OneToManyTest {
	@Autowired
	PDSFileRepository fileRepo;
	@Autowired
	PDSBoardRepository boardRepo;
	
	@Test
	void selectBoard() {
		Long pid = 1L;
		PDSBoardEntity board = boardRepo.findById(pid).orElse(null);
		if(board == null) return;
		System.out.println(board); //lazy의 경우 tostring이 오타남
		System.out.println(board.getPname());		
		System.out.println(board.getWriter());
		System.out.println(board.getFiles());//리스트가 나올 것
		System.out.println(board.getFiles().size());//리스트의 사이즈가 나올 것
		
	}
	
	
	//조회
	//@Test
	void selectFile() {
		Long fno = 10L;
		PDSFileEntity file = fileRepo.findById(fno).orElse(null);
		System.out.println(file);
	}
	
	//@Test
	void insert2() {
		//board:10건
		IntStream.rangeClosed(1, 10).forEach(j->{
			List<PDSFileEntity> files = new ArrayList<>();
			//file: 5건 * 10
			IntStream.rangeClosed(1, 5).forEach(i->{
				PDSFileEntity file = PDSFileEntity.builder()
						.pdsfilename("스프링Book-" + j + "-" + i + ".ppt")
						.build();
				files.add(file);
			});
			
			PDSBoardEntity board = PDSBoardEntity.builder()
					.pname("springboot-JPA")
					.writer("준성")
					.files(files)
					.build();
			boardRepo.save(board);
		});
	}
	
	//@Test
	void insert() {
		List<PDSFileEntity> files = new ArrayList<>();
		IntStream.rangeClosed(1, 5).forEach(i->{
			PDSFileEntity file = PDSFileEntity.builder()
					.pdsfilename("스프링Book-" + "-" + i + ".ppt")
					.build();
			files.add(file);
		});
		PDSBoardEntity board = PDSBoardEntity.builder()
				.pname("springTest")
				.writer("누군가")
				.files(files)
				.build();
		boardRepo.save(board);
	}
	
}
