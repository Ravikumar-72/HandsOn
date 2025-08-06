package com.project.MovieBookingApp.Repository;

import com.project.MovieBookingApp.Entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheatreRepo extends JpaRepository<Theatre, Long> {

    public Optional<List<Theatre>> findByLocation(String location);
}
