package com.loven.jy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class Boast_cmt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//pk이면서 자동증가칼럼
	private int cmt_seq;
	
	private int boast_seq;
	private String content;
	private String id;
	
	@Column(columnDefinition ="datetime default now()")
	private Date reg_date;
}
