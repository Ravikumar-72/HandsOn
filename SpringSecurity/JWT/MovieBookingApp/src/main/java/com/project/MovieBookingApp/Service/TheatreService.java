package com.project.MovieBookingApp.Service;

import com.project.MovieBookingApp.DTO.TheatreDTO;
import com.project.MovieBookingApp.Entity.Theatre;
import com.project.MovieBookingApp.Repository.TheatreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepo theatreRepo;

    public Theatre addTheatre(TheatreDTO theatreDTO) {
        Theatre theatre = new Theatre();

        theatre.setName(theatreDTO.getName());
        theatre.setCapacity(theatreDTO.getCapacity());
        theatre.setLocation(theatreDTO.getLocation());
        theatre.setScreenType(theatreDTO.getScreenType());

        return theatreRepo.save(theatre);
    }

    public Theatre updateTheatre(Long id, TheatreDTO theatreDTO) {

        Theatre theatre = theatreRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("No theatre found"));

        theatre.setName(theatreDTO.getName());
        theatre.setCapacity(theatreDTO.getCapacity());
        theatre.setLocation(theatreDTO.getLocation());
        theatre.setScreenType(theatreDTO.getScreenType());

        return theatreRepo.save(theatre);
    }

    public void deleteTheatre(Long id) {

        theatreRepo.deleteById(id);
    }

    public List<Theatre> getTheatreByLocation(String location) {

        Optional<List<Theatre>> theatreLocationList = theatreRepo.findByLocation(location);
        if(theatreLocationList.isPresent()){
            return theatreLocationList.get();
        }
        else throw new RuntimeException("No theatre found for the location "+ location);
    }
}
