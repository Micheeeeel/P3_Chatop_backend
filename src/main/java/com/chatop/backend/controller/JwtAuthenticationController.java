
package com.chatop.backend.controller;


import com.chatop.backend.model.DAOUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.chatop.backend.service.JwtUserDetailsService;


import com.chatop.backend.configuration.JwtTokenUtil;
import com.chatop.backend.model.JwtRequest;
import com.chatop.backend.model.JwtResponse;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;


	@PostMapping("/api/auth/register")
	public ResponseEntity<?> saveUser(@RequestBody DAOUser user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	@PostMapping("/api/auth/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getLogin(), authenticationRequest.getPassword());
		
		// Si l'authentification réussit, la méthode authenticate() ne lèvera pas d'exception et l'exécution continuera. 
		// Dans le cas contraire, une exception sera levée, indiquant que l'authentification a échoué.
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getLogin());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}



	private void authenticate(String login, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
