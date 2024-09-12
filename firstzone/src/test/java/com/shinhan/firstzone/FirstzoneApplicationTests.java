package com.shinhan.firstzone;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.vo.CarVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class FirstzoneApplicationTests {
	
	@Test
	void f1() {
		CarVO car = CarVO.builder().model("그랜져").price(5000).build();
		log.info(car);
	}
	
	//@Test
	void contextLoads() {
	}

}
