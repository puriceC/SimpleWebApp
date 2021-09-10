package com.forgacea.WebApp.Services.Implementations;

import com.forgacea.WebApp.Models.Genre;
import com.forgacea.WebApp.Repositories.GenreRepository;
import com.forgacea.WebApp.Services.Interfaces.GenreService;
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
public class GenreServiceImpl implements GenreService {
	private static final Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);

	GenreRepository repository;

	public GenreServiceImpl(GenreRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Genre> getPage(int pageSize, int pageNumber, String sortBy) {
		logger.debug(format("getPage called with pageSize = %d, pageNumber = %d and sortBy = '%s'", pageSize, pageNumber, sortBy));
		Sort sort = sortBy.isEmpty() ? Sort.unsorted() : Sort.by(sortBy);

		if (Arrays.stream(Genre.class.getDeclaredFields()).noneMatch(field -> field.getName().equals(sortBy))) {
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
		Page<Genre> genrePage = repository.findAll(pageable);
		return genrePage.getContent();
	}

	@Override
	public Optional<Genre> findById(Integer id) {
		logger.debug(format("findById called with id = %d", id));
		return repository.findById(id);
	}

	@Override
	public Genre insert(Genre genre) {
		logger.debug(format("insert called with %s", genre));
		return repository.save(genre);
	}

	@Override
	public void update(Integer id, Genre genre) {
		logger.debug(format("update called with id = %d and %s", id, genre));
		genre.setId(id);
		repository.save(genre);
	}

	@Override
	public void delete(Integer id) {
		logger.debug(format("delete called with id = %d", id));
		Genre toBeDeleted = repository.getById(id);
		repository.delete(toBeDeleted);
	}
}
