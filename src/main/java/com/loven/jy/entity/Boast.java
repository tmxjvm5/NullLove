package com.loven.jy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Boast {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//pk이면서 자동증가칼럼
	private Long seq;
	private String title;
	private String content;
	
	@Column(columnDefinition ="datetime default now()")
	private Date date;
	
	@Column(columnDefinition = "int default 0")
	private Long cnt;
	
	private Long likes;
	
	@Column(columnDefinition = "char default n")
	private char order;
	private String id;
	private String file1;
	private String file2;
	private String file3;
	private String file4;
	private String file5;
	
}
