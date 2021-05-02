package com.example.Gamesite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	// UserDetailService is used to configure the password generation encoding
	@Qualifier("userDetailsServiceImpl")
	@Autowired
	private UserDetailsService userDetailsService;
	
	// springSecurityDialect is used to access login information in html templates
	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}
	
	// bCryptPasswordEncoder is the default encoding of passwords
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// configure the password encoding to use bCryptPasswordEncoder
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	 
	// configure network security
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http

			// Disable CSRF to enable saving scores from javascript
			.csrf().disable()
			
			// Allow iframe to load from anywhere
			.headers().addHeaderWriter(new StaticHeadersWriter(
			        "X-Content-Security-Policy",
			        "frame-ancestors self *"))
			.and()
			.headers().addHeaderWriter(new StaticHeadersWriter(
			        "Content-Security-Policy",
			        "frame-ancestors self *"))
			.and()
			
			// Set which pages can be accessed without logging in
			.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/registration", "/gamelist/**", "/play/**", "/", "/game-photos/**").permitAll()
				.anyRequest().authenticated()
				.and()
				
			// Configure login
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
				
			// Configure logout
			.logout()
				.permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
	}
	
	// AuthenticationManager required in SecurityServiceImpl
	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}
}
