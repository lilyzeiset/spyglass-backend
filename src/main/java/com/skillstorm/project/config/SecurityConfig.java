package com.skillstorm.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
//				.mvcMatchers(HttpMethod.POST, "/login", "/roomtypes","/guests", "/guests/**").permitAll()
//				.mvcMatchers("/reservations","/reservations/**","/rooms").authenticated()
				.anyRequest().permitAll()
				.and()
			.httpBasic()
				.and()
//			.formLogin()
//				.successHandler(authenticationSuccessHandler())
//				.and()
			.cors().and().csrf().disable();

		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}
