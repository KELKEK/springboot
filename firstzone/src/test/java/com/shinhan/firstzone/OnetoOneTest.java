package com.shinhan.firstzone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository2.UserCellPhoneVORepository;
import com.shinhan.firstzone.repository2.UserCellPhoneVORepository2;
import com.shinhan.firstzone.repository2.UserVORepository;
import com.shinhan.firstzone.repository2.UserVORepository3;
import com.shinhan.firstzone.vo3.UserCellPhoneVO;
import com.shinhan.firstzone.vo3.UserCellPhoneVO2;
import com.shinhan.firstzone.vo3.UserCellPhoneVO3;
import com.shinhan.firstzone.vo3.UserVO;
import com.shinhan.firstzone.vo3.UserVO2;
import com.shinhan.firstzone.vo3.UserVO3;

@SpringBootTest
public class OnetoOneTest {
	@Autowired
	UserVORepository uRepo;
	@Autowired
	UserCellPhoneVORepository pRepo;
	@Autowired
	UserCellPhoneVORepository2 p2Repo;
	@Autowired
	UserVORepository3 u3repo;
	
	@Test
	void f3() {
		UserCellPhoneVO3 phone = UserCellPhoneVO3.builder()
				.phoneNumber("12345")
				.model("aa")
				.build();
		
		UserVO3 user = UserVO3.builder()
				.userid("옥")
				.username("옥주현")
				.phone(null)
				.build();
		
		phone.setUser3(user);
		u3repo.save(user);
	}
	
	//@Test
	void f2() {
		UserVO2 user = UserVO2.builder()
				.userid("coffee2")
				.username("우지2")
				.build();
		UserCellPhoneVO2 phone = UserCellPhoneVO2.builder()
				.phoneNumber("010-7886-4589")
				.model("갤럭시")
				.user(user)
				.build();
		p2Repo.save(phone);
	}
	
	//@Test
	void f1() {
		UserCellPhoneVO phone = UserCellPhoneVO.builder()
				.phoneNumber("010-7896-4569")
				.model("갤럭시")
				.build(); //폰의 정보를 먼저 알아야 userVO도 넣을 수 있다.
		
		UserCellPhoneVO newPhone = pRepo.save(phone);
		
		UserVO uservo = UserVO.builder()
				.userid("zzilre")
				.username("jin")
				.phone(newPhone)
				.build();
		
		uRepo.save(uservo);
	}
}
