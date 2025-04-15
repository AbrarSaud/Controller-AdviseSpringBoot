package com.example.exercisecontrolleradvise.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotEmpty(message = "The user name must not be empty")
    @Size(min = 4, message = "The 'username' be more than 4 length")
    @Column(columnDefinition = "varchar(150) not null unique")
    private String userName;

    @Column(columnDefinition = "varchar(20) not null")
    @NotEmpty(message = "Name must not be Empty")
    private String name;

    @NotEmpty(message = "The email must not be empty")
    @Email()
    @Column(columnDefinition = "varchar(255) not null unique")
    private String email;

    @NotNull(message = "The age must not be empty")
    @Column(columnDefinition = "int not null ")
    private Integer age;

    @NotEmpty(message = "The password must not be empty")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{7,}$", message = "Password must contain at least one letter and one number")
    @Column(columnDefinition = "varchar(255) not null")
    private String password;

    @NotNull(message = "The 'weight' must be NOT Null")
    @Positive
    @Column(columnDefinition = "int not null")
    private Integer weight;

    @NotNull(message = "The 'weight' must be NOT Null")
    @Positive
    @Column(columnDefinition = "int not null")
    private Integer height;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date")
    private LocalDate startData;

    @Column(columnDefinition = "double")
    private Double bmi=00.00;

    @Column(columnDefinition = "varchar(255) ")
    private String categoryBmi=" ";


}
