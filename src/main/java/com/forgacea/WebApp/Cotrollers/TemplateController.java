package com.forgacea.WebApp.Cotrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {
	private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

	@GetMapping("login")
	public String getLogin() {
		logger.info("login page accessed");
		return "login-page.html";
	}
	@GetMapping({"", "index"})
	public String getIndex() {
		logger.info("index accessed");
		return "index-page.html";
	}
}
