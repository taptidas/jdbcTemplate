package com.example.demo;

	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.ControllerAdvice;
	import org.springframework.web.bind.annotation.ExceptionHandler;

	@ControllerAdvice
	public class EmployeeExceptionController {

		   @ExceptionHandler(value = EmployeeNotPresent.class)
		   public ResponseEntity<Object> exception(EmployeeNotPresent exception) {
		      return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
		   }
		   
		   @ExceptionHandler(value = EmployeeNotFound.class)
		   public ResponseEntity<Object> exception(EmployeeNotFound exception) {
		      return new ResponseEntity<>("Employee with this id ndon't exists!", HttpStatus.NOT_FOUND);
		   }
		}



