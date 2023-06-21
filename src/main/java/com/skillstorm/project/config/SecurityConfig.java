package com.skillstorm.project.config;

import java.util.Arrays;

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
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.csrf().disable()
			.oauth2Login();
		
		http.cors().configurationSource(request -> {
			CorsConfiguration corsConfig = new CorsConfiguration();
			corsConfig.addAllowedOrigin("http://localhost:5173");
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
