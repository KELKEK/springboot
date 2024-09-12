package com.shinhan.firstzone.repository2;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.firstzone.vo3.MultiKeyA;
import com.shinhan.firstzone.vo3.MultiKeyAClass;
import java.util.List;


public interface MultiKeyARepository extends CrudRepository<MultiKeyAClass, MultiKeyA> {

}
