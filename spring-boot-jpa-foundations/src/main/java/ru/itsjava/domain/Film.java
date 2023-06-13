package ru.itsjava.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne(targetEntity = Genre.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id") // название колонки в этой сущности (Film)
    private Genre genre;

    @OneToMany(targetEntity = Place.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "film_id") // название колонки в сущности Place
    private List<Place> places;
}
