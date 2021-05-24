package com.forgacea.WebApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemController {
	@GetMapping("items")
	List<Item> getItems(){
		return new ArrayList<>();
	}

}
