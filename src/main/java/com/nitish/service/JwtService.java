package com.nitish.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nitish.dao.UserDao;
import com.nitish.entity.JwtRequest;
import com.nitish.entity.JwtResponse;
import com.nitish.entity.User;
import com.nitish.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception
	{
		String userName = jwtRequest.getUserName();
		String userPassword = jwtRequest.getUserPassword();
		
		authenticate(userName,userPassword);
		
		final UserDetails userDetails = loadUserByUsername(userName);
		String newGenerateToken = jwtUtil.generateToken(userDetails);
		User user = userDao.findById(userName).get();
		
		return new JwtResponse(user,newGenerateToken);
	}
	
	private Set getAuthorities(User user)
	{
		Set authorities=new HashSet();
		user.getRole().forEach(role ->{
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
		});
		 return authorities;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDao.findById(username).get();
		if(user!=null)
		{
			return new org.springframework.security.core.userdetails.User(
					user.getUserName(),
					user.getUserPassword(),
					getAuthorities(user)
					
					);
			
		}else {
			
			throw new UsernameNotFoundException("Username is not valid");
		}
	}
	
	private void authenticate(String userName,String userPassword) throws Exception
	{
		try
		{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,userPassword));
			
		}catch(DisabledException ex)
		{
			throw new Exception("User is disabled");
			
		}catch(BadCredentialsException ex)
		{
			throw new Exception("Bad Credential from user");
		}
		
	}
}
