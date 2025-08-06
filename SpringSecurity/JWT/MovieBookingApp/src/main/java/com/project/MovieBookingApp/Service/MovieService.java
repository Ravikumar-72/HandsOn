package com.project.MovieBookingApp.Service;

import com.project.MovieBookingApp.DTO.MovieDTO;
import com.project.MovieBookingApp.Entity.Movie;
import com.project.MovieBookingApp.Repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepo movieRepo;

    public Movie addMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setLanguage(movieDTO.getLanguage());
        movie.setDuration(movieDTO.getDuration());
        movie.setReleaseDate(movieDTO.getReleaseDate());

        return movieRepo.save(movie);
    }

    public Movie updateMovie(Long id,MovieDTO movieDTO) {
        Movie movie = movieRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No movie found for the id "+id));

        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(movieDTO.getGenre());
        movie.setLanguage(movieDTO.getLanguage());
        movie.setDuration(movieDTO.getDuration());
        movie.setReleaseDate(movieDTO.getReleaseDate());

        return movieRepo.save(movie);
    }

    public void deleteMovie(Long id){
        movieRepo.deleteById(id);
    }

    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    public List<Movie> getAllMoviesByGenre(String genre) {
        Optional<List<Movie>> movieGenreList = movieRepo.findByGenre(genre);
        if(movieGenreList.isPresent()){
            return movieGenreList.get();
        }
        else throw new RuntimeException("No movies found for the genre "+ genre);
    }

    public List<Movie> getAllMoviesByTitle(String title) {
        Optional<List<Movie>> movieTitleList = movieRepo.findByName(title);
        if(movieTitleList.isPresent()){
            return movieTitleList.get();
        }
        else throw new RuntimeException("No movies found for the title "+ title);
    }

    public List<Movie> getAllMoviesByLanguage(String language) {
        Optional<List<Movie>> movieLangList = movieRepo.findByLanguage(language);
        if(movieLangList.isPresent()){
            return movieLangList.get();
        }
        else throw new RuntimeException("No movies found for the language "+ language);
    }
}
