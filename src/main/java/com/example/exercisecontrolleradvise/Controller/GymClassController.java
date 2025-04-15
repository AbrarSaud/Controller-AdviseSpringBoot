package com.example.exercisecontrolleradvise.Controller;


import com.example.exercisecontrolleradvise.Api.ApiResponse;
import com.example.exercisecontrolleradvise.Model.GymClass;
import com.example.exercisecontrolleradvise.Service.GymClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gym/gym-class")
@RequiredArgsConstructor
public class GymClassController {
    private final GymClassService gymClassService;

    //     Get all GymClass
    @GetMapping("/get")
    public ResponseEntity<?> getAllGymClass() {
        return ResponseEntity.ok(gymClassService.getAllClassGym());
    }

    //     Add a new GymClass
    @PostMapping("/add")
    public ResponseEntity<?> addGymClass(@Valid @RequestBody GymClass gymClass, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isAdded = gymClassService.addGymClass(gymClass);

        if (!isAdded) {
            return ResponseEntity.status(404).body(new ApiResponse("Coach not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("GymClass added !!"));
    }

    //     Update GymClass
    @PutMapping("/update/{gymClass_id}")
    public ResponseEntity<?> updateGymClass(@PathVariable Integer gymClass_id, @Valid @RequestBody GymClass gymClass, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        Boolean isUpdate = gymClassService.updateGymClass(gymClass_id, gymClass);
        if (isUpdate) {
            return ResponseEntity.status(200).body(new ApiResponse("GymClass Update"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Not found"));
    }

    //      Delete GymClass
    @DeleteMapping("/delete/{gymClass_id}")
    public ResponseEntity<?> deleteGymClass(@PathVariable Integer gymClass_id) {
        Boolean isDelete = gymClassService.deleteGymClass(gymClass_id);
        if (isDelete) {
            return ResponseEntity.status(200).body(new ApiResponse("GymClass delete"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Not found"));
    }

    // (Endpoints #7) Update gym class capacity (BUT not capacity is same).
    @PutMapping("/update-capacity/{gymClassId}/{newCapacity}")
    public ResponseEntity<?> updateCapacity(@PathVariable Integer gymClassId, @PathVariable Integer newCapacity) {
        boolean isUpdated = gymClassService.updateCapacity(gymClassId, newCapacity);
        if (!isUpdated) {
            return ResponseEntity.status(404).body(new ApiResponse("GymClass not found or capacity is the same"));
        }
        return ResponseEntity.ok(new ApiResponse("Capacity updated !"));
    }

    // (Endpoints #8) Update gym class name.
    @PutMapping("/update-class-name/{classId}")
    public ResponseEntity<String> updateClassName(@PathVariable Integer classId, @RequestParam String newName) {
        String message = gymClassService.updateClassName(classId, newName);
        return ResponseEntity.ok(message);
    }

    // (Endpoints #9) Update gym class room number.
    @PutMapping("/update-room-number/{classId}")
    public ResponseEntity<?> updateRoomNumber(@PathVariable Integer classId, @RequestParam Integer newRoomNumber) {
        return ResponseEntity.ok(new ApiResponse(gymClassService.updateRoomNumber(classId, newRoomNumber)));
    }

}
