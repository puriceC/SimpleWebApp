package com.forgacea.WebApp.Repositories;

import com.forgacea.WebApp.Models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
