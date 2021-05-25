package com.forgacea.WebApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemController {
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
	@GetMapping("items")
	List<Item> getItems(){
		logger.info("get Items called");
		return new ArrayList<>();
	}

}
