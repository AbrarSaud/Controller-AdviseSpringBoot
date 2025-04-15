package com.example.exercisecontrolleradvise.Service;


import com.example.exercisecontrolleradvise.Api.ApiException;
import com.example.exercisecontrolleradvise.Model.Coach;
import com.example.exercisecontrolleradvise.Model.PersonalTraining;
import com.example.exercisecontrolleradvise.Model.User;
import com.example.exercisecontrolleradvise.Repository.CoachRepository;
import com.example.exercisecontrolleradvise.Repository.PersonalTrainingRepository;
import com.example.exercisecontrolleradvise.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalTrainingService {
    private final PersonalTrainingRepository personalTrainingRepository;
    private final UserRepository userRepository;
    private final CoachRepository coachRepository;


    //     Get all PersonalTraining
    public List<PersonalTraining> getAllPersonalTraining() {
        return personalTrainingRepository.findAll();
    }

    //     Add a new PersonalTraining
    public void addPersonalTrainingPersonalTraining(PersonalTraining personalTraining) {
        User user = userRepository.findUserByUserId(personalTraining.getUserId());
        Coach coach = coachRepository.findCoachByCoachId(personalTraining.getCoachId());
        if (user == null || coach == null) {
            throw new ApiException("User or Coach not found !");
        }
        if (personalTraining.getSubscriptionMonths() != 3 && personalTraining.getSubscriptionMonths() != 6) {
            throw new ApiException(" months must be   3 or 6 !");
        }
        price(personalTraining);
        personalTrainingRepository.save(personalTraining);

    }

    //     Update a PersonalTraining
    public void updatePersonalTraining(Integer pt_id, PersonalTraining personalTraining) {
        PersonalTraining oldPersonalTraining = personalTrainingRepository.findPersonalTrainingByPersonalTrainingId(pt_id);
        User user = userRepository.findUserByUserId((personalTraining.getUserId()));
        Coach coach = coachRepository.findCoachByCoachId(personalTraining.getCoachId());
        if (oldPersonalTraining == null || user != null || coach != null) {
            throw new ApiException("not fond");
        }
        oldPersonalTraining.setStartDate(personalTraining.getStartDate());
        oldPersonalTraining.setSubscriptionMonths(personalTraining.getSubscriptionMonths());
        oldPersonalTraining.setPrice((personalTraining.getPrice()));

        personalTrainingRepository.save(oldPersonalTraining);

    }

    //     Delete a PersonalTraining
    public void deletePersonalTraining(Integer pt_id) {
        PersonalTraining deletePersonalTraining = personalTrainingRepository.findPersonalTrainingByPersonalTrainingId(pt_id);
        if (deletePersonalTraining == null) {
            throw new ApiException("Not Found");
        }
        personalTrainingRepository.delete(deletePersonalTraining);
    }

    // (Endpoints #10) Get subscription information by personalTrainingId.
    public String getSubscriptionInfo(Integer pt_id) {
        PersonalTraining personalTraining = personalTrainingRepository.findPersonalTrainingByPersonalTrainingId(pt_id);
        if (personalTraining != null) {
            LocalDate startDate = personalTraining.getStartDate();
            Integer subscriptionMonths = personalTraining.getSubscriptionMonths();
            LocalDate endDate = startDate.plusMonths(subscriptionMonths);

            LocalDate today = LocalDate.now();
            long daysLeft = endDate.toEpochDay() - today.toEpochDay();
            return "The subscription ends on: " + endDate + ", and there are " + daysLeft + " days left.";
        }
        throw new ApiException("Personal Training not found");

    }

    // (Endpoints #11) Renew subscription by adding months.
    public String renewSubscription(Integer pt_id, Integer months) {
        PersonalTraining personalTraining = personalTrainingRepository.findPersonalTrainingByPersonalTrainingId(pt_id);
        User user = userRepository.findUserByUserId(personalTraining.getUserId());
        Coach coach = coachRepository.findCoachByCoachId(personalTraining.getCoachId());
        if (coach == null || user == null) {
            throw new ApiException("User or Coach not found.");
        }
        if (months != 3 && months != 6) {
            throw new ApiException("You can only renew for 3 or 6 months");
        }
        personalTraining.setUserId(personalTraining.getUserId());
        personalTraining.setCoachId(personalTraining.getCoachId());
        personalTraining.setStartDate(LocalDate.now());
        personalTraining.setSubscriptionMonths(months);
        price(personalTraining);
        personalTrainingRepository.save(personalTraining);

        return "Subscription renewed successfully for " + months + " months. Total price: " + personalTraining.getPrice();
    }

    // (Endpoints #12) Apply discount to subscription.
    public int applyDiscount(Integer pt_Id, double discountPercentage) {
        PersonalTraining personalTraining = personalTrainingRepository.findPersonalTrainingByPersonalTrainingId(pt_Id);
        price(personalTraining);
        double discount = (personalTraining.getPrice() * discountPercentage) / 100;
        double newPrice = personalTraining.getPrice() - discount;

        personalTraining.setPrice((int) newPrice);
        personalTrainingRepository.save(personalTraining);
        return (int) newPrice;
    }

    // (Endpoints #13) Freeze the subscription by pt_id.
    public String freezeSubscription(Integer pt_Id) {
        PersonalTraining personalTraining = personalTrainingRepository.findPersonalTrainingByPersonalTrainingId(pt_Id);
        if (personalTraining == null) {
            throw new ApiException("Personal training not found.");
        }
        personalTraining.setIsFreeze(true);
        personalTraining.setFreezeEndDate(LocalDate.now().plusDays(20));
        personalTrainingRepository.save(personalTraining);
        return "Subscription frozen successfully for 20 days.";
    }

    // (Endpoints #14) Change the old coach to new coach.
    public void changeCoach(Integer ptId, Integer oldCoachId, Integer newCoachId) {
        PersonalTraining personalTraining = personalTrainingRepository.findPersonalTrainingByPersonalTrainingId(ptId);
        if (personalTraining == null) {
            throw new ApiException("PersonalTraining  not found");
        }
        if (!personalTraining.getCoachId().equals(oldCoachId)) {
            throw new ApiException(" Coach not found");
        }
        personalTraining.setCoachId(newCoachId);
        personalTrainingRepository.save(personalTraining);
    }

    // (Endpoints #15) Extend freeze days for a subscription.
    public String extendFreeze(Integer pt_Id, Integer extraDays) {
        PersonalTraining personalTraining = personalTrainingRepository.findPersonalTrainingByPersonalTrainingId(pt_Id);
        if (personalTraining == null) {
            throw new ApiException("Personal training not found.");
        }
        if (personalTraining.getFreezeEndDate() == null) {
            throw new ApiException("No active freeze to extend.");
        }
        if (extraDays > 14) {
            throw new ApiException("Cannot extend freeze by more than 14 days.");
        }

        personalTraining.setFreezeEndDate(personalTraining.getFreezeEndDate().plusDays(extraDays));
        personalTrainingRepository.save(personalTraining);
        return "Freeze period extended successfully by " + extraDays + " days.";
    }

    public void price(PersonalTraining personalTraining) {
        Integer price = 0;
        if (personalTraining.getSubscriptionMonths() == 3) {
            price = 1518;
        } else if (personalTraining.getSubscriptionMonths() == 6) {
            price = 3518;
        }
        personalTraining.setPrice(price);
    }
}
