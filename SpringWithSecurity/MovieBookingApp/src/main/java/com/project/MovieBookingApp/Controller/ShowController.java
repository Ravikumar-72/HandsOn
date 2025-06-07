package com.project.MovieBookingApp.Controller;

import com.project.MovieBookingApp.DTO.ShowDTO;
import com.project.MovieBookingApp.Entity.Show;
import com.project.MovieBookingApp.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/addshow")
    public ResponseEntity<Show> addShow(@RequestBody ShowDTO showDTO){
        return ResponseEntity.ok(showService.addShow(showDTO));
    }

    @PutMapping("/updatesshow/{id}")
    public ResponseEntity<Show> updateShow(@PathVariable Long id, @RequestBody ShowDTO showDTO){
        return ResponseEntity.ok(showService.updateShow(id,showDTO));
    }

    @DeleteMapping("deleteshow/{id}")
    public ResponseEntity<Show> deleteShow(@PathVariable Long id){
        showService.deleteShow(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/allshow")
    public ResponseEntity<List<Show>> getAllShows(){
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/showbymovie")
    public ResponseEntity<List<Show>> getShowsByMovie(@PathVariable Long movieId){
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @GetMapping("/showbytheatre")
    public ResponseEntity<List<Show>> getShowsByTheatre(@PathVariable Long theatreId){
        return ResponseEntity.ok(showService.getShowsByTheatre(theatreId));
    }


}
