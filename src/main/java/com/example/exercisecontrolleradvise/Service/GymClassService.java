package com.example.exercisecontrolleradvise.Service;


import com.example.exercisecontrolleradvise.Api.ApiException;
import com.example.exercisecontrolleradvise.Model.Coach;
import com.example.exercisecontrolleradvise.Model.GymClass;
import com.example.exercisecontrolleradvise.Repository.CoachRepository;
import com.example.exercisecontrolleradvise.Repository.GymClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GymClassService {
    private final GymClassRepository gymClassRepository;
    private final CoachRepository coachRepository;

    //     Get all ClassGym
    public List<GymClass> getAllClassGym() {
        return gymClassRepository.findAll();
    }

    //     Add a new GymClass
    public void addGymClass(GymClass gymClass) {
        Coach coach = coachRepository.findCoachByCoachId(gymClass.getCoachId());
        if (coach == null) {
            throw new ApiException("Coach Not found");
        }
        gymClassRepository.save(gymClass);
    }

    //     Update a GymClass
    public void updateGymClass(Integer gymClass_id, GymClass gymClass) {
        GymClass oldGymClass = gymClassRepository.findGymClassByGymClassId(gymClass_id);
        Coach coach = coachRepository.findCoachByCoachId(gymClass.getCoachId());
        if (oldGymClass == null && coach == null) {
            throw new ApiException("GymClass or Coach Not found");
        }
        oldGymClass.setName(gymClass.getName());
        oldGymClass.setTime(gymClass.getTime());
        oldGymClass.setCapacity(gymClass.getCapacity());
        oldGymClass.setRoomNum(gymClass.getRoomNum());
        gymClassRepository.save(oldGymClass);
    }

    //     Delete a GymClass
    public void deleteGymClass(Integer gymClass_id) {
        GymClass deleteGymClass = gymClassRepository.findGymClassByGymClassId(gymClass_id);
        if (deleteGymClass == null) {
            throw new ApiException("GymClass Not found");
        }
        gymClassRepository.delete(deleteGymClass);

    }

    // (Endpoints #7) Update gym class capacity (BUT not capacity is same).
    public void updateCapacity(Integer gymClassId, Integer newCapacity) {
        GymClass gymClass = gymClassRepository.findGymClassByGymClassId(gymClassId);

        if (gymClass == null) {
            throw new ApiException("GymClass Not found");

        }
        if (gymClass.getCapacity() == (newCapacity)) {
            throw new ApiException("capacity is the same");
        }
        gymClass.setCapacity(newCapacity);
        gymClassRepository.save(gymClass);
    }

    // (Endpoints #8) Update gym class name.
    public String updateClassName(Integer classId, String newName) {
        GymClass gymClass = gymClassRepository.findGymClassByGymClassId(classId);
        gymClass.setName(newName);
        gymClassRepository.save(gymClass);
        return "Class name updated successfully";
    }

    // (Endpoints #9) Update gym class room number.
    public String updateRoomNumber(Integer classId, Integer newRoomNumber) {
        GymClass updatedRows = gymClassRepository.findGymClassByGymClassIdAndRoomNum(classId, newRoomNumber);

        if (updatedRows.getRoomNum() > 0) {
            return "Room number updated successfully.";
        } else {
            throw new ApiException("Class not found or update failed.");
        }
    }

}
