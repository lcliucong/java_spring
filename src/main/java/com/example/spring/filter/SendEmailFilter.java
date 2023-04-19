package com.example.spring.filter;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SendEmailFilter {
    private String sendFrom;
    private String sendTo;
    private String subjects;
    private String text;
    private Timestamp sendTime;
}
