package com.forgacea.WebApp.Services.Interfaces;

import com.forgacea.WebApp.Models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

	List<Genre> getPage(int pageSize, int pageNumber, String sortOrder);

	Optional<Genre> findById(Integer id);

	Genre insert(Genre genre);

	void update(Integer id, Genre genre);

	void delete(Integer id);
}
