package com.example.demo.service;

import java.io.IOException;


import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.FileDB;

import com.example.demo.repository.FileRepository;



@Service
public class FileService {

 @Autowired
 private FileRepository fileRepository;

	
 public FileDB store(MultipartFile file) throws IOException
 {
	 String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	 FileDB filedb = new FileDB(fileName, file.getContentType(), file.getBytes());
	 
	 return fileRepository.save(filedb);
 }
 
 public FileDB getfile(String id)
 {
	 return fileRepository.findById(id).orElseThrow();
 }
 
 public Stream<FileDB> getFiles()
 {
	 return fileRepository.findAll().stream();
 }

}

