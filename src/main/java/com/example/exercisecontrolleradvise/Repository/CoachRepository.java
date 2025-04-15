package com.example.exercisecontrolleradvise.Repository;


import com.example.exercisecontrolleradvise.Model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Integer> {
    // Find one Coach class by CoachId.
    Coach findCoachByCoachId(Integer coachId);

    // Show the best coaches using desc
    @Query("select c from Coach c order by  c.yearsOfExperience desc ")
    List<Coach> getAllCoachesOrderByYearsOfExperienceDesc();



}
