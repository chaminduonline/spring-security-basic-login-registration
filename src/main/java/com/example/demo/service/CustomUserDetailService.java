package com.example.demo.service;

import com.example.demo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

@Service("CustomUserDetailService")
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = this.userRepository.findByUserName(username);
			if(user.getEnable()){
				return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),authorities(user.getRoles()));
			}else {
				throw new UsernameNotFoundException("disable account");
			}
		}catch (NullPointerException e){
			throw new UsernameNotFoundException("invalid username or password");
		}
	}

	private List<GrantedAuthority> authorities(List<Role> roles){
		List<GrantedAuthority> roleList = new ArrayList<>();
		for (Role role:roles) {
			roleList.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return roleList;
	}
	
	
}
