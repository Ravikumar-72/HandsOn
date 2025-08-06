package com.project.MovieBookingApp.Service;

import com.project.MovieBookingApp.DTO.ShowDTO;
import com.project.MovieBookingApp.Entity.Booking;
import com.project.MovieBookingApp.Entity.Movie;
import com.project.MovieBookingApp.Entity.Show;
import com.project.MovieBookingApp.Entity.Theatre;
import com.project.MovieBookingApp.Repository.MovieRepo;
import com.project.MovieBookingApp.Repository.ShowRepo;
import com.project.MovieBookingApp.Repository.TheatreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepo showRepo;

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private TheatreRepo theatreRepo;

    public Show addShow(ShowDTO showDTO) {

        Movie movie = movieRepo.findById(showDTO.getMovieId())
                .orElseThrow(()-> new RuntimeException("No movie found! "+showDTO.getMovieId()));

        Theatre theatre = theatreRepo.findById(showDTO.getTheatreId())
                .orElseThrow(()-> new RuntimeException("No theatre found! "+showDTO.getTheatreId()));

        Show show = new Show();

        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheatre(theatre);

        return showRepo.save(show);
    }

    public Show updateShow(Long id, ShowDTO showDTO) {

        Show show = showRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("No show found for the id "+id));

        Movie movie = movieRepo.findById(showDTO.getMovieId())
                .orElseThrow(()-> new RuntimeException("No movie found! "+showDTO.getMovieId()));

        Theatre theatre = theatreRepo.findById(showDTO.getTheatreId())
                .orElseThrow(()-> new RuntimeException("No theatre found! "+showDTO.getTheatreId()));

        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheatre(theatre);

        return showRepo.save(show);
    }


    public List<Show> getAllShows() {
        return showRepo.findAll();
    }

    public List<Show> getShowsByMovie(Long movieId) {
        Optional<List<Show>> showByMovieList = showRepo.findShowByMovie_Id(movieId);
        if(showByMovieList.isPresent()){
            return showByMovieList.get();
        }
        else throw new RuntimeException("No shows available for the movie!");
    }

    public List<Show> getShowsByTheatre(Long theatreId) {
        Optional<List<Show>> showByTheatreList = showRepo.findShowByTheatre_Id(theatreId);
        if(showByTheatreList.isPresent()){
            return showByTheatreList.get();
        }
        else throw new RuntimeException("No shows available for the Theatre!");
    }

    public void deleteShow(Long id) {

        if(!showRepo.existsById(id)){
            throw new RuntimeException("No show available for the id "+id);
        }

        List<Booking> bookings = showRepo.findById(id).get().getBookings();
        if(bookings.isEmpty()){
            throw new RuntimeException("Can't delete show with existing bookings");
        }

        showRepo.deleteById(id);
    }
}
