package com.unla.grupo18.controllers;


import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo18.dto.ProductDto;
import com.unla.grupo18.helpers.ViewRouteHelper;
import com.unla.grupo18.services.IProductService;
import com.unla.grupo18.services.implementation.ProductService;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.INDEX);
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//modelAndView.addObject("username", user.getUsername());
		String role =  SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		System.out.println("ROL DEL USUARIO" + role);

		if (role.contains("ROLE_USER")) {
			return new ModelAndView(ViewRouteHelper.USER);
		}

		
		return modelAndView;
	}

	
	@GetMapping("/hello/{name}")
	
	public ModelAndView helloParams2(@PathVariable("name") String name) {

		ModelAndView mV = new ModelAndView(ViewRouteHelper.HELLO);
		mV.addObject("name", name);		
		return mV;
		
	}

	
	@GetMapping("/")
	public RedirectView redirectToHomeIndex() {
		return new RedirectView(ViewRouteHelper.ROUTE);
	}
}
