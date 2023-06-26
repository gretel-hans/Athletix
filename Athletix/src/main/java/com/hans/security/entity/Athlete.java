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

import com.hans.entity.Results;
import com.hans.enums.UserSex;
import com.hans.security.security.PasswordConverter;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "athlete", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class Athlete implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private String lastname;
    
    private Integer age;
    
    private LocalDate birthdate;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Convert(converter = PasswordConverter.class)
    @Column(nullable = false)
    private String password;
    
    @Column(name = "date_registration")
    private LocalDateTime dateRegistration;

    @OneToMany(fetch = FetchType.EAGER)
    List<Results> athleteResults;
    
    
    
    
    
    // Il caricamento EAGER delle raccolte significa che vengono recuperate 
    // completamente nel momento in cui viene recuperato il loro genitore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "athlete_roles",
            joinColumns = @JoinColumn(name = "athlete_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();
}
