package com.challenge.inventory.service;


import com.challenge.inventory.model.AppUser;


public interface AppUserServiceInterface {

	AppUser saveAppUser(AppUser appUser);

	AppUser findUserByEmail(String email);

}
