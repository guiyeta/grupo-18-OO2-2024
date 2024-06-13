package com.unla.grupo18.controllers;

import java.net.http.HttpRequest;


import javax.naming.AuthenticationException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unla.grupo18.entities.UserRole;
import com.unla.grupo18.helpers.ViewRouteHelper;
import com.unla.grupo18.repositories.IUserRepository;
import com.unla.grupo18.services.implementation.UserService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class UserController {

	@GetMapping("/login")
	public String login(Model model,
						@RequestParam(name="error",required=false) String error,
						@RequestParam(name="logout", required=false) String logout) {
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		return ViewRouteHelper.USER_LOGIN;
	}

	@GetMapping("/logout")
	public String logout(Model model) {
		return ViewRouteHelper.USER_LOGOUT;
	}

   @GetMapping("/loginsuccess")
	public String loginCheck() {
	  
	
 //	 String userString= SecurityContextHolder.getContext().getAuthentication();
	   org.springframework.security.core.userdetails.User user =(org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   String userStr=user.getAuthorities().toString();
	   if (userStr.contains("Admin")){
		   userStr="admin";
	   }else {
		   userStr="index";
	   }
	   return "redirect:/"+userStr;
	}
	
	

}
