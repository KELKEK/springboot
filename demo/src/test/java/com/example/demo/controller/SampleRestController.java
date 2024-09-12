package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.vo.CarVO;

@RestController // @controller + @responseBody
@RequestMapping("/sample")
public class SampleRestController {
	@GetMapping("/test1")
	public String f1() {
		
		return "Hello~반갑습니다.";
	}
	
	@GetMapping("/test2")
	public CarVO f2() {
		CarVO car = CarVO.builder()
				.model("ABC모델")
				.price(2000)
				.build();
		
		return car;
		//json으로 만들어주는 jackson에 의해 객체가 만들어진다.
	}
	
	
	@GetMapping("/test3")
	public List<CarVO> f3() {
		List<CarVO> carList = new ArrayList<>();
		IntStream.rangeClosed(1, 5).forEach(i->{
			CarVO car = CarVO.builder()
					.model("ABC모델" + i)
					.price(2000*i)
					.build();
			carList.add(car);
		});
		return carList;
		//json으로 만들어주는 jackson에 의해 객체가 만들어진다.
	}
}
