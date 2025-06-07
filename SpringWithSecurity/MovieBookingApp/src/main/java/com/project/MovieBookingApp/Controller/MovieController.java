package com.project.MovieBookingApp.Controller;

import com.project.MovieBookingApp.DTO.MovieDTO;
import com.project.MovieBookingApp.Entity.Movie;
import com.project.MovieBookingApp.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // ADMIN API's
    @PostMapping("/addmovie")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> addMovie(@RequestBody MovieDTO movieDTO){
        return ResponseEntity.ok(movieService.addMovie(movieDTO));
    }

    @PutMapping("/updatemovie/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO){
        return ResponseEntity.ok(movieService.updateMovie(id,movieDTO));
    }

    @DeleteMapping("/deletemovie/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }

    //Public Endpoints
    @GetMapping("/allmovies")
    public ResponseEntity<List<Movie>> getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/filtergenre")
    public ResponseEntity<List<Movie>> getMovieByGenre(@RequestParam String genre){
        return ResponseEntity.ok(movieService.getAllMoviesByGenre(genre));
    }

    @GetMapping("/filtertitle")
    public ResponseEntity<List<Movie>> getMovieByTitle(@RequestParam String title){
        return ResponseEntity.ok(movieService.getAllMoviesByTitle(title));
    }

    @GetMapping("/filterlanguage")
    public ResponseEntity<List<Movie>> getMovieByLanguage(@RequestParam String language){
        return ResponseEntity.ok(movieService.getAllMoviesByLanguage(language));
    }






}
