package com.other.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeHttpRequests(authorizationManager -> authorizationManager.anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults())
			.logout(logoutCustomizer -> logoutCustomizer.invalidateHttpSession(true).deleteCookies("JSESSIONID"));
		return httpSecurity.build();
	}
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails user = User.builder().username("user").password(passwordEncoder().encode("123")).roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}
}
