package com.cloud.security.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloud.security.model.User;
import com.cloud.security.model.UserProfile;
import com.cloud.security.service.UserService;

@Controller
public class MainController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		model.addAttribute("greeting", "Hi, Welcome");
		return "welcome";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		Object principal1 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userService.findBySso(((UserDetails) principal1).getUsername());
		return "admin";
	}

	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "accessDenied";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(value = "/login/verify", method = RequestMethod.GET)
	public String verify(
					@RequestParam("id") String id,
					@RequestParam("email") String email,
					@RequestParam("firstName") String firstName,
					@RequestParam("lastName") String lastName) {
		User usr = userService.findByGID(id);
		if(null != usr  && usr.getState().equals(new String("Active"))) {
			UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(usr.getSsoId(), usr.getPassword());
			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(authReq);
			return "success";
		}else if(null==usr) {
			User new_usr = userService.createUser(id,email,firstName,lastName);
			UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(new_usr.getSsoId(), new_usr.getPassword());
			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(authReq);
			return "success";
		}
		return "error";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "logout";
	}

	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}