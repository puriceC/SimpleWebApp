package com.forgacea.WebApp.Cotrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class StaticPageController {
	private static final Logger logger = LoggerFactory.getLogger(StaticPageController.class);

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
}
