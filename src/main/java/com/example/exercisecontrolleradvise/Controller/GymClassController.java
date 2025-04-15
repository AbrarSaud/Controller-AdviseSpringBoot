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
    public ResponseEntity<?> addGymClass(@Valid @RequestBody GymClass gymClass) {
        gymClassService.addGymClass(gymClass);
        return ResponseEntity.status(200).body(new ApiResponse("GymClass added !!"));
    }

    //     Update GymClass
    @PutMapping("/update/{gymClass_id}")
    public ResponseEntity<?> updateGymClass(@PathVariable Integer gymClass_id, @Valid @RequestBody GymClass gymClass) {
        gymClassService.updateGymClass(gymClass_id, gymClass);
        return ResponseEntity.status(200).body(new ApiResponse("GymClass Update"));
    }

    //      Delete GymClass
    @DeleteMapping("/delete/{gymClass_id}")
    public ResponseEntity<?> deleteGymClass(@PathVariable Integer gymClass_id) {
        gymClassService.deleteGymClass(gymClass_id);
        return ResponseEntity.status(200).body(new ApiResponse("GymClass delete"));
    }

    // (Endpoints #7) Update gym class capacity (BUT not capacity is same).
    @PutMapping("/update-capacity/{gymClassId}/{newCapacity}")
    public ResponseEntity<?> updateCapacity(@PathVariable Integer gymClassId, @PathVariable Integer newCapacity) {
        gymClassService.updateCapacity(gymClassId, newCapacity);
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
