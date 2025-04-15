package com.example.exercisecontrolleradvise.Controller;


import com.example.exercisecontrolleradvise.Api.ApiResponse;
import com.example.exercisecontrolleradvise.Model.Booking;
import com.example.exercisecontrolleradvise.Service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gym/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // Get all bookings
    @GetMapping("/get")
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    //(Endpoints #16)  Add new booking.
    //  check if user and gym class exist. If both exist and capacity > 0,  decrease capacity by 1 and save the booking.
    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@Valid @RequestBody Booking booking) {
        bookingService.addBooking(booking);
        return ResponseEntity.status(200).body(new ApiResponse("Booking added!"));
    }

    //(Endpoints #17)  Delete a booking.
    //  check if booking exists and  If booking exists,  increase gym class capacity by 1 and delete the booking.
    @DeleteMapping("/delete/{booking_id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Integer booking_id) {
        bookingService.deleteBooking(booking_id);
        return ResponseEntity.status(200).body(new ApiResponse("Booking deleted !"));
    }

    // (Endpoints #18) Show usernames in one gym class.
    @GetMapping("/class/{gymClassId}")
    public ResponseEntity<List<String>> getUsernamesInClass(@PathVariable Integer gymClassId) {
        List<String> usernames = bookingService.getUsernamesInGymClass(gymClassId);
        return ResponseEntity.ok(usernames);
    }

    // (Endpoints #19) Change user from old gym class to new gym class.
    @PutMapping("/change/{userId}/{oldGymClassId}/{newGymClassId}")
    public ResponseEntity<?> changeUserGymClass(@PathVariable Integer userId, @PathVariable Integer oldGymClassId, @PathVariable Integer newGymClassId) {
        bookingService.changeUserGymClass(userId, oldGymClassId, newGymClassId);
        return ResponseEntity.ok(new ApiResponse("Successfully changed gym class!"));
    }


}
