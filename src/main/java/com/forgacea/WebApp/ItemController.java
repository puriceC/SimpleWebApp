package com.forgacea.WebApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	List<Item> getItems(){
		logger.info("get Items called");
		return (List<Item>)service.getItems();
	}

}
