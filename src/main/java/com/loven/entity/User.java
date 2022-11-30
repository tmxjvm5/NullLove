package com.loven.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
	private String id;
	private String pw;
	private String name;
	private String nick;
	private String email;
	private String reg_date;
	private char login_type;
	private String company_name;
}
