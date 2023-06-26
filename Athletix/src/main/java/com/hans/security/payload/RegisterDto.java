package com.hans.security.payload;

import java.time.LocalDate;
import java.util.Set;

import com.hans.enums.UserSex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String name;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private LocalDate birthdate;
    private Integer age;
    private UserSex sex;

    // Passagio di ruoli dal client (Facoltativo)
    private Set<String> roles;
}


/*{
    "name": "Franc",
    "lastname": "Adjei",
    "username": "fra",
    "email": "sewue.hans@example.com",
    "password": "Malaguzzi18",
    "birthdate":"2003-10-18",
    "age":19,
    "sex":"Maschio",
    "roles": ["Allenatore"]
}*/
