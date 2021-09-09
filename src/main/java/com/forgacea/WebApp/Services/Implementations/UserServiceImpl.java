package com.forgacea.WebApp.Services.Implementations;

import com.forgacea.WebApp.Models.User;
import com.forgacea.WebApp.Repositories.UserRepository;
import com.forgacea.WebApp.Services.Interfaces.UserService;
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
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	UserRepository repository;

	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<User> getPage(int pageSize, int pageNumber, String sortBy) {
		logger.debug(format("getPage called with pageSize = %d, pageNumber = %d and sortBy = '%s'", pageSize, pageNumber, sortBy));
		Sort sort = sortBy.isEmpty() ? Sort.unsorted() : Sort.by(sortBy);

		if (Arrays.stream(User.class.getDeclaredFields()).noneMatch(field -> field.getName().equals(sortBy))) {
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
		Page<User> userPage = repository.findAll(pageable);
		return userPage.getContent();
	}

	@Override
	public Optional<User> findById(String userName) {
		logger.debug(format("findById called with userName = %s", userName));
		return repository.findById(userName);
	}

	@Override
	public User insert(User user) {
		logger.debug(format("insert called with %s", user));
		return repository.save(user);
	}

	@Override
	public void update(String userName, User user) {
		logger.debug(format("update called with userName = %s and %s", userName, user));
		user.setUsername(userName);
		repository.save(user);
	}

	@Override
	public void delete(String userName) {
		logger.debug(format("delete called with userName = %s", userName));
		User toBeDeleted = repository.getById(userName);
		repository.delete(toBeDeleted);
	}
}
