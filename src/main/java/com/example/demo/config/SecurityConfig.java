package com.example.demo.config;

import com.example.demo.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	private final CustomUserDetailService  customUserDetailService;

	@Autowired
	public SecurityConfig(@Qualifier("CustomUserDetailService") CustomUserDetailService customUserDetailService) {
		this.customUserDetailService = customUserDetailService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
		.authorizeRequests().antMatchers("/css/**","/js/**").permitAll()
		.anyRequest()
		.authenticated()
		.and().
		formLogin().
		loginPage("/login").permitAll()
				.defaultSuccessUrl("/home").permitAll()
		.and()
		.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login?logout")
		.permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailService);
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
