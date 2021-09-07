package com.forgacea.WebApp.Repositories;

import com.forgacea.WebApp.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
