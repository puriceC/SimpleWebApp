package com.forgacea.WebApp.Cotrollers.PageControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forgacea.WebApp.Models.Director;
import com.forgacea.WebApp.Services.Interfaces.DirectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/directors")
public class DirectorPageController {
	private static final Logger logger = LoggerFactory.getLogger(DirectorPageController.class);

	@Autowired
	private DirectorService service;
	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping
	public String viewAllDirectorsPage(@RequestParam(name = "page_size", defaultValue = "20") int pageSize,
									@RequestParam(name = "page_number", defaultValue = "0")  int pageNumber,
									@RequestParam(name = "sort_by", defaultValue = "") String sortBy,
									Model model,
									SecurityContextHolderAwareRequestWrapper requestWrapper) {
		// get a list of directors
		List<Director> directors = service.getPage(pageSize, pageNumber, sortBy);

		if (requestWrapper.isUserInRole("ADMIN")){
			model.addAttribute("role", "ADMIN");
		}

		model.addAttribute("directors", directors);
		model.addAttribute("page_number", pageNumber);
		model.addAttribute("page_size", pageSize);
		logger.info("all page accessed");
		return "Director/all-page";
	}

	@GetMapping("/{id}")
	public String viewDetailsPage(@PathVariable("id") int id, Model model) {
		Optional<Director> director = service.findById(id);

		model.addAttribute("director", director.orElse(null));

		logger.info("details page accessed");
		return "Director/details-page";
	}

	@GetMapping("/admin/{id}")
	public String viewEditPage(@PathVariable("id") int id, Model model) {
		Optional<Director> director = service.findById(id);

		model.addAttribute("director", director.orElse(null));

		logger.info("edit page accessed");
		return "Director/edit-page";
	}

	@GetMapping("/admin/new")
	public String viewNewPage() {
		logger.info("new page accessed");
		return "Director/new-page";
	}
}
