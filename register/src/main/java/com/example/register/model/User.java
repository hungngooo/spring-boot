package com.example.register.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long")
    private String name;
    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank
    @Size(min = 6, max = 20, message = "password must be between 2 and 20 characters long")
    private String password;
}
