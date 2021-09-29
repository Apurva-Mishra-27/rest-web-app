package com.challenge.inventory.controller;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.inventory.VO.AppUserVO;
import com.challenge.inventory.VO.AuthResponse;
import com.challenge.inventory.exception.CustomException;
import com.challenge.inventory.model.AppUser;
import com.challenge.inventory.service.AppUserServiceInterface;
import com.challenge.inventory.util.JwtUtil;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
@RestController
public class AppUserController {
	
	@Autowired
	AppUserServiceInterface appUserServiceInterface;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AppUserVO appUserVO) throws CustomException{
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUserVO.getEmail(),appUserVO.getPassword()));
		}catch(BadCredentialsException e) {
			throw new CustomException("EC_5001","Incorrect email or password.");
		}
		System.out.println("50");
		final AppUser appUser= appUserServiceInterface.findUserByEmail(appUserVO.getEmail());
		
		final String jwtToken= jwtUtil.generateToken(appUser);
		
		return new ResponseEntity<AuthResponse>(new AuthResponse(jwtToken),HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<AppUserVO> register(@RequestBody AppUserVO appUserVO) throws CustomException{
		AppUserVO createdAppUserVO=new AppUserVO();
		if(appUserVO==null)
			throw new CustomException("EC_5002","User details cannot be null.");
		if(appUserVO.getFirstName().isEmpty()||appUserVO.getFirstName().length()==0)
			throw new CustomException("EC_5003","First name should not be null or empty.");
		if(appUserVO.getLastName().isEmpty()||appUserVO.getLastName().length()==0)
			throw new CustomException("EC_5004","Last name should not be null or empty.");
		if(appUserVO.getEmail().isEmpty()||appUserVO.getEmail().length()==0)
			throw new CustomException("EC_5005","Email should not be null or empty.");
		if(appUserVO.getPassword().isEmpty()||appUserVO.getPassword().length()==0)
			throw new CustomException("EC_5006","Password name should not be null or empty.");
		
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; 
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(appUserVO.getEmail());
		if(!matcher.matches())
			throw new CustomException("EC_5007","Please enter valid email.");
		
		AppUser appUser=new AppUser();
		appUser.setEmail(appUserVO.getEmail());
		appUser.setPassword(appUserVO.getPassword());
		appUser.setFirstName(appUserVO.getFirstName());
		appUser.setLastName(appUserVO.getLastName());
		
		try {
			AppUser createdUser=appUserServiceInterface.saveAppUser(appUser);
			if(createdUser==null)
				throw new CustomException("EC_5008","App User created with id as null");
			else {
				createdAppUserVO.setEmail(createdUser.getEmail());
				createdAppUserVO.setPassword(createdUser.getPassword());
				createdAppUserVO.setFirstName(createdUser.getFirstName());
				createdAppUserVO.setLastName(createdUser.getLastName());
			}
		}catch(CustomException e)
		{
			throw e;
		}catch(Exception e)
		{
			throw new CustomException("EC_5999","Error occured while registering user.");
		}
		return new ResponseEntity<AppUserVO>(createdAppUserVO,HttpStatus.OK);
	}
	
	

}
