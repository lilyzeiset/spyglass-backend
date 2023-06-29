package com.skillstorm.project.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

	@Value("${frontend-url}")
	private String frontendUrl;
	
	@Value("${mode}")
	private String mode;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.mvcMatchers("/goal/all").access(mode.equals("dev") ? "permitAll()" : "denyAll()")
			.anyRequest().authenticated()
			.and()
			.csrf().disable()
			.oauth2Login()
			.and()
			.logout(logout -> logout.permitAll()
	                .logoutSuccessHandler((request, response, authentication) -> {
	                    response.setStatus(HttpServletResponse.SC_OK);
	                }));
		
		http.cors().configurationSource(request -> {
			CorsConfiguration corsConfig = new CorsConfiguration();
			corsConfig.addAllowedOrigin(frontendUrl);
			corsConfig.addAllowedOrigin("http://lily-spyglass-env.eba-he3agp52.us-east-1.elasticbeanstalk.com");
			corsConfig.addAllowedOrigin("http://lily-spyglass.s3-website-us-east-1.amazonaws.com");
			corsConfig.addAllowedOrigin("http://vdpwuzgpcr.us-east-1.awsapprunner.com");
			corsConfig.addAllowedOrigin("http://localhost:8089");
			corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
			corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
			corsConfig.setAllowCredentials(true);
			corsConfig.setMaxAge(3600L);
			
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", corsConfig);
			
			return corsConfig;
		});

		return http.build();
	}
}
