package com.forgacea.WebApp.Cotrollers.ApiControllers;

import com.forgacea.WebApp.Models.Movie;
import com.forgacea.WebApp.Models.Rating;
import com.forgacea.WebApp.Models.User;
import com.forgacea.WebApp.Services.Interfaces.MovieService;
import com.forgacea.WebApp.Services.Interfaces.RatingService;
import com.forgacea.WebApp.Services.Interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/rate")
public class RatingController {
	private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

	RatingService service;
	@Autowired
	MovieService movieService;
	@Autowired
	UserService userService;

	public RatingController(RatingService service) {
		this.service = service;
	}

	@PutMapping({"/{movieId}"})
	ResponseEntity<Rating> rate(@PathVariable Integer movieId, @RequestBody Rating rate, Principal principal) {
		String username = principal.getName();
		logger.debug(format("rate called with rate = %s, movieId = %d by %s", rate, movieId, username));
		Rating rating = new Rating();
		rating.setRating(rate.getRating());

		Optional<Movie> movie = movieService.findById(movieId);
		if (movie.isEmpty())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		rating.setMovie(movie.get());

		Optional<User> user = userService.findById(username);
		if (user.isEmpty())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		rating.setUser(user.get());

		service.insert(rating);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
