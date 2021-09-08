package com.forgacea.WebApp.Cotrollers.ApiControllers;

import com.forgacea.WebApp.Models.Director;
import com.forgacea.WebApp.Models.Genre;
import com.forgacea.WebApp.Models.Movie;
import com.forgacea.WebApp.Repositories.DirectorRepository;
import com.forgacea.WebApp.Repositories.GenreRepository;
import com.forgacea.WebApp.Services.Interfaces.MovieService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
	private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

	MovieService service;
	@Autowired
	DirectorRepository directorRepository;
	@Autowired
	GenreRepository genreRepository;

	public MovieController(MovieService service) {
		this.service = service;
	}

	@NotNull
	private Movie setMovieProperties(Movie movie, Map<String, String> properties) {
		String title = properties.get("title");
		String genre = properties.get("genre");
		String director = properties.get("director");
		logger.info(format("request made with %s,%s,%s", title, genre, director));
		movie.setTitle(title);

		ExampleMatcher matcher = ExampleMatcher.matchingAny().withIgnorePaths("id");

		Genre genreEg = new Genre();
		genreEg.setGenre(genre);
		Optional<Genre> optionalGenre = genreRepository.findOne(Example.of(genreEg, matcher));
		optionalGenre.ifPresent(movie::setGenre);

		Director directorEg = new Director();
		directorEg.setName(director);
		Optional<Director> optionalDirector = directorRepository.findOne(Example.of(directorEg, matcher));
		optionalDirector.ifPresent(movie::setDirector);

		return movie;
	}

	@PostMapping
	ResponseEntity<Movie> insert(@RequestBody Map<String, String> properties) {
		Movie movie = setMovieProperties(new Movie(), properties);
		Movie returned = service.insert(movie);
		return new ResponseEntity<>(returned, HttpStatus.OK);
	}

	@PutMapping({"/{id}"})
	ResponseEntity<Movie> update(@PathVariable Integer id, @RequestBody Map<String, String> properties) {

		Optional<Movie> optionalMovie = service.findById(id);
		if (optionalMovie.isEmpty()) {
			logger.info("Movie to be updated doesn't exist");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Movie movie = setMovieProperties(optionalMovie.get(), properties);
		logger.info(format("update called with id = %d and %s", id, movie));
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
