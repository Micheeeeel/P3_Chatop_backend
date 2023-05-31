
package com.chatop.backend.service;

import java.util.ArrayList;

import com.chatop.backend.model.DAOUser;
import com.chatop.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// The Spring Security Authentication Manager calls this method for getting the user details from the database when authenticating the user details provided by the user.
// Here we are getting the user details from a hardcoded User List.
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public CustomUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return loadUserByUserEmail(userName);	// because I authenticate with email

	}

	public CustomUserDetails loadUserByUserEmail(String userEmail) throws UsernameNotFoundException {
		DAOUser user = userRepository.findByEmail(userEmail);	// authentication is done by email
		if (user == null) {
			throw new UsernameNotFoundException("User not found with user email: " + userEmail);
		}
		return new CustomUserDetails(user);

	}

	public DAOUser save(DAOUser user) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
}
