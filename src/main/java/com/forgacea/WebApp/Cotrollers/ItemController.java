package com.forgacea.WebApp.Cotrollers;

import com.forgacea.WebApp.Services.ItemService;
import com.forgacea.WebApp.Models.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
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


	@GetMapping
	List<Item> get(@RequestParam(name = "page_size", defaultValue = "20") int pageSize,
				   @RequestParam(name = "page_number", defaultValue = "0")  int pageNumber,
				   @RequestParam(name = "sort_by", defaultValue = "") String sortBy){
		logger.info(format("get called with pageSize = %d, pageNumber = %d, and sortBy = %s", pageSize, pageNumber, sortBy));
		return service.getItemSortedPage(pageSize, pageNumber, sortBy);
	}


	@GetMapping({"/{id}"})
	ResponseEntity<Item> getById(@PathVariable Integer id) {
		logger.info(format("update called for item with id = %d", id));
		Optional<Item> returnedItem = service.findItem(id);
		if (returnedItem.isEmpty()) {
			logger.debug(format("Item ('%d') does not exist", id));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(returnedItem.get(), HttpStatus.OK);
	}

	@PostMapping
	ResponseEntity<Item> insert(@RequestBody Item item){
		logger.info(format("insert called with %s", item));
		Item returnedItem = service.insertItem(item);
		return new ResponseEntity<>(returnedItem, HttpStatus.OK);
	}

	@PutMapping({"/{id}"})
	ResponseEntity<Item> update(@PathVariable Integer id, @RequestBody Item item) {
		logger.info(format("update called for item with id = %d and %s", id, item));
		if (service.findItem(id).isEmpty()) {
			logger.info("Item to be updated doesn't exist");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		service.updateItem(id, item);
		Optional<Item> updatedItem = service.findItem(id);
		if (updatedItem.isEmpty()) {
			logger.error(format("Item (id = %d) missing after service.update", id));
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug(format("update successful for %s", item));
		return new ResponseEntity<>(updatedItem.get(), HttpStatus.OK);
	}

	@DeleteMapping({"/{id}"})
	ResponseEntity<Item> delete(@PathVariable("id") Integer id) {
		logger.info(format("delete called on item with id = %d", id));
		service.deleteItem(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
