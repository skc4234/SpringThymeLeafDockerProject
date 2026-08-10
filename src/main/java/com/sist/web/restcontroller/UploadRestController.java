package com.sist.web.restcontroller;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadRestController {
	// yml의 값 읽기
	@Value("${file.upload_dir}")
	private String uploadDir;
	private static int count=1; // 같은 파일명이 있을 경우 처리
	
	// multipart/form-data => Protocol
	// post 기반
	@PostMapping("/upload_ok")
	public String upload_ok(
			@RequestParam(value="file",required = false) MultipartFile file)
			throws Exception {
		File f=new File(uploadDir);
		if(!f.exists()) {
			f.mkdir();
		}
		if(file.isEmpty()) {
			return "파일이 존재하지 않습니다.";
		}
		String oname=file.getOriginalFilename(); // 사용자가 보낸 파일명
		File files=new File(uploadDir+"/"+oname); // 같은 파일명이 있는지 확인용
		String newName=oname; // 업로드 될 파일명
		if(files.exists()) { // 같은 파일명이 있으면
			String name=oname.substring(0,oname.lastIndexOf(".")); // 순수 파일이름
			String ext=oname.substring(oname.lastIndexOf(".")); // .jpg, .png 등
			newName=name+" ("+count+")"+ext;
			count++;
		}
		// upload
		Path savePath=Paths.get(uploadDir,newName);
		Files.copy(file.getInputStream(), savePath);
		return "업로드 성공: "+oname+", 변경된 파일명: "+newName;
	}
	
	@PostMapping("/multi_upload")
	public String multi_upload(
			@RequestParam(value="files",required = false) List<MultipartFile> files)
			throws Exception {
		for(MultipartFile file:files) {
			if(file.isEmpty()) {
				return "파일이 존재하지 않습니다...";
			}
			else {
				String oname=file.getOriginalFilename();
				System.out.println("oname: "+oname);
				File f=new File(uploadDir+"/"+oname);
				if(f.exists()) {
					String name=oname.substring(0,oname.lastIndexOf(".")); // 순수 파일이름
					String ext=oname.substring(oname.lastIndexOf(".")); // .jpg, .png 등
					int cnt=1;
					while(f.exists()) {
						String newName=name+" ("+cnt+")"+ext;
						System.out.println("newName: "+newName);
						f=new File(uploadDir+"/"+newName);
						cnt++;
					}
				}
				Path savePath=Paths.get(uploadDir,f.getName());
				Files.copy(file.getInputStream(), savePath);
			}
		}
		return "업로드 성공";
	}
}
