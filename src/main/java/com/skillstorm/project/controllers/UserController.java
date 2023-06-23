package com.skillstorm.project.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:8080","http://lily-spyglass.s3-website-us-east-1.amazonaws.com"})
public class UserController {
	
	@Value("${frontend-url}")
	private String frontendUrl;
	
	@GetMapping("/signin")
	public RedirectView redirectView() {
		System.out.println(frontendUrl);
		RedirectView redirectView = new RedirectView(frontendUrl+"/goals");
		return redirectView;
	}
	
	@GetMapping("/userinfo")
	@ResponseBody
	public Map<String, Object> userInfo(@AuthenticationPrincipal OAuth2User user) {
		System.out.println("getting user info...");
		return user.getAttributes();
	}
}
