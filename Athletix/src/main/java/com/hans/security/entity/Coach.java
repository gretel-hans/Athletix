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
import java.util.Set;

import com.hans.security.security.PasswordConverter;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "coach", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class Coach implements Serializable {

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

    
    
    
    
    
    // Il caricamento EAGER delle raccolte significa che vengono recuperate 
    // completamente nel momento in cui viene recuperato il loro genitore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "coach_roles",
            joinColumns = @JoinColumn(name = "coach_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();
}
