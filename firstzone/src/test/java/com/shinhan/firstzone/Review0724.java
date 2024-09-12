package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.repository.PDSBoardRepository;
import com.shinhan.firstzone.repository.PDSFileRepository;
import com.shinhan.firstzone.repository.ProfileRepository;
import com.shinhan.firstzone.vo2.PDSBoardEntity;
import com.shinhan.firstzone.vo2.PDSFileEntity;

import jakarta.transaction.Transactional;

@Commit
@SpringBootTest
public class Review0724 {
	
	//ManyToOne Test
	@Autowired
	MemberRepository mRepo;
	@Autowired
	ProfileRepository pRepo;
	
	//OneToMany Test
	@Autowired
	PDSBoardRepository boardRepo;
	@Autowired
	PDSFileRepository fileRepo;
	
	//삭제
	//board의 10번을 삭제(자식이 있는 부모를 삭제) <- Cascade 설정이므로 자식도 함꼐 삭제가 될 수 있음, 그렇지 않을 경우 둘 다 삭제하는 코드를 짜야함.
	@Test
	void deleteFile2() {
		boardRepo.deleteById(10L);
	}
	
	//파일의 번호를 아는 상태에서 파일 삭제
	//@Test
	void deleteFile() {
		fileRepo.deleteById(55L);
	}
	
	
	//boardRepo에서 PDSfile을 수정하기, 직접SQL 작성
	//@Transactional //이것만 쓰면 Test 환경에서는 실행 후에 롤백이 되어버림 <- commit을 추가해야함 
	//@Test
	void updateFile() {
		boardRepo.updatePDSFile(30L, "여긴어디나는누구");
		System.out.println("수정완료");
	}

	//PDSBoard의 이름 변경 : 본래 이름 뒤에 pid 붙이기
	//@Test
	void update() {
		boardRepo.findAll().forEach(board->{
			String name = board.getPname() + "-" + board.getPid();
			board.setPname(name);
			boardRepo.save(board);
		});
	}
	
	//@Test
	void selectBoard5() {
		boardRepo.getFilesCount().forEach(arr->System.out.println(Arrays.toString(arr)));
	}

	//@Test
	void selectBoard4() {
		long pid = 4L;
		List<Object[]> blist = boardRepo.getFilesInfo(pid);
		blist.forEach(arr->System.out.println(Arrays.toString(arr)));
	}
	
	//입력
	//board의 file추가
	//@Test
	void insertBoard() {
		PDSBoardEntity board = boardRepo.findById(4L).orElse(null);
		if(board == null) return;
		List<PDSFileEntity> flist = board.getFiles();
		PDSFileEntity file1 = PDSFileEntity.builder().pdsfilename("파일추가1.jpg").build();
		PDSFileEntity file2 = PDSFileEntity.builder().pdsfilename("파일추가2.jpg").build();
		flist.add(file1);flist.add(file2);
		board.setWriter("작성자 수정");
		boardRepo.save(board);
	} // builder쓰면 새로운 투플이 추가됨
	
	
	
	
	//조회

	//@Test
	void selectBoard2() {
		PDSBoardEntity board = boardRepo.findById(4L).orElse(null);
		if(board == null) return;
		System.out.println(board.getPname());
		System.out.println(board);
	}
	
	
	//@Transactional //lazy
	//@Test
	void selectBoard() {
		String w = "누군가";
		List<PDSBoardEntity> blist = boardRepo.findByWriter(w);
		for(PDSBoardEntity board:blist) {
			System.out.println(board);
			System.out.println(board.getFiles().size() + "건");
		}
	}
	
	
	//PDSFileEntity의 pdsfilename으로 조회
	//@Test
	void f3() {
		String filename = "스프링Book-1-1.ppt";
		PDSFileEntity file =  fileRepo.findByPdsfilename(filename);
		System.out.println(file);
	}
	
	//OneToMany
	//@Transactional // lazy이더라도 같은 transaction으로 묶음, EAGER가 즉시로딩으로 수행됨
	//@Test
	void f2() {
		boardRepo.findAll().forEach(board->{
			System.out.println(board); //즉시로딩(Eager), 지연로딩(Lazy)이 default, Lazy를 toSreing 할 경우 에러남
		});
	}
	
	//ManyToOne, Select Test
	//@Test
	void f1() {
		pRepo.findAll().forEach(profile->{
			System.out.println(profile); //즉시로딩(Eager), 지연로딩(Lazy)
		});
	}
	
}
