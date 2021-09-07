package com.forgacea.WebApp.Services.Interfaces;

import com.forgacea.WebApp.Models.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

	List<Movie> getPage(int pageSize, int pageNumber, String sortOrder);

	Optional<Movie> findById(Integer id);

	Movie insert(Movie movie);

	void update(Integer id, Movie movie);

	void delete(Integer id);
}
