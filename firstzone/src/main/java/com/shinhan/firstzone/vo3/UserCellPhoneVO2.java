package com.shinhan.firstzone.vo3;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name="tbl_usercellphone2")
public class UserCellPhoneVO2 {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="phone_id")
Long phoneId;
String phoneNumber;
String model;

//비시별자로 참조하기(참조하는 쪽에 annotation을 넣음)
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "user_id")
UserVO2 user;

}