package com.challenge.inventory.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.challenge.inventory.exception.CustomException;
import com.challenge.inventory.model.AppUser;
import com.challenge.inventory.repository.AppUserRepository;

@Service
public class AppUserService implements AppUserServiceInterface,UserDetailsService{
@Autowired
	AppUserRepository appUserRepository;
	
	@Override
	public AppUser saveAppUser(AppUser appUser) {
		AppUser createdUser=new AppUser();
		try {
		createdUser=appUserRepository.save(appUser);
		if(createdUser==null)
			throw new CustomException("ES_6001","Unable  to creating user.");
		}catch(CustomException e) {
			throw e;
		}catch(Exception e) {
			throw new CustomException("ES_6999","Something went wrong while creating user."+e.getMessage());
		}
		return createdUser;
	}
	
	@Override
	public AppUser findUserByEmail(String email) {
		AppUser appUser=null;
		try {
		appUser=appUserRepository.findByEmail(email);
		if(appUser==null)
			throw new CustomException("ES_6002","User with given Email not found.");
		}catch(CustomException e) {
			throw e;
		}catch(Exception e) {
			throw new CustomException("ES_6998","Something went wrong while retriving product with given email. "+e.getMessage());
		}
		return appUser;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("in loadUserByUsername function");
		AppUser appUser=this.findUserByEmail(email);
		System.out.println(appUser.getEmail());
		return new User(appUser.getEmail(),appUser.getPassword(),new ArrayList<>());
	}
	
	

}
