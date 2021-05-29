package com.forgacea.WebApp.Cotrollers;

import com.forgacea.WebApp.Services.ItemService;
import com.forgacea.WebApp.Models.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
				   @RequestParam(name = "page_number", defaultValue = "0")  int pageNumber){
		logger.info("get called with pageSize = " + pageSize + " and pageNumber = " + pageNumber);
		return service.getItemPage(pageSize, pageNumber);
	}

	@GetMapping({"/all"})
	List<Item> getAll(){
		logger.info("getAll called");
		return service.getItems();
	}

	@GetMapping({"/{id}"})
	ResponseEntity<Item> getById(@PathVariable Integer id) {
		logger.info("update called for item with id = " + id);
		Optional<Item> returnedItem = service.findItem(id);
		if (returnedItem.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(returnedItem.get(), HttpStatus.OK);
	}

	@PostMapping
	ResponseEntity<Item> insert(@RequestBody Item item){
		logger.info("insert called with " + item);
		Item returnedItem = service.insertItem(item);
		return new ResponseEntity<>(returnedItem, HttpStatus.OK);
	}

	@PutMapping({"/{id}"})
	ResponseEntity<Item> update(@PathVariable Integer id, @RequestBody Item item) {
		logger.info("update called for item with id = " + id + " and " + item);
		if (service.findItem(id).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		service.updateItem(id, item);
		Optional<Item> updatedItem = service.findItem(id);
		if (updatedItem.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(updatedItem.get(), HttpStatus.OK);
	}

	@DeleteMapping({"/{id}"})
	ResponseEntity<Item> delete(@PathVariable("id") Integer id) {
		logger.info("delete called on item with id = " + id);
		service.deleteItem(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
