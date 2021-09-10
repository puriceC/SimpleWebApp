package com.forgacea.WebApp.Services.Interfaces;

import com.forgacea.WebApp.Models.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorService {

	List<Director> getPage(int pageSize, int pageNumber, String sortOrder);

	Optional<Director> findById(Integer id);

	Director insert(Director director);

	void update(Integer id, Director director);

	void delete(Integer id);
}
