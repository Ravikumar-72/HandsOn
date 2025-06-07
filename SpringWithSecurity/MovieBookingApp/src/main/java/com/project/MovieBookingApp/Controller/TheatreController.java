package com.project.MovieBookingApp.Controller;

import com.project.MovieBookingApp.DTO.TheatreDTO;
import com.project.MovieBookingApp.Entity.Theatre;
import com.project.MovieBookingApp.Service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatre")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @PostMapping("/addtheatre")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theatre> addTheatre(@RequestBody TheatreDTO theatreDTO){
        return ResponseEntity.ok(theatreService.addTheatre(theatreDTO));
    }

    @PutMapping("/updatetheatre/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theatre> updateTheatre(@PathVariable Long id, @RequestBody TheatreDTO theatreDTO){
        return ResponseEntity.ok(theatreService.updateTheatre(id, theatreDTO));
    }

    @DeleteMapping("/deleteTheatre/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTheatre(@PathVariable Long id){
        theatreService.deleteTheatre(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filterlocation")
    public ResponseEntity<List<Theatre>> getALlTheatreBYLocation(@RequestParam String location){
        return ResponseEntity.ok(theatreService.getTheatreByLocation(location));
    }
}
