package com.ske.bookshop.securityJwt.controllers;

import com.ske.bookshop.exceptions.NotFoundException;
import com.ske.bookshop.person.PersonRepository;
import com.ske.bookshop.person.Person;
import com.ske.bookshop.securityJwt.models.ERole;
import com.ske.bookshop.securityJwt.models.Role;
import com.ske.bookshop.securityJwt.models.User;
import com.ske.bookshop.securityJwt.payload.request.LoginRequest;
import com.ske.bookshop.securityJwt.payload.request.SignupRequest;
import com.ske.bookshop.securityJwt.payload.response.JwtResponse;
import com.ske.bookshop.securityJwt.payload.response.MessageResponse;
import com.ske.bookshop.securityJwt.repository.RoleRepository;
import com.ske.bookshop.securityJwt.repository.UserRepository;
import com.ske.bookshop.securityJwt.security.jwt.JwtUtils;
import com.ske.bookshop.securityJwt.security.services.UserDetailsImpl;
import com.ske.bookshop.person.PersonRepository;
import com.ske.bookshop.securityJwt.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		if(userRepository.findByUsername(loginRequest.getUsername()).isEmpty()){
			return ResponseEntity.badRequest().body("User not found");
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User(signUpRequest.getUsername(),
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseGet(
				()-> roleRepository.insert(new Role(ERole.ROLE_USER))
		);

		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
		Person person = new Person(null, null, null, user.getEmail());
		personRepository.save(person);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}




}
