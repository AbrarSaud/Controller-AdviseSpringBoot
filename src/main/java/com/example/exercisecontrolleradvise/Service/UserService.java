package com.example.exercisecontrolleradvise.Service;


import com.example.exercisecontrolleradvise.Api.ApiException;
import com.example.exercisecontrolleradvise.Model.User;
import com.example.exercisecontrolleradvise.Repository.GymClassRepository;
import com.example.exercisecontrolleradvise.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GymClassRepository classGymRepository;

    //     Get all Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //     Add a new User
    public void addUser(User user) {
        userRepository.save(user);
    }

    //     Update a User
    public void updateUser(Integer user_id, User user) {
        User oldUser = userRepository.findUserByUserId(user_id);
        if (oldUser == null) {
            throw new ApiException("User Not found");
        }
        oldUser.setName(user.getName());
        if (user.getUserName() != null) {
            oldUser.setUserName(user.getUserName());
        }
        oldUser.setPassword(user.getPassword());
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        oldUser.setAge(user.getAge());
        oldUser.setWeight(user.getWeight());
        oldUser.setHeight(user.getHeight());
        oldUser.setStartData(user.getStartData());
        userRepository.save(oldUser);

    }

    //     Delete a User
    public void deleteUser(Integer user_id) {
        User userToDelete = userRepository.findUserByUserId(user_id);
        if (userToDelete == null) {
            throw new ApiException("User Not found");
        }
        userRepository.delete(userToDelete);
    }

    //    (Endpoints # 1)  We get all users where BMI is bigger than or equal to 25.
    public List<User> getUsersWithBmi() {
        return userRepository.getUsersWithBmiGreater();
    }

    //  (Endpoints # 2)   Show a list of new users in the last 7 days.( calculate the date 7 days )
    public List<User> getNewUsers() {
        LocalDate weekAgo = LocalDate.now().minusDays(7);
        return userRepository.findNewUsers(weekAgo);
    }

    //    (Endpoints # 3 ) calculate his BMI from weight and height by user ID.
    //    Then save the new BMI and category in database.
    public String calculateBmi(Integer user_id) {
        User user = userRepository.findUserByUserId(user_id);
        if (user == null) {
            throw new ApiException("User Not found");
        }
        Double bmi = user.getWeight() / Math.pow(user.getHeight() / 100.0, 2);
        bmi = (int) (bmi * 100) / 100.0;
        user.setBmi(bmi);
        String category;
        if (bmi < 18.5) {
            category = "Underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            category = "Normal weight";
        } else if (bmi >= 25 && bmi < 29.9) {
            category = "Overweight";
        } else {
            category = "Obese";
        }
        user.setCategoryBmi(category);
        userRepository.save(user);
        return "BMI is " + bmi + "Your is " + category;
    }

}
