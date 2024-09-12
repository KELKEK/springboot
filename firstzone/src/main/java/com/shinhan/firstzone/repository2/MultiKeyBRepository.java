package com.shinhan.firstzone.repository2;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.firstzone.vo3.MultiKeyB;
import com.shinhan.firstzone.vo3.MultiKeyBClass;


public interface MultiKeyBRepository extends CrudRepository<MultiKeyBClass, MultiKeyB> {
	List<MultiKeyBClass> findByMultiid(MultiKeyB id);
}
