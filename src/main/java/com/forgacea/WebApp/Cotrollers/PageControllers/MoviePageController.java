package com.forgacea.WebApp.Cotrollers.PageControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forgacea.WebApp.Models.Movie;
import com.forgacea.WebApp.Services.Interfaces.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/movies")
public class MoviePageController {
	private static final Logger logger = LoggerFactory.getLogger(MoviePageController.class);

	@Autowired
	private MovieService service;
	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping
	public String viewAllMoviesPage(@RequestParam(name = "page_size", defaultValue = "20") int pageSize,
								   @RequestParam(name = "page_number", defaultValue = "0")  int pageNumber,
								   @RequestParam(name = "sort_by", defaultValue = "") String sortBy,
								   Model model) {
		// get a list of movies
		List<Movie> movies = service.getPage(pageSize, pageNumber, sortBy);

		model.addAttribute("entity", "movies");
		model.addAttribute("fields", new String[]{"title", "genre", "rating"});
		model.addAttribute("movies", movies);
		model.addAttribute("page_number", pageNumber);
		model.addAttribute("page_size", pageSize);
		logger.info("all page accessed");
		return "Movie/all-page";
	}

	@GetMapping("/{id}")
	public String viewDetailsPage(@PathVariable("id") int id, Model model) {
		Optional<Movie> movie = service.findById(id);

		model.addAttribute("entity", "movies");
		model.addAttribute("fields", Movie.class.getDeclaredFields());
		if (movie.isPresent()) {
			model.addAttribute("details", objectMapper.convertValue(movie.get(), Map.class));
		} else {
			model.addAttribute("details", null);
		}
		logger.info("details page accessed");
		return "Movie/details-page";
	}

	@GetMapping("/new")
	public String viewNewPage(Model model) {
		model.addAttribute("entity", "movies");
		model.addAttribute("fields", Movie.class.getDeclaredFields());
		logger.info("new page accessed");
		return "Movie/new-page";
	}
}
