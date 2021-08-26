package com.forgacea.WebApp.Cotrollers.PageControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forgacea.WebApp.Models.Item;
import com.forgacea.WebApp.Services.Interfaces.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/items")
public class ItemPageController {
	private static final Logger logger = LoggerFactory.getLogger(ItemPageController.class);

	@Autowired
	private ItemService service;
	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping
	public String viewAllItemsPage(@RequestParam(name = "page_size", defaultValue = "20") int pageSize,
								   @RequestParam(name = "page_number", defaultValue = "0")  int pageNumber,
								   @RequestParam(name = "sort_by", defaultValue = "") String sortBy,
								   Model model) {
		// get a list of items
		List<Item> items = service.getPage(pageSize, pageNumber, sortBy);
		// turn each item from java Object into a map of its properties
		Object[] rows = items.stream().map(item -> objectMapper.convertValue(item, Map.class)).toArray();
		model.addAttribute("entity", "items");
		model.addAttribute("fields", Item.class.getDeclaredFields());
		model.addAttribute("rows", rows);
		model.addAttribute("page_number", pageNumber);
		model.addAttribute("page_size", pageSize);
		logger.info("all page accessed");
		return "all-page";
	}

	@GetMapping("/{id}")
	public String viewDetailsPage(@PathVariable("id") int id, Model model) {
		Optional<Item> item = service.findById(id);

		model.addAttribute("entity", "items");
		model.addAttribute("fields", Item.class.getDeclaredFields());
		if (item.isPresent()) {
			model.addAttribute("details", objectMapper.convertValue(item.get(), Map.class));
		} else {
			model.addAttribute("details", null);
		}
		logger.info("details page accessed");
		return "details-page";
	}

	@GetMapping("/new")
	public String viewNewPage(Model model) {
		model.addAttribute("entity", "items");
		model.addAttribute("fields", Item.class.getDeclaredFields());
		logger.info("new page accessed");
		return "new-page";
	}
}
