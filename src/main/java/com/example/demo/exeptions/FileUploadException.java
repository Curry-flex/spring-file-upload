package com.example.demo.exeptions;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.entity.ResponseMessage;

@ControllerAdvice
public class FileUploadException extends ResponseEntityExceptionHandler {
	
	  @ExceptionHandler(MaxUploadSizeExceededException.class)
	  public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc) {
		  
		  
			 JSONObject jObject = new JSONObject();
			    try {
			      Field changeMap = jObject.getClass().getDeclaredField("map");
			      changeMap.setAccessible(true);
			      changeMap.set(jObject, new LinkedHashMap<>());
			      changeMap.setAccessible(false);
			    } catch (IllegalAccessException | NoSuchFieldException ex) {
			     
			   }
			

			jObject.put("statusCode", 2001);
			jObject.put("statusMessage", "file is too large exceed maximum limit of 2M");
			
			
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(jObject.toString());
	    //return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File too large!"));
	  }
}

//LOCOMOTIVE FAINT - DONCAT