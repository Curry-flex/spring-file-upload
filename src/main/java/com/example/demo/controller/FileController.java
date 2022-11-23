package com.example.demo.controller;


import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.DTO.FileResponse;
import com.example.demo.entity.FileDB;
import com.example.demo.entity.ResponseMessage;
import com.example.demo.exeptions.FileToLargeException;
import com.example.demo.exeptions.RequestedFileNotFound;
import com.example.demo.service.FileService;


@RestController
@CrossOrigin ("*")
public class FileController {

	@Autowired
	private FileService fileService;
	
	@PostMapping("upload")
	public ResponseEntity<ResponseMessage> fileUpload(@RequestParam("file") MultipartFile file)
	{
		String message = "";
		 try {
//			 if(file.getBytes().length > 2097152)
//			 {
//				throw new FileToLargeException("2001", "file exceed maximum limit 2M");
//			 }
			 
			 fileService.store(file);
			
			  message ="file uploaded successfully" + file.getOriginalFilename();
			  return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			
			message ="Could not upload file " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}	
	}
	
	@GetMapping(path ="files",produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFiles()
	{
		 List<FileResponse> files = fileService.getFiles().map(dbFile -> {
		      String fileDownloadUri = ServletUriComponentsBuilder
		          .fromCurrentContextPath()
		          .path("/file/")
		          .path(dbFile.getId())
		          .toUriString();
			
		      return new FileResponse(
		              dbFile.getName(),
		              fileDownloadUri,
		              dbFile.getType(),
		              dbFile.getData().length);
		        }).collect(Collectors.toList());
		 
		 LinkedHashMap<String, Object> response = new LinkedHashMap<String, Object>();
	
		
		 
		 JSONObject jObject = new JSONObject();
		    try {
		      Field changeMap = jObject.getClass().getDeclaredField("map");
		      changeMap.setAccessible(true);
		      changeMap.set(jObject, new LinkedHashMap<>());
		      changeMap.setAccessible(false);
		    } catch (IllegalAccessException | NoSuchFieldException e) {
		     
		   }
		

		jObject.put("statusCode", 2000);
		jObject.put("statusMessage", "Operation Successfully");
		jObject.put("data", files);
		
	 
		 return  ResponseEntity.status(HttpStatus.OK).body(jObject.toString());
	}
	
	@GetMapping(path ="files/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		 
		 try {
			 FileDB fileDB = fileService.getfile(id);

			    return ResponseEntity.ok()
			        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"",HttpHeaders.ACCEPT_LANGUAGE,"sw")
			        .body(fileDB.getData());
			
		} catch (Exception e) {
			throw new RequestedFileNotFound(HttpStatus.NOT_FOUND,"Could not find such file");
		}
		   
	}
	 
	 //Download file
	 // @GetMapping("file/download/{id}")
	
	 
	 
}
