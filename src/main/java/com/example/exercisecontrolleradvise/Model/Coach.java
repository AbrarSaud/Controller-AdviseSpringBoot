package com.example.exercisecontrolleradvise.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coachId;

    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "Name must not be Empty")
    private String name;

    @NotEmpty(message = "The email must not be empty")
    @Email()
    @Column(columnDefinition = "varchar(255) not null unique")
    private String email;

    @NotEmpty(message = "The password must not be empty")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{7,}$", message = "Password must contain at least one letter and one number")
    @Column(columnDefinition = "varchar(255) not null")
    private String password;

    @NotNull(message = "The 'Coach Id' must be NOT Null")
    @Column(columnDefinition = "int not null")
    @PositiveOrZero
    private Integer yearsOfExperience;


}
