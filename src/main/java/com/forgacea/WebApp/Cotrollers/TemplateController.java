package com.forgacea.WebApp.Cotrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forgacea.WebApp.Models.Item;
import com.forgacea.WebApp.Services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
@RequestMapping("/")
public class TemplateController {
	private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

	@Autowired private ItemService itemService;
	@Autowired private ObjectMapper objectMapper;

	@GetMapping("login")
	public String getLogin() {
		logger.info("login page accessed");
		return "login-page";
	}
	@GetMapping({"", "index"})
	public String getIndex() {
		logger.info("index accessed");
		return "index-page";
	}

	@GetMapping("items")
	public String viewAllItemsPage(Model model) {
		// get a list of items
		List<Item> items = itemService.getItemSortedPage(0, 0, null);
		// turn each item from java Object into a map of its properties
		Object[] rows = items.stream().map(item -> objectMapper.convertValue(item, Map.class)).toArray();
		// get the names of all fields of class Item
		Object[] headers = Arrays.stream(Item.class.getDeclaredFields()).map(Field::getName).toArray();
		model.addAttribute("view_object", "items");
		model.addAttribute("headers", headers);
		model.addAttribute("rows", rows);
		return "all-items-page";
	}

	@GetMapping("items/{id}")
	public String viewItemDetailsPage(@PathVariable("id") int id, Model model) {
		Optional<Item> item = itemService.findItem(id);

		model.addAttribute("view_object", "items");
		model.addAttribute("fields", Item.class.getDeclaredFields());
		if (item.isPresent()) {
			model.addAttribute("details", objectMapper.convertValue(item.get(), Map.class));
		} else {
			model.addAttribute("details", null);
		}
		return "item-details-page";
	}

	@GetMapping("items/new")
	public String viewNewItemPage(Model model) {
		model.addAttribute("view_object", "items");
		model.addAttribute("fields", Item.class.getDeclaredFields());
		return "new-item-page";
	}
}
