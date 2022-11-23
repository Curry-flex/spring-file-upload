package com.example.demo.exeptions;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.DTO.ExceptionMessage;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ExceptionAdvice {
	
	@ExceptionHandler(RequestedFileNotFound.class)
	public ResponseEntity handleException(RequestedFileNotFound e)
	{
		//returns as formatted json array object
		
		 
		 JSONObject jObject = new JSONObject();
		    try {
		      Field changeMap = jObject.getClass().getDeclaredField("map");
		      changeMap.setAccessible(true);
		      changeMap.set(jObject, new LinkedHashMap<>());
		      changeMap.setAccessible(false);
		    } catch (IllegalAccessException | NoSuchFieldException ex) {
		     
		   }
		

		jObject.put("statusCode", 2001);
		jObject.put("statusMessage", e.getMessage());
		
		
		return ResponseEntity.status(e.getHttpStatus()).body(jObject.toString());
	}
	
	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ExceptionMessage> globalExceptionHnadler(Exception ex)
//	{
//		ExceptionMessage eMessage = new ExceptionMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "operation failed", ex.getMessage());
//		
//		return new ResponseEntity<ExceptionMessage>(eMessage,HttpStatus.INTERNAL_SERVER_ERROR);
//		
//	}
	
	@ExceptionHandler(FileToLargeException.class)
	public ResponseEntity fileToLarg(FileToLargeException e)
	{
		 
		 JSONObject jObject = new JSONObject();
		    try {
		      Field changeMap = jObject.getClass().getDeclaredField("map");
		      changeMap.setAccessible(true);
		      changeMap.set(jObject, new LinkedHashMap<>());
		      changeMap.setAccessible(false);
		    } catch (IllegalAccessException | NoSuchFieldException ex) {
		     
		   }
		

		jObject.put("statusCode", 2001);
		jObject.put("statusMessage", e.getMessage());
		
		
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(jObject.toString());
		
	}
	
	

	  
//	  @ExceptionHandler(value = {ResourceNotFoundException.class, CertainException.class})
//	  public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//	    ErrorMessage message = new ErrorMessage(
//	        status,
//	        date,
//	        ex.getMessage(),
//	        description);
//	    
//	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
//	  }
	

}
