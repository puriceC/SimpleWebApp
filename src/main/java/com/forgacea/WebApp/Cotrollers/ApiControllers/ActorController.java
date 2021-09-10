package com.forgacea.WebApp.Cotrollers.ApiControllers;

import com.forgacea.WebApp.Models.Actor;
import com.forgacea.WebApp.Models.Genre;
import com.forgacea.WebApp.Repositories.ActorRepository;
import com.forgacea.WebApp.Repositories.GenreRepository;
import com.forgacea.WebApp.Services.Interfaces.ActorService;
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
@RequestMapping("/api/actors")
public class ActorController {
	private static final Logger logger = LoggerFactory.getLogger(ActorController.class);

	ActorService service;

	public ActorController(ActorService service) {
		this.service = service;
	}

	@PostMapping
	ResponseEntity<Actor> insert(@RequestBody Map<String, String> properties) {
		Actor actor = new Actor();
		actor.setName(properties.getOrDefault("name", "Unknown"));
		Actor returned = service.insert(actor);
		return new ResponseEntity<>(returned, HttpStatus.OK);
	}

	@PutMapping({"/{id}"})
	ResponseEntity<Actor> update(@PathVariable Integer id, @RequestBody Map<String, String> properties) {

		Optional<Actor> optionalActor = service.findById(id);
		if (optionalActor.isEmpty()) {
			logger.info("Actor to be updated doesn't exist");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Actor actor = optionalActor.get();
		actor.setName(properties.getOrDefault("name", "Unknown"));
		logger.info(format("update called with id = %d and %s", id, actor));
		service.update(id, actor);
		Optional<Actor> updated = service.findById(id);
		if (updated.isEmpty()) {
			logger.error(format("Actor (id = %d) missing after service.update", id));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(format("update successful for %s", actor));
		return new ResponseEntity<>(updated.get(), HttpStatus.OK);
	}

	@DeleteMapping({"/{id}"})
	ResponseEntity<Actor> delete(@PathVariable("id") Integer id) {
		logger.info(format("delete called with id = %d", id));
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
