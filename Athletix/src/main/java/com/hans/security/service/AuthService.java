package com.hans.security.service;

import java.util.List;

import com.hans.security.entity.Utente;
import com.hans.security.payload.LoginDto;
import com.hans.security.payload.RegisterDto;

public interface AuthService {
    
	String login(LoginDto loginDto);
    Utente register(RegisterDto registerDto);
    List<Utente> getAll();
    
}
