package com.nhphuong.utilitytool.esb.config;

import java.util.Arrays;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.nhphuong.utilitytool.esb.filter.JWTAuthenticationFilter;
import com.nhphuong.utilitytool.esb.filter.JWTLoginFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger log = Logger.getLogger(WebSecurityConfig.class);
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/**").permitAll()
			.antMatchers("/api/auth/login").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public CorsFilter corsFilter() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    final CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.setAllowedOrigins(Collections.singletonList("*"));
	    config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
	    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Bean
	public JWTLoginFilter jwtLoginFilter() throws Exception {
		return new JWTLoginFilter("/api/auth/login", authenticationManager());
	}
	
	@Bean JWTAuthenticationFilter jwtAuthenticationFilter() {
		return new JWTAuthenticationFilter();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("Configure WebSecurityConfig");
		String password = "123";
		String encryptedPassword = this.passwordEncoder().encode(password);
		
		InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> mgnBuilder = auth.inMemoryAuthentication();
		UserDetails u1 = User.withUsername("phuong").password(encryptedPassword).roles("USER").build();
		UserDetails u2 = User.withUsername("tien").password(encryptedPassword).roles("USER").build();
		mgnBuilder.withUser(u1);
		mgnBuilder.withUser(u2);
	}
	
}
