package com.challenge.inventory.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.challenge.inventory.exception.CustomException;
import com.challenge.inventory.model.AppUser;
import com.challenge.inventory.service.AppUserService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private JwtUtil jwtUtil;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.debug("in do filter internal");
		final String authHeader = request.getHeader("Authorization");
		
		String email=null,jwt=null;
		
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			jwt=authHeader.substring(7);
			System.out.println("on line 39"+jwt);
			email=jwtUtil.extractEmail(jwt);
			System.out.println("on line 42"+email);
		System.out.print(SecurityContextHolder.getContext().getAuthentication());

		if(email!=null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			AppUser appUser=appUserService.findUserByEmail(email);
			System.out.println("48+ "+appUser.getFirstName());
			if(jwtUtil.validateToken(jwt,appUser)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(appUser.getEmail(),appUser.getPassword());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		else {
			throw new CustomException("EC_1001001","Invalid token.");
		}
		}
		filterChain.doFilter(request, response);
		
	}

}
