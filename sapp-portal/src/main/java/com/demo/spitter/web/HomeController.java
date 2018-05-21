package com.demo.spitter.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {
	public static final int DEFAULT_SPITTLES_PER_PAGE = 25;

	@RequestMapping(method = RequestMethod.GET)
	public String showHomePage(Map<String, Object> model) {
		return "home";
	}
}
