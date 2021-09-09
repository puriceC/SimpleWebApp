package com.forgacea.WebApp.Services.Implementations;

import com.forgacea.WebApp.Models.Rating;
import com.forgacea.WebApp.Repositories.RatingRepository;
import com.forgacea.WebApp.Services.Interfaces.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class RatingServiceImpl implements RatingService {
	private static final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

	RatingRepository repository;

	public RatingServiceImpl(RatingRepository repository) {
		this.repository = repository;
	}

	@Override
	public void insert(Rating rating) {
		logger.debug(format("insert called with %s", rating));
		repository.save(rating);
	}
}
