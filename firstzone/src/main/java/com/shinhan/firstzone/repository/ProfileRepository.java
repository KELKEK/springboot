package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shinhan.firstzone.vo2.MemberEntity;
import com.shinhan.firstzone.vo2.ProfileEntity;


public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>{
	//1, 기본적인 CRUD
	//2, 규칙에 맞는 함수 정의
	List<ProfileEntity> findByMember(MemberEntity member);
	List<ProfileEntity> findByMemberAndCurrentYnIsTrue(MemberEntity member); //Is생략 가능
	List<ProfileEntity> findByCurrentYnTrue(); //필요한 매개변수는 알아서 설정해준다.
	
	//3, @Query이용 -- 특정 member의 profile 가져오기, 없는 멤버도 나올 수 있도록 outer join함.
	@Query("select m.mname, p.fname from ProfileEntity p right outer join MemberEntity m on (m = p.member) where m.mid = ?1")
	List<Object[]> getProfile(String mid);
	
	//모든 mid, profile의 count 출력, 없는 사람도 나오려면 outer join해야 한다.
	@Query("select m.mid, count(p) from MemberEntity m left outer join ProfileEntity p on (m = p.member) group by m.mid")
	List<Object[]> getProfileCount();
}
