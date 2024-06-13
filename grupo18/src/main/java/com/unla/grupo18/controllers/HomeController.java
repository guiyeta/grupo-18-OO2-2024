package com.unla.grupo18.controllers;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import com.unla.grupo18.entities.User;

import com.unla.grupo18.helpers.ViewRouteHelper;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDEX);
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//modelAndView.addObject("username", user.getUsername());
		return modelAndView;
	}

	@GetMapping("/hello/{name}")
	
	public ModelAndView helloParams2(@PathVariable("name") String name) {
		ModelAndView mV;
		 org.springframework.security.core.userdetails.User user =(org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   String userStr=user.getAuthorities().toString();
		   if (userStr.contains("Client")){
			  mV = new ModelAndView(ViewRouteHelper.INDEX);
			  
			   
		   }else {
			   mV = new ModelAndView(ViewRouteHelper.HELLO);
			   mV.addObject("name", name);
			  
		   }
		  
		
		
		return mV;
		
	}
	@GetMapping("/admin")
	public ModelAndView admin() {
		ModelAndView mV=null;
		 org.springframework.security.core.userdetails.User user =(org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   String userStr=user.getAuthorities().toString();
		   if (userStr.contains("Admin")){
			  mV = new ModelAndView(ViewRouteHelper.ADMIN);
			  
			   
		   }else if(userStr.contains("Client")){
			   mV = new ModelAndView(ViewRouteHelper.INDEX);
			   
		   }
		  
		
		
		return mV;
	}

	@GetMapping("/")
	public RedirectView redirectToHomeIndex() {
		return new RedirectView(ViewRouteHelper.ROUTE);
	}
}
