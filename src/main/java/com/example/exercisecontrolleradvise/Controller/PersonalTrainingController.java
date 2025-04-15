package com.example.exercisecontrolleradvise.Controller;


import com.example.exercisecontrolleradvise.Api.ApiResponse;
import com.example.exercisecontrolleradvise.Model.PersonalTraining;
import com.example.exercisecontrolleradvise.Service.PersonalTrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gym/personal-training")
@RequiredArgsConstructor
public class PersonalTrainingController {
    private final PersonalTrainingService personalTrainingService;

    //     Get all PersonalTraining
    @GetMapping("/get")
    public ResponseEntity<?> getAllPersonalTraining() {
        return ResponseEntity.ok(personalTrainingService.getAllPersonalTraining());
    }

    //     Add a new PersonalTraining
    @PostMapping("/add")
    public ResponseEntity<?> addPersonalTraining(@RequestBody @Valid PersonalTraining personalTraining) {
        personalTrainingService.addPersonalTrainingPersonalTraining(personalTraining);
        return ResponseEntity.ok(new ApiResponse("PersonalTraining added !"));
    }

    //     Update PersonalTraining
    @PutMapping("/update/{pt_id}")
    public ResponseEntity<?> updatePersonalTraining(@PathVariable Integer pt_id, @Valid @RequestBody PersonalTraining personalTraining, Errors errors) {
        personalTrainingService.updatePersonalTraining(pt_id, personalTraining);

        return ResponseEntity.status(200).body(new ApiResponse("PersonalTraining Update"));

    }

    //      Delete PersonalTraining
    @DeleteMapping("/delete/{pt_id}")
    public ResponseEntity<?> deletePersonalTraining(@PathVariable Integer pt_id) {
        personalTrainingService.deletePersonalTraining(pt_id);

        return ResponseEntity.status(200).body(new ApiResponse("PersonalTraining delete"));

    }

    // (Endpoints #10) Get subscription information by personalTrainingId.
    @GetMapping("/subscription-info/{personalTrainingId}")
    public ResponseEntity<?> getSubscriptionInfo(@PathVariable Integer personalTrainingId) {
        String info = personalTrainingService.getSubscriptionInfo(personalTrainingId);
        if (info == null) {
            return ResponseEntity.status(404).body(new ApiResponse("Personal training not found"));
        }
        return ResponseEntity.ok(new ApiResponse(info));
    }

    // (Endpoints #11) Renew subscription by adding months.
    @PostMapping("/renew/{pt_id}/{months}")
    public ResponseEntity<?> renewSubscription(@PathVariable Integer pt_id, @PathVariable Integer months) {
        String response = personalTrainingService.renewSubscription(pt_id, months);
        if (response.contains("not found") || response.contains("only renew")) {
            return ResponseEntity.badRequest().body(new ApiResponse(response));
        }
        return ResponseEntity.ok(new ApiResponse(response));
    }

    // (Endpoints #12) Apply discount to subscription.
    @PostMapping("/discount")
    public ResponseEntity<?> applyDiscount(@RequestParam Integer personalTrainingId, @RequestParam Double discountPercentage) {
        return ResponseEntity.ok(new ApiResponse("Discount applied successfully. New price: " + personalTrainingService.applyDiscount(personalTrainingId, discountPercentage)));
    }

    // (Endpoints #13) Freeze the subscription by pt_id.
    @PostMapping("/freeze/{pt_id}")
    public ResponseEntity<?> freezeSubscription(@PathVariable Integer pt_id) {
        String response = personalTrainingService.freezeSubscription(pt_id);
        if (response.contains("not found")) {
            return ResponseEntity.status(404).body(new ApiResponse(response));
        }
        return ResponseEntity.ok(new ApiResponse(response));
    }

    // (Endpoints #14) Change the old coach to new coach.
    @PutMapping("/change-coach/{ptId}/{oldCoachId}/{newCoachId}")
    public ResponseEntity<?> changeCoach(@PathVariable Integer ptId, @PathVariable Integer oldCoachId, @PathVariable Integer newCoachId) {
        personalTrainingService.changeCoach(ptId, oldCoachId, newCoachId);
        return ResponseEntity.ok(new ApiResponse("Coach changed !"));
    }

    // (Endpoints #15) Extend freeze days for a subscription.
    @PutMapping("/extend-freeze/{pt_Id}")
    public ResponseEntity<?> extendFreeze(@PathVariable Integer pt_Id, @RequestParam Integer extraDays) {
        String message = personalTrainingService.extendFreeze(pt_Id, extraDays);
        return ResponseEntity.ok(new ApiResponse(message));
    }

}
