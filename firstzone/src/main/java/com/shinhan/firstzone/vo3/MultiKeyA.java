package com.shinhan.firstzone.vo3;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder@AllArgsConstructor@NoArgsConstructor
public class MultiKeyA implements Serializable {

	private static final long serialVersionUID = 1L; //serializable때문에 추가한 것. 필수는 아님(노란 줄 갖다대면 자동으로 생성 가능)
	Integer id1;
	Integer id2;
}
