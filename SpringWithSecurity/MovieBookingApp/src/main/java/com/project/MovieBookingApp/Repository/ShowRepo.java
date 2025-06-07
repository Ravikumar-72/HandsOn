package com.project.MovieBookingApp.Repository;

import com.project.MovieBookingApp.Entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepo extends JpaRepository<Show, Long> {
    public Optional<List<Show>> findShowByMovie_Id(Long movieId);

    public Optional<List<Show>> findShowByTheatre_Id(Long theatreId);
}
