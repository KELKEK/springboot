package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.firstzone.repository2.FreeBoardRepository;
import com.shinhan.firstzone.repository2.FreeReplyRepository;
import com.shinhan.firstzone.vo3.FreeBoardEntity;
import com.shinhan.firstzone.vo3.FreeReplyEntity;
import com.shinhan.firstzone.vo3.QFreeBoardEntity;

@SpringBootTest
public class BiDirectionTest {
	@Autowired
	FreeBoardRepository boardRepo;
	@Autowired
	FreeReplyRepository replyRepo;
	
	//@Test
	void queryDslTest() {
		String type = "tcw";
		String keyword = "맑음";
		
		BooleanBuilder builder = new BooleanBuilder();
		QFreeBoardEntity board =QFreeBoardEntity.freeBoardEntity;
		builder.and(board.bno.gt(0L));
		
		BooleanBuilder builder2 = new BooleanBuilder();
		
		if(type.equals("t")) {
			builder2.or(board.title.like("%"+keyword+"%"));
		}
		if(type.equals("c")) {
			builder2.or(board.content.like("%"+keyword+"%"));
		}
		if(type.equals("w")) {
			builder2.or(board.writer.like("%"+keyword+"%"));
		}
		builder.and(builder2);
		System.out.println(builder);
		Pageable page = PageRequest.of(0, 10);
		Page<FreeBoardEntity> result = boardRepo.findAll(builder, page);
		List<FreeBoardEntity> list = result.getContent();
		list.forEach(b->{ System.out.println(b); });
		System.out.println("getSize:" + result.getSize());
		System.out.println("getTotalPages:" + result.getTotalPages());
		System.out.println("getTotalElements:" + result.getTotalElements());
		System.out.println("nextPageable:" + result.nextPageable());

	}
	
	@Test
	void join2() {
		//N:1에서 테스트
		//Reply select, N+1문제 발생
		//Reply가 참조하는 board가 5가지라면 Reply select 1개 당 5의 board select가 나온다.
		//replyRepo.findAllWithReplyUsingJoin().forEach(b->System.out.println(b));
		//Reply select, N+1문제 해결
		replyRepo.findAllWithReplyUsingJoinFetch().forEach(b->System.out.println(b));
	}
	
	//@Test
	void join1() {
		boardRepo.findAllWithReplyUsingJoin().forEach(b->System.out.println(b));
	}
	
	//조건조회 bno>= 10 and bno <= 20, paging추가, sort추가
	//@Transactional
	//@Test
	void selectBoard2() {
		//1번째 Page 5건 return
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "bno"); // bno기준으로 내림차순정렬
		//where bno between 1 and 10 order by bno desc
		Page<FreeBoardEntity> result = boardRepo.findByBnoBetween(1L, 10L, pageable);
		System.out.println("getNumber : " + result.getNumber());
		System.out.println("getSize : " + result.getSize());
		System.out.println("getTotalElements : " + result.getTotalElements());
		System.out.println("getTotalPages : " + result.getTotalPages());
		System.out.println("getSort : " + result.getSort());
		System.out.println("getPageable : " + result.getPageable());
//		boardRepo.findByBnoBetween(1L, 10L, pageable).forEach(board->{
//			System.out.println(board);
//			System.out.println(board.getReplies().size() + "건");
//		});
	}
	
	//BoardTitle, Reply건수
	//@Test
	void selectBoardReply() {
		boardRepo.getFreeBoardReplyCount().forEach(arr->System.out.println(Arrays.toString(arr)));
	}

	
	//@Transactional
	//@Test
	void selectBoard() {
		boardRepo.findAll().forEach(board->{
			System.out.println(board);
			System.out.println("댓글 건수 : " + board.getReplies().size());
		});
	}
	
	//댓글 조회(Board번호를 알고 댓글들의 정보조회)
	//@Test
	void selectByReply() {
		FreeBoardEntity board = FreeBoardEntity.builder().bno(5L).build();
		replyRepo.findByBoard(board).forEach(reply->{
			System.out.println(reply);
			System.out.println(reply.getBoard());
		});
	}
	
	//댓글 수정
	//@Test
	void updateReply() {
		FreeBoardEntity board = FreeBoardEntity.builder().bno(5L).build();
		List<FreeReplyEntity> replies = replyRepo.findAllById(Arrays.asList(1L, 2L, 6L, 8L));
		replies.forEach(reply -> {
		    reply.setBoard(board);
		    replyRepo.save(reply);
		});
	}
	
	//댓글 삽입
	//@Test
	void insertReply() {
		//FreeBoardEntity board = boardRepo.findById(4L).get();
		FreeBoardEntity board = FreeBoardEntity.builder().bno(4L).build();
		FreeReplyEntity reply = FreeReplyEntity.builder()
				.reply("점심메뉴")
				.replyer("user1")
				.board(board)
				.build();
		
		replyRepo.save(reply);
	}
	
	
	//특정 보드의 댓글삽입
	//@Test
	void insertBoard2() {
		boardRepo.findById(3L).ifPresent(board->{
			List<FreeReplyEntity> replyList = board.getReplies();
			IntStream.rangeClosed(2, 3).forEach(i->{
				FreeReplyEntity reply = FreeReplyEntity.builder()
						.reply("살려줘"+i)
						.replyer("user"+i)
						.board(board)
						.build();
			replyList.add(reply);
			});
			boardRepo.save(board);
		});
	}
	
	//@Test
	void insertBoard() {
		//30건
		IntStream.rangeClosed(1, 30).forEach(i->{
			FreeBoardEntity entity = FreeBoardEntity.builder()
					.title("수요일"+i)
					.content("오늘은" + (i%2==0?"맑음":"흐림"))
					.writer("user" + i%5)
					.build();
			boardRepo.save(entity);
		});
	}
}
