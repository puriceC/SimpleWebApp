package com.forgacea.WebApp.Cotrollers.ApiControllers;

import com.forgacea.WebApp.Models.Movie;
import com.forgacea.WebApp.Services.Interfaces.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

	MovieService service;

	public MovieController(MovieService service) {
		this.service = service;
	}

	@PostMapping
	ResponseEntity<Movie> insert(@RequestBody Movie movie){
		logger.info(format("insert called with %s", movie));
		Movie returned = service.insert(movie);
		return new ResponseEntity<>(returned, HttpStatus.OK);
	}

	@PutMapping({"/{id}"})
	ResponseEntity<Movie> update(@PathVariable Integer id, @RequestBody Movie movie) {
		logger.info(format("update called with id = %d and %s", id, movie));
		if (service.findById(id).isEmpty()) {
			logger.info("Movie to be updated doesn't exist");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		service.update(id, movie);
		Optional<Movie> updated = service.findById(id);
		if (updated.isEmpty()) {
			logger.error(format("Movie (id = %d) missing after service.update", id));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(format("update successful for %s", movie));
		return new ResponseEntity<>(updated.get(), HttpStatus.OK);
	}

	@DeleteMapping({"/{id}"})
	ResponseEntity<Movie> delete(@PathVariable("id") Integer id) {
		logger.info(format("delete called with id = %d", id));
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
