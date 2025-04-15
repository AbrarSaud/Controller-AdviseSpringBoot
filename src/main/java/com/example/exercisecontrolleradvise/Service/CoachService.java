package com.example.exercisecontrolleradvise.Service;


import com.example.exercisecontrolleradvise.Model.Coach;
import com.example.exercisecontrolleradvise.Model.User;
import com.example.exercisecontrolleradvise.Repository.CoachRepository;
import com.example.exercisecontrolleradvise.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachService {
    private final CoachRepository coachRepository;
    private final UserRepository userRepository;


    //     Get all Coach
    public List<Coach> getAllCoach() {
        return coachRepository.findAll();
    }

    //     Add a new Coach
    public Boolean addCoach(Coach coach) {
        if (coach == null) {
            return false;
        }
        coachRepository.save(coach);
        return true;
    }

    //     Update a Coach
    public Boolean updateCoach(Integer coach_id, Coach coach) {
        Coach oldCoach = coachRepository.findCoachByCoachId(coach_id);
        if (oldCoach == null) {
            return false;
        }
        oldCoach.setName(coach.getName());
        if (coach.getEmail() != null) {
            oldCoach.setEmail(coach.getEmail());
        }
        oldCoach.setPassword(coach.getPassword());

        oldCoach.setYearsOfExperience(coach.getYearsOfExperience());
        coachRepository.save(oldCoach);
        return true;
    }

    //     Delete a Coach
    public Boolean deleteCoach(Integer coach_id) {
        Coach deleteCoach = coachRepository.findCoachByCoachId(coach_id);
        if (deleteCoach == null) {
            return false;
        }
        coachRepository.delete(deleteCoach);
        return true;
    }

    // (Endpoints #4) Show the best coaches (ordered by experience )
    public List<Coach> getAllCoachesOrderedByExperience() {
        return coachRepository.getAllCoachesOrderByYearsOfExperienceDesc();
    }

    // (Endpoints #5) Update coach experience (if new experience is bigger.)
    public Coach updateCoachExperience(Integer coach_id, Integer newYearsExperience) {
        Coach coach = coachRepository.findCoachByCoachId(coach_id);
        if (coach != null && newYearsExperience > coach.getYearsOfExperience()) {
            coach.setYearsOfExperience(newYearsExperience);
            coachRepository.save(coach);
            return coach;
        }
        return null;
    }

    // (Endpoints #6) Promote user to coach , change a user to a coach.(save new coach, and delete user.)
    public boolean promoteUserToCoach(Integer userId) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            return false;
        }
        Coach coach = new Coach();
        coach.setName(user.getName());
        coach.setEmail(user.getEmail());
        coach.setPassword(user.getPassword());
        coach.setYearsOfExperience(0);
        coachRepository.save(coach);
        userRepository.delete(user);
        return true;
    }
}
