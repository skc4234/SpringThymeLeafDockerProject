package com.sist.web.vo;
/*
NO        NOT NULL NUMBER         
NAME      NOT NULL VARCHAR2(51)   
SUBJECT   NOT NULL VARCHAR2(2000) 
CONTENT   NOT NULL CLOB           
PWD       NOT NULL VARCHAR2(10)   
REGDATE            DATE           
HIT                NUMBER         
FILENAME           VARCHAR2(1000) 
FILESIZE           VARCHAR2(500)  
FILECOUNT          NUMBER
	
	1. SpringBoot+ThymeLeaf
	2. JPA+MyBatis => JOIN / JPQL / QueryDSL / 동적 쿼리
	ㅇ3. Spring Security + JWT
	ㅇ4. 알림 => WebSocket + Stormp + Kafka
	5. JavaMail 보내기
	ㅇ6. Front-End => Pinia(Vue)
	ㅇ7. Spring AI
	==> CI/CD (AWS) : 무중단(Blue / Green) => nginx => Jenkins
	---------------------------
	Spring AI + React + TanStack-Query + TypeScript + NodeJS
	---------------------------
	NextJS
 */
import java.util.*;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class DataBoardVO {
	private int no,hit,filecount;
	private String name,subject,content,pwd,dbday,filename,filesize;
	private Date regdate;
	private List<MultipartFile> files;
}
