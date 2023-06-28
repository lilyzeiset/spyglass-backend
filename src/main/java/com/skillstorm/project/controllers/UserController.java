package com.skillstorm.project.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@CrossOrigin(allowCredentials = "true", originPatterns = {"http://d1ulkr0tra4x6n.cloudfront.net","http://localhost:5173","http://lily-spyglass.s3-website-us-east-1.amazonaws.com","http://lily-spyglass-env.eba-he3agp52.us-east-1.elasticbeanstalk.com"})
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Value("${frontend-url}")
	private String frontendUrl;
	
	@GetMapping("/signin")
	public RedirectView redirectView() {
		log.info("Frontend URL: " + frontendUrl);
		log.info("Signing in with Google");
		RedirectView redirectView = new RedirectView(frontendUrl+"/goals");
		return redirectView;
	}
	
	@GetMapping("/userinfo")
	@ResponseBody
	public Map<String, Object> userInfo(@AuthenticationPrincipal OAuth2User user) {
		log.info("getting user info...");
		return user.getAttributes();
	}
}
