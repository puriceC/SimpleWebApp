package com.forgacea.WebApp.Services.Implementations;

import com.forgacea.WebApp.Models.Movie;
import com.forgacea.WebApp.Repositories.MovieRepository;
import com.forgacea.WebApp.Services.Interfaces.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class MovieServiceImpl implements MovieService {
	private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

	MovieRepository repository;

	public MovieServiceImpl(MovieRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Movie> getPage(int pageSize, int pageNumber, String sortBy) {
		logger.debug(format("getPage called with pageSize = %d, pageNumber = %d and sortBy = '%s'", pageSize, pageNumber, sortBy));
		Sort sort = sortBy.isEmpty() ? Sort.unsorted() : Sort.by(sortBy);

		if (Arrays.stream(Movie.class.getDeclaredFields()).noneMatch(field -> field.getName().equals(sortBy))) {
			logger.warn(format("Value of sortBy ('%s') was not found in class fields and will be ignored", sortBy));
			sort = Sort.unsorted();
		}
		if (pageSize < 1){
			logger.warn(format("Value of page size (%d) less then 1 and will be ignored", pageSize));
			pageSize = 20;
		}
		if (pageNumber < 0){
			logger.warn(format("Value of page number (%d) less then 0 and will be set to 0", pageNumber));
			pageNumber = 0;
		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Movie> moviePage = repository.findAll(pageable);
		return moviePage.getContent();
	}

	@Override
	public Optional<Movie> findById(Integer id) {
		logger.debug(format("findById called with id = %d", id));
		return repository.findById(id);
	}

	@Override
	public Movie insert(Movie movie) {
		logger.debug(format("insert called with %s", movie));
		return repository.save(movie);
	}

	@Override
	public void update(Integer id, Movie movie) {
		logger.debug(format("update called with id = %d and %s", id, movie));
		movie.setId(id);
		repository.save(movie);
	}

	@Override
	public void delete(Integer id) {
		logger.debug(format("delete called with id = %d", id));
		Movie toBeDeleted = repository.getById(id);
		repository.delete(toBeDeleted);
	}
}
