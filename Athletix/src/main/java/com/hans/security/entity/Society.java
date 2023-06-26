package com.hans.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hans.security.security.PasswordConverter;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "society", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class Society implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="society_name")
    private String societyName;

    private Address address;
    
    @OneToMany
    @JoinColumn(name="athletes_list")
    private List<Athlete> athletesList;

    @OneToMany
    @JoinColumn(name="coaches_list")
    private List<Coach> coachesList;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Convert(converter = PasswordConverter.class)
    @Column(nullable = false)
    private String password;
    
    @Column(name = "date_registration")
    private LocalDateTime dateRegistration;

    private Role role;
}
