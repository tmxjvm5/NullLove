package com.loven.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Company {
	private String no;
	private String opendate;
	private String owner;
	private String type;
	private String id;
	private String name;
	private String org_super;
}
