package com.shinhan.firstzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.repository2.MultiKeyARepository;
import com.shinhan.firstzone.repository2.MultiKeyBRepository;
import com.shinhan.firstzone.vo3.MultiKeyA;
import com.shinhan.firstzone.vo3.MultiKeyAClass;
import com.shinhan.firstzone.vo3.MultiKeyB;
import com.shinhan.firstzone.vo3.MultiKeyBClass;

@SpringBootTest
public class MultiKeyTest {
	@Autowired
	MultiKeyARepository aRepo;
	@Autowired
	MultiKeyBRepository bRepo;
	
	//@Test
	void selectB() {
		MultiKeyB b = MultiKeyB.builder().id1(20).id2(30).build();
		bRepo.findById(b).ifPresent(m->{
			System.out.println(m);
		});
	}
	
	@Test
	void insertB() {
		MultiKeyB id = MultiKeyB.builder().id1(5).id2(10).build();
		MultiKeyBClass b = MultiKeyBClass.builder()
				.multiid(id)
				.productName("람쥐1")
				.price(75)
				.build();
		bRepo.save(b);
	}
	
	//@Test
	void selectA() {
		MultiKeyA a = MultiKeyA.builder().id1(10).id2(20).build();
		aRepo.findById(a).ifPresent(m->{
			System.out.println(m);
		});
	}
	
	//@Test
	void insertA() {
		MultiKeyAClass a = MultiKeyAClass.builder()
				.id1(20)
				.id2(30)
				.productName("커피")
				.price(75)
				.build();
		aRepo.save(a);
	}
}
