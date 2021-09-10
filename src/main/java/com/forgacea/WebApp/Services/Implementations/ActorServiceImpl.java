package com.forgacea.WebApp.Services.Implementations;

import com.forgacea.WebApp.Models.Actor;
import com.forgacea.WebApp.Repositories.ActorRepository;
import com.forgacea.WebApp.Services.Interfaces.ActorService;
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
public class ActorServiceImpl implements ActorService {
	private static final Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);

	ActorRepository repository;

	public ActorServiceImpl(ActorRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Actor> getPage(int pageSize, int pageNumber, String sortBy) {
		logger.debug(format("getPage called with pageSize = %d, pageNumber = %d and sortBy = '%s'", pageSize, pageNumber, sortBy));
		Sort sort = sortBy.isEmpty() ? Sort.unsorted() : Sort.by(sortBy);

		if (Arrays.stream(Actor.class.getDeclaredFields()).noneMatch(field -> field.getName().equals(sortBy))) {
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
		Page<Actor> actorPage = repository.findAll(pageable);
		return actorPage.getContent();
	}

	@Override
	public Optional<Actor> findById(Integer id) {
		logger.debug(format("findById called with id = %d", id));
		return repository.findById(id);
	}

	@Override
	public Actor insert(Actor actor) {
		logger.debug(format("insert called with %s", actor));
		return repository.save(actor);
	}

	@Override
	public void update(Integer id, Actor actor) {
		logger.debug(format("update called with id = %d and %s", id, actor));
		actor.setId(id);
		repository.save(actor);
	}

	@Override
	public void delete(Integer id) {
		logger.debug(format("delete called with id = %d", id));
		Actor toBeDeleted = repository.getById(id);
		repository.delete(toBeDeleted);
	}
}
