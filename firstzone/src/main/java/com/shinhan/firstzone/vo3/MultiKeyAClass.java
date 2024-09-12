package com.shinhan.firstzone.vo3;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "t_child1")
@Entity
@Data
@AllArgsConstructor@NoArgsConstructor@Builder
@IdClass(MultiKeyA.class)
public class MultiKeyAClass {
	@Id
	Integer id1;
	@Id
	Integer id2; //DB스러운 방법
	String productName;
	int price;
}
