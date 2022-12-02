package com.loven.entity;

import lombok.Data;

@Data
public class Comment {

    private int cmt_seq;
    private String content;
    private String reg_date;
    private int seq;
    private String id;
}
