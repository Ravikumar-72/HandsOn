package com.project.MovieBookingApp.Service;

import com.project.MovieBookingApp.DTO.BookingDTO;
import com.project.MovieBookingApp.Entity.Booking;
import com.project.MovieBookingApp.Entity.BookingStatus;
import com.project.MovieBookingApp.Entity.Show;
import com.project.MovieBookingApp.Entity.User;
import com.project.MovieBookingApp.Repository.BookingRepo;
import com.project.MovieBookingApp.Repository.ShowRepo;
import com.project.MovieBookingApp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ShowRepo showRepo;

    @Autowired
    private UserRepo userRepo;

    public Booking createBooking(BookingDTO bookingDTO) {
        Show show = showRepo.findById(bookingDTO.getShowId())
                .orElseThrow(()-> new RuntimeException("Show not found!"));
        if(!isSeatsAvailable(show.getId(),bookingDTO.getNumberOfSeats())){
            throw new RuntimeException("Oops! Not enough seats are available.");
        }

        if(bookingDTO.getSeatNumbers().size() != bookingDTO.getNumberOfSeats()){
            throw new RuntimeException("Seat numbers and number of seats must be equal");
        }

        validateDuplicateSeats(show.getId(), bookingDTO.getSeatNumbers());

        User user = userRepo.findById(bookingDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found!"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setSeatNumbers(bookingDTO.getSeatNumbers());
        booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
        booking.setPrice(calculateTotalAmount(show.getPrice(), bookingDTO.getNumberOfSeats()));
        booking.setBookingTime(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.PENDING);

        return bookingRepo.save(booking);
    }

    public List<Booking> getUserBookings(Long id) {
        return bookingRepo.findByUserId(id);
    }

    public List<Booking> getShowBookings(Long id) {
        return bookingRepo.findByShowId(id);
    }

    public Booking confirmBooking(Long id) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Booking not found!"));

        if(booking.getBookingStatus() != BookingStatus.PENDING){
            throw new RuntimeException("Booking is not in PENDING state");
        }

        //Payment process
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        return bookingRepo.save(booking);
    }

    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Booking not found!"));

        validateCancellation(booking);

        booking.setBookingStatus(BookingStatus.CANCELLED);
        return bookingRepo.save(booking);
    }

    public List<Booking> getBookingByStatus(BookingStatus bookingStatus) {
        return bookingRepo.findByBookingStatus(bookingStatus);
    }

    public boolean isSeatsAvailable(Long showId, Integer numberOfSeats){
        Show show = showRepo.findById(showId)
                .orElseThrow(()-> new RuntimeException("Show not found!"));

        int bookedSeats = show.getBookings().stream()
                .filter(booking -> booking.getBookingStatus() != BookingStatus.CANCELLED)
                .mapToInt(Booking::getNumberOfSeats)
                .sum();

        return (show.getTheatre().getCapacity() - bookedSeats) >= numberOfSeats;
    }

    public void validateDuplicateSeats(Long showId, List<String> seatNumbers){
        Show show  = showRepo.findById(showId)
                .orElseThrow(()-> new RuntimeException("Show not found!"));

        Set<String> occupiedSeats = show.getBookings().stream()
                .filter(b-> b.getBookingStatus() != BookingStatus.CANCELLED)
                .flatMap(b -> b.getSeatNumbers().stream())
                .collect(Collectors.toSet());

        List<String> duplicateSeats = seatNumbers.stream()
                .filter(occupiedSeats::contains)
                .collect(Collectors.toList());

        if(!duplicateSeats.isEmpty()){
            throw new RuntimeException("Seats are already booked!");
        }
    }

    public double calculateTotalAmount(Double price, Integer numberOfSeats){
        return price * numberOfSeats;
    }

    private void validateCancellation(Booking booking) {
        LocalDateTime showTime = booking.getShow().getShowTime();
        LocalDateTime deadlineTime = showTime.minusHours(3);

        if(LocalDateTime.now().isAfter(deadlineTime)){
            throw new RuntimeException("Cannot cancel the booking");
        }

        if(booking.getBookingStatus() == BookingStatus.CANCELLED){
            throw new RuntimeException("Booking already cancelled..");
        }
    }
}
