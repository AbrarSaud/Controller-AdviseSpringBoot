package com.example.exercisecontrolleradvise.Repository;


import com.example.exercisecontrolleradvise.Model.PersonalTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalTrainingRepository extends JpaRepository<PersonalTraining, Integer> {
    PersonalTraining findPersonalTrainingByPersonalTrainingId(Integer personalTrainingId);

}


