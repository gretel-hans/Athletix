package com.hans.security.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hans.enums.ERole;
import com.hans.security.entity.Role;
import com.hans.security.entity.Utente;
import com.hans.security.exception.MyAPIException;
import com.hans.security.payload.LoginDto;
import com.hans.security.payload.RegisterDto;
import com.hans.security.repository.RoleRepository;
import com.hans.security.repository.UserRepository;
import com.hans.security.security.JwtTokenProvider;


@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        
    	Authentication authentication = authenticationManager.authenticate(
        		new UsernamePasswordAuthenticationToken(
        				loginDto.getUsername(), loginDto.getPassword()
        		)
        ); 
    	System.out.println(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        System.out.println(token);
        return token;
    }

    @Override
    public Utente register(RegisterDto registerDto) {

        // add check for username exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new MyAPIException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        // add check for email exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new MyAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        Utente user = new Utente();
        user.setName(registerDto.getName());
        user.setLastname(registerDto.getLastname());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setDateRegistration(LocalDateTime.now());
        user.setAge(registerDto.getAge());
        user.setBirthdate(registerDto.getBirthdate());

        Set<Role> roles = new HashSet<>();
        
        if(registerDto.getRoles() != null) {
	        registerDto.getRoles().forEach(role -> {
	        	Role userRole = roleRepository.findByRoleName(getRole(role)).get();
	        	roles.add(userRole);
	        });
        } else {
        	Role userRole = roleRepository.findByRoleName(ERole.ROLE_ATLETA).get();
        	roles.add(userRole);
        }
        
        user.setRoles(roles);
        System.out.println(user);
       Utente u= userRepository.save(user);

        return u;
    }
    
    public ERole getRole(String role) {
    	if(role.equalsIgnoreCase("ADMIN")) return ERole.ROLE_ADMIN;
    	else if(role.equalsIgnoreCase("SOCIETA")) return ERole.ROLE_SOCIETA;
    	else if(role.equalsIgnoreCase("ALLENATORE")) return ERole.ROLE_ALLENATORE;
    	else return ERole.ROLE_ATLETA;
    }
    
    @Override
 	public List<Utente> getAll() {
 		return userRepository.findAll();
 	}
 	
// 	public List<AuthUser> getByCreditCard(String creditCard) {
// 		return userRepository.findByCreditCard(creditCard);
// 	}
    
}
