package com.forgacea.WebApp.Cotrollers.ApiControllers;

import com.forgacea.WebApp.Models.Genre;
import com.forgacea.WebApp.Models.Genre;
import com.forgacea.WebApp.Repositories.GenreRepository;
import com.forgacea.WebApp.Repositories.GenreRepository;
import com.forgacea.WebApp.Services.Interfaces.GenreService;
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
@RequestMapping("/api/genres")
public class GenreController {
	private static final Logger logger = LoggerFactory.getLogger(GenreController.class);

	GenreService service;

	public GenreController(GenreService service) {
		this.service = service;
	}

	@PostMapping
	ResponseEntity<Genre> insert(@RequestBody Map<String, String> properties) {
		Genre genre = new Genre();
		genre.setGenre(properties.getOrDefault("genre", "Other"));
		Genre returned = service.insert(genre);
		return new ResponseEntity<>(returned, HttpStatus.OK);
	}

	@PutMapping({"/{id}"})
	ResponseEntity<Genre> update(@PathVariable Integer id, @RequestBody Map<String, String> properties) {

		Optional<Genre> optionalGenre = service.findById(id);
		if (optionalGenre.isEmpty()) {
			logger.info("Genre to be updated doesn't exist");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Genre genre = optionalGenre.get();
		genre.setGenre(properties.getOrDefault("genre", "Other"));
		logger.info(format("update called with id = %d and %s", id, genre));
		service.update(id, genre);
		Optional<Genre> updated = service.findById(id);
		if (updated.isEmpty()) {
			logger.error(format("Genre (id = %d) missing after service.update", id));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(format("update successful for %s", genre));
		return new ResponseEntity<>(updated.get(), HttpStatus.OK);
	}

	@DeleteMapping({"/{id}"})
	ResponseEntity<Genre> delete(@PathVariable("id") Integer id) {
		logger.info(format("delete called with id = %d", id));
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
