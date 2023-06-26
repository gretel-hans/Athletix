package com.hans.security.runner;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hans.enums.ERole;
import com.hans.enums.UserSex;
import com.hans.security.entity.Role;
import com.hans.security.payload.RegisterDto;
import com.hans.security.repository.RoleRepository;
import com.hans.security.repository.UserRepository;
import com.hans.security.service.AuthService;


@Component
public class AuthRunner implements ApplicationRunner {
	
	@Autowired RoleRepository roleRepository;
	@Autowired UserRepository userRepository;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired AuthService authService;
	
	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Run...");
		// Metodo da lanciare solo la prima volta
		// Serve per salvare i ruoli nel DB
		
		if(roleRepository.findAll().size() == 0) {
			setRoleDefault();
		}
		
		if(userRepository.findAll().size() == 0) {
			saveUser();
		}
		
		
		
	}
	
	private void setRoleDefault() {
		Role admin = new Role();
		admin.setRoleName(ERole.ROLE_ADMIN);
		roleRepository.save(admin);
		
		Role atleta = new Role();
		atleta.setRoleName(ERole.ROLE_ATLETA);
		roleRepository.save(atleta);
		
		Role allenatore = new Role();
		allenatore.setRoleName(ERole.ROLE_ALLENATORE);
		roleRepository.save(allenatore);
		
		Role societa = new Role();
		societa.setRoleName(ERole.ROLE_SOCIETA);
		roleRepository.save(societa);
		
	}
	
	private void saveUser() {
		RegisterDto u = new RegisterDto(); 
		u.setName("Mario");
		u.setLastname("Rossi");
		u.setUsername("marros");
		u.setEmail("m.rossi@example.com");
		u.setAge(19);
		u.setPassword("qwerty");
		u.setBirthdate(LocalDate.of(2003, 10, 18));
		u.setSex(UserSex.Maschio);
		authService.register(u);
	}
	

	

}
