package com.shinhan.firstzone.vo2;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//데이터 계속 전송용(DB말고 Java와 브라우저 간에 지속적으로 소통하기 위함)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestBookDTO {
	private Long gno;
	private String title;
	private String content;
	private String writer;
	
	LocalDateTime regDate;
	LocalDateTime modDate;
	//조인을 했다면 조인된 칼럼까지 추가로 적어준다.
}
