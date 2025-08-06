package com.project.MovieBookingApp.Repository;

import com.project.MovieBookingApp.Entity.Booking;
import com.project.MovieBookingApp.Entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long id);

    List<Booking> findByShowId(Long id);

    List<Booking> findByBookingStatus(BookingStatus bookingStatus);
}
