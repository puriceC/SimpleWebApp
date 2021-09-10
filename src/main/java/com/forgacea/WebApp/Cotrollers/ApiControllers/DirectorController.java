package com.forgacea.WebApp.Cotrollers.ApiControllers;

import com.forgacea.WebApp.Models.Director;
import com.forgacea.WebApp.Models.Genre;
import com.forgacea.WebApp.Repositories.DirectorRepository;
import com.forgacea.WebApp.Repositories.GenreRepository;
import com.forgacea.WebApp.Services.Interfaces.DirectorService;
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
@RequestMapping("/api/directors")
public class DirectorController {
	private static final Logger logger = LoggerFactory.getLogger(DirectorController.class);

	DirectorService service;

	public DirectorController(DirectorService service) {
		this.service = service;
	}

	@PostMapping
	ResponseEntity<Director> insert(@RequestBody Map<String, String> properties) {
		Director director = new Director();
		director.setName(properties.getOrDefault("name", "Unknown"));
		Director returned = service.insert(director);
		return new ResponseEntity<>(returned, HttpStatus.OK);
	}

	@PutMapping({"/{id}"})
	ResponseEntity<Director> update(@PathVariable Integer id, @RequestBody Map<String, String> properties) {

		Optional<Director> optionalDirector = service.findById(id);
		if (optionalDirector.isEmpty()) {
			logger.info("Director to be updated doesn't exist");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Director director = optionalDirector.get();
		director.setName(properties.getOrDefault("name", "Unknown"));
		logger.info(format("update called with id = %d and %s", id, director));
		service.update(id, director);
		Optional<Director> updated = service.findById(id);
		if (updated.isEmpty()) {
			logger.error(format("Director (id = %d) missing after service.update", id));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(format("update successful for %s", director));
		return new ResponseEntity<>(updated.get(), HttpStatus.OK);
	}

	@DeleteMapping({"/{id}"})
	ResponseEntity<Director> delete(@PathVariable("id") Integer id) {
		logger.info(format("delete called with id = %d", id));
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
