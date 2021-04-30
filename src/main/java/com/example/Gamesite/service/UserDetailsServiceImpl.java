package com.example.Gamesite.service;

import java.util.HashSet;
import java.util.Set;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Gamesite.model.User;
import com.example.Gamesite.repository.UserRepository;

/**
 * This class is used by spring security to authenticate and authorize user
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User curruser = userRepository.findByUsername(username);
		
		UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPassword(),
				AuthorityUtils.createAuthorityList(curruser.getRole()));
		return user;
	}

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
//    {   
//    	User curruser = repository.findByName(username);
//        UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(), 
//        		AuthorityUtils.createAuthorityList(curruser.getRole()));
//        return user;
//    }

//	@Override
//	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
//		User user = userRepository.findByEmail(userName)
//				.orElseThrow(() -> new UsernameNotFoundException("Email " + userName + " not found"));
//		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
//	}
//	
//	 private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
//	        String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
//	        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
//	        return authorities;
//	    }
//
//	@Override
//	@Transactional(readOnly = true)
//	public UserDetails loadUserByUsername(String username) {
//		User user = userRepository.findByUsername(username);
//		if (user == null)
//			throw new UsernameNotFoundException(username);
//
//		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//		for (Role role : user.getRoles()) {
//			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//		}
//
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//				grantedAuthorities);
//	}
}