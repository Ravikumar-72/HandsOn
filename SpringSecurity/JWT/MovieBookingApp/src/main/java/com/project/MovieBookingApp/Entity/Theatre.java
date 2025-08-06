package com.project.MovieBookingApp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private String screenType;

    @OneToMany(mappedBy = "theatre", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Show> show;
}
