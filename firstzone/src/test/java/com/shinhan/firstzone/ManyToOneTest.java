package com.shinhan.firstzone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.repository.ProfileRepository;
import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo2.MemberRole;
import com.shinhan.firstzone.vo2.ProfileEntity;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class ManyToOneTest {
	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ProfileRepository pRepo;
	
	//Spring Security는 반드시 비밀번호가 암호화 되어있어야 한다.
	@Autowired
	PasswordEncoder passEncoder;
	
	@Test
	void makePass() {
		
		//user2
		List<String> ids = new ArrayList<>();
		ids.add("user2");ids.add("admin1");ids.add("Manager");
		mRepo.findAllById(ids).forEach(member->{
			member.setMpassword(passEncoder.encode(member.getMpassword())); //암호화 과정
			mRepo.save(member);
		});
		//admin1
		
	}
	
	//@Test
	void getProfileCount() {
		pRepo.getProfileCount().forEach(arr->{log.info(Arrays.toString(arr));
		log.info("mid : " + arr[0] + ", 프로필갯수 : " + arr[1]);
		log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		});
	}
	
	
	//@Test
	void getProfile() {
		pRepo.getProfile("user5").forEach(arr->{log.info(Arrays.toString(arr));
		log.info("이름 : " + arr[0] + ", 파일이름 : " + arr[1]);
		log.info("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		});
	}
		
	//특정 Member(user5), currentYn이 1인 것의 프로파일 조회
	//@Test
	void selectByMem() {
		MemberEntity member = MemberEntity.builder()
				.mid("user5").build();
		pRepo.findByMemberAndCurrentYnIsTrue(member).forEach(p->log.info(p));
	}
	
	
	
	//fno=1의 정보 조회
	//@Test
	void selectByFno() {
		pRepo.findById(3L).ifPresentOrElse(m->{
			System.out.println("파일 이름 : " + m.getFname());
			System.out.println(m.isCurrentYn()?"현재프로파일임":"과거프로파일임");
			System.out.println("이름 : " + m.getMember().getMname());
			System.out.println("권한 : " + m.getMember().getMrole().name());
		}, ()->{
			log.info("존재하지 않는 회원");
		});
	}
	
	//조회
	//@Test
	void selectByMid() {
//		mRepo.findById("user5").ifPresentOrElse(m->{
//			log.info(m);
//		}, ()->{
//			log.info("존재하지 않는 Member입니다.");
//		});
		
		//전체조회
		pRepo.findAll().forEach(p->{
			log.info(p);
		});
	}
	
	//2, profile Insert
	//@Test
	void profileInsert() {
		MemberEntity member = MemberEntity.builder().mid("user5").build();
		IntStream.rangeClosed(1, 10).forEach(i->{
			ProfileEntity profile = ProfileEntity.builder()
					.fname("computer - " + i)
					.currentYn(i==5?true:false)
					.member(member)
					.build();
			pRepo.save(profile);
		});
	}
	
	//1, member insert
	//@Test
	void memberInsert() {
		IntStream.rangeClosed(1, 2).forEach(i->{
			MemberEntity member = MemberEntity.builder()
					.mid("admin"+i)
					.mname("신윤철"+i)
					.mpassword("1234")
					.mrole(MemberRole.USER)
					.build();
			mRepo.save(member);
		});
	}
}
