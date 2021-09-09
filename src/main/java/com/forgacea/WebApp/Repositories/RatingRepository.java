package com.forgacea.WebApp.Repositories;

import com.forgacea.WebApp.Models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
}
