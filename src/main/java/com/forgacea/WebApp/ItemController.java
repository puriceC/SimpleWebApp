package com.forgacea.WebApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	ItemService service;

	public ItemController(ItemService service) {
		this.service = service;
	}

	@GetMapping
	List<Item> getAll(){
		logger.info("getAll called");
		return service.getItems();
	}

	@PostMapping
	ResponseEntity<Item> insert(@RequestBody Item item){
		logger.info("insert called with " + item);
		service.insertItem(item);
		Item returnedItem = service.getItemById(item.id);
		return new ResponseEntity<>(returnedItem, HttpStatus.OK);
	}

	@PutMapping({"/{id}"})
	ResponseEntity<Item> update(@PathVariable Integer id, @RequestBody Item item) {
		logger.info("update called for item with id = " + id + " and " + item);
		service.updateItem(id, item);
		Item returnedItem = service.getItemById(item.id);
		return new ResponseEntity<>(returnedItem, HttpStatus.OK);
	}

	@DeleteMapping({"/{id}"})
	ResponseEntity<Item> delete(@PathVariable("id") Integer id) {
		logger.info("delete called on item with id = " + id);
		service.deleteItem(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
