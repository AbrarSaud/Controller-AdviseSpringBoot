package com.example.exercisecontrolleradvise.Service;


import com.example.exercisecontrolleradvise.Model.Booking;
import com.example.exercisecontrolleradvise.Model.GymClass;
import com.example.exercisecontrolleradvise.Model.User;
import com.example.exercisecontrolleradvise.Repository.BookingRepository;
import com.example.exercisecontrolleradvise.Repository.GymClassRepository;
import com.example.exercisecontrolleradvise.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final GymClassRepository gymClassRepository;

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    //(Endpoints #16)  Add new booking.
    //  check if user and gym class exist. If both exist and capacity > 0,  decrease capacity by 1 and save the booking.
    public Boolean addBooking(Booking booking) {
        User existingUser = userRepository.findUserByUserId(booking.getUserId());
        GymClass checkCapacity = gymClassRepository.findGymClassByGymClassId(booking.getGymClassId());
        if (existingUser == null) {
            return false;
        }
        if (checkCapacity == null || checkCapacity.getCapacity() <= 0) {
            return false;
        }
        checkCapacity.setCapacity(checkCapacity.getCapacity() - 1);
        gymClassRepository.save(checkCapacity);
        bookingRepository.save(booking);
        return true;

    }

    //(Endpoints #17)  Delete a booking.
    //  check if booking exists and  If booking exists,  increase gym class capacity by 1 and delete the booking.
    public Boolean deleteBooking(Integer bookingId) {
        Booking existingBooking = bookingRepository.getBookingsById(bookingId);
        if (existingBooking == null) {
            return false;
        }
        GymClass existingGymClass = gymClassRepository.findGymClassByGymClassId(existingBooking.getGymClassId());
        if (existingGymClass != null) {
            existingGymClass.setCapacity(existingGymClass.getCapacity() + 1);
            gymClassRepository.save(existingGymClass);
        }
        bookingRepository.delete(existingBooking);
        return true;
    }

    // (Endpoints #18) Show usernames in one gym class.
    public List<String> getUsernamesInGymClass(Integer gymClassId) {
        List<Integer> userIds = bookingRepository.findUserIdsByGymClassId(gymClassId);
        if (userIds == null) {
            return null;
        }
        return userRepository.findUsernamesByIds(userIds);
    }

    // (Endpoints #19) Change user from old gym class to new gym class.
    public Boolean changeUserGymClass(Integer userId, Integer oldGymClassId, Integer newGymClassId) {
        Booking existingBooking = bookingRepository.getBookingByUserIdAndGymClassId(userId, oldGymClassId);
        if (existingBooking == null) {
            return false;
        }
        GymClass oldGymClass = gymClassRepository.findGymClassByGymClassId(oldGymClassId);
        GymClass newGymClass = gymClassRepository.findGymClassByGymClassId(newGymClassId);

        if (newGymClass == null || newGymClass.getCapacity() > 20) {
            return false;
        }
        if (oldGymClass == null) {
            oldGymClass.setCapacity(oldGymClass.getCapacity() + 1);
            gymClassRepository.save(oldGymClass);
        }
        newGymClass.setCapacity(newGymClass.getCapacity() - 1);
        gymClassRepository.save(newGymClass);
        existingBooking.setGymClassId(newGymClassId);
        bookingRepository.save(existingBooking);
        return true;
    }


}
