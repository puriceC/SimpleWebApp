package com.forgacea.WebApp.Cotrollers.ApiControllers;

import com.forgacea.WebApp.Services.Interfaces.ItemService;
import com.forgacea.WebApp.Models.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/items")
public class ItemController {
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	ItemService service;

	public ItemController(ItemService service) {
		this.service = service;
	}

	@PostMapping
	ResponseEntity<Item> insert(@RequestBody Item item){
		logger.info(format("insert called with %s", item));
		Item returned = service.insert(item);
		return new ResponseEntity<>(returned, HttpStatus.OK);
	}

	@PutMapping({"/{id}"})
	ResponseEntity<Item> update(@PathVariable Integer id, @RequestBody Item item) {
		logger.info(format("update called with id = %d and %s", id, item));
		if (service.findById(id).isEmpty()) {
			logger.info("Item to be updated doesn't exist");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		service.update(id, item);
		Optional<Item> updated = service.findById(id);
		if (updated.isEmpty()) {
			logger.error(format("Item (id = %d) missing after service.update", id));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(format("update successful for %s", item));
		return new ResponseEntity<>(updated.get(), HttpStatus.OK);
	}

	@DeleteMapping({"/{id}"})
	ResponseEntity<Item> delete(@PathVariable("id") Integer id) {
		logger.info(format("delete called with id = %d", id));
		service.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
