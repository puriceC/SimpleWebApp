package com.forgacea.WebApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class StaticController {

	@GetMapping("loginPage")
	public String getLogin() {
		return "loginPage.html";
	}
	@GetMapping({"", "index"})
	public String getIndex() {
		return "index.html";
	}
}
