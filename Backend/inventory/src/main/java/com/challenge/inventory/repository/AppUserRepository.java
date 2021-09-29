package com.challenge.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.inventory.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

	AppUser findByEmail(String email);

}
