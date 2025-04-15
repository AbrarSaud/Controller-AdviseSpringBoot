package com.example.exercisecontrolleradvise.Controller;

import com.example.exercisecontrolleradvise.Api.ApiResponse;
import com.example.exercisecontrolleradvise.Model.Coach;
import com.example.exercisecontrolleradvise.Service.CoachService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/gym/coach")
@RequiredArgsConstructor
public class CoachController {
    private final CoachService coachService;


    //     Get all Coach
    @GetMapping("/get")
    public ResponseEntity<?> getAllCoach() {
        return ResponseEntity.ok(coachService.getAllCoach());
    }

    //     Add a new Coach
    @PostMapping("/add")
    public ResponseEntity<?> addCoach(@Valid @RequestBody Coach coach, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isAdded = coachService.addCoach(coach);
        if (!isAdded) {
            return ResponseEntity.status(404).body(new ApiResponse("Coach not be added!"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Coach added !!"));
    }


    //     Update Coach
    @PutMapping("/update/{coach_id}")
    public ResponseEntity<?> updateCoach(@PathVariable Integer coach_id, @Valid @RequestBody Coach coach, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        Boolean isUpdate = coachService.updateCoach(coach_id, coach);
        if (isUpdate) {
            return ResponseEntity.status(200).body(new ApiResponse("Coach Update"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Not found"));
    }

    //      Delete Coach
    @DeleteMapping("/delete/{coach_id}")
    public ResponseEntity<?> deleteCoach(@PathVariable Integer coach_id) {
        Boolean isDelete = coachService.deleteCoach(coach_id);
        if (isDelete) {
            return ResponseEntity.status(200).body(new ApiResponse("Coach delete"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Not found"));
    }

    // (Endpoints #4) Show the best coaches (ordered by experience )
    @GetMapping("/top-coaches")
    public ResponseEntity<?> getCoachesOrdered() {
        return ResponseEntity.ok(coachService.getAllCoachesOrderedByExperience());
    }

    // (Endpoints #5) Update coach experience (if new experience is bigger.)
    @PutMapping("/update-experience/{coach_id}/{newYearsExperience}")
    public ResponseEntity<?> updateCoachExperience(@PathVariable Integer coach_id, @PathVariable Integer newYearsExperience) {
        Coach updateCoach = coachService.updateCoachExperience(coach_id, newYearsExperience);
        if (updateCoach == null) {
            return ResponseEntity.status(404).body(new ApiResponse("Coach not found or experience not updated."));
        }
        return ResponseEntity.ok(new ApiResponse("Coach Update"));
    }

    // (Endpoints #6) Promote user to coach , change a user to a coach.(save new coach, and delete user.)
    @PostMapping("/promote/{userId}")
    public ResponseEntity<?> promoteUserToCoach(@PathVariable Integer userId) {
        boolean check = coachService.promoteUserToCoach(userId);
        if (check) {
            return ResponseEntity.ok(new ApiResponse("User promoted to Coach successfully!"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Not found"));
    }
}
