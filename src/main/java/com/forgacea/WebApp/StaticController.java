package com.forgacea.WebApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class StaticController {
	private static final Logger logger = LoggerFactory.getLogger(StaticController.class);

	@GetMapping("loginPage")
	public String getLogin() {
		logger.info("login page accessed");
		return "loginPage.html";
	}
	@GetMapping({"", "index"})
	public String getIndex() {
		logger.info("index accessed");
		return "index.html";
	}
}
