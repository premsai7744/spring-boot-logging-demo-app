package com.premit.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.premit.dto.UserDTO;

@RestController
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private Map<String, UserDTO> map = new LinkedHashMap<>();
	
	
	@PostMapping("/user/regisration")
	public ResponseEntity<String> userRegistration(@RequestBody UserDTO userDTO) {
		logger.info("Received user registration request with username {}",userDTO.getUserName());
		
		try {
			if(map.containsKey(userDTO.getEmail())) {
				throw new RuntimeException("User registration failed, user already existed with email : "+userDTO.getEmail());
			} else {
				logger.debug("Username : "+userDTO.getUserName());
				logger.debug("Userpassword : "+userDTO.getPassword());
				map.put(userDTO.getEmail(), userDTO);
				logger.info("Registration succesful for user with username {}",userDTO.getUserName());
				return ResponseEntity.status(HttpStatus.CREATED)
						.body("Registation succesfull for user : "+userDTO.getUserName());
			}
		}catch(Exception e) {
			logger.error("Exception occurred : {}",e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(e.getMessage());
		}
	}
}
