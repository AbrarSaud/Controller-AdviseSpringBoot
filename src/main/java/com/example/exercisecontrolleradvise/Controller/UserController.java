package com.example.exercisecontrolleradvise.Controller;

import com.example.exercisecontrolleradvise.Api.ApiResponse;
import com.example.exercisecontrolleradvise.Model.User;
import com.example.exercisecontrolleradvise.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/gym/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //     Get all Users
    @GetMapping("/get")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //     Add a new User
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added!"));
    }

    //     Update User
    @PutMapping("/update/{user_id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer user_id, @Valid @RequestBody User user) {
        userService.updateUser(user_id, user);
        return ResponseEntity.status(200).body(new ApiResponse("User Update"));
    }

    //      Delete User
    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer user_id) {
        userService.deleteUser(user_id);
        return ResponseEntity.status(200).body(new ApiResponse("User delete"));
    }

    //   (Endpoints # 1)  Get all users where BMI is bigger than or equal to 25.
    @GetMapping("/high-bmi")
    public ResponseEntity<?> getUsersWithBmi() {
        return ResponseEntity.ok(userService.getUsersWithBmi());
    }

    //  (Endpoints # 2)   Show a list of new users in the last 7 days.( calculate the date 7 days )
    @GetMapping("/new")
    public ResponseEntity<?> getNewUsersToday() {
        return ResponseEntity.ok(userService.getNewUsers());
    }

    //    (Endpoints # 3 ) calculate his BMI from weight and height by user ID.
//    Then save the new BMI and category in database.
    @PutMapping("/calculate-bmi/{user_id}")
    private ResponseEntity<?> calculateBmi(@PathVariable Integer user_id) {
        String message = userService.calculateBmi(user_id);
        return ResponseEntity.ok(new ApiResponse(message));
    }
}
