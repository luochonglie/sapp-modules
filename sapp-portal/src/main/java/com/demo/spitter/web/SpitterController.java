package com.demo.spitter.web;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.spitter.entity.Spitter;
import com.demo.spitter.service.SpitterService;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

	@Resource
	private SpitterService spitterService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegistrationForm() {
		return "registerForm";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegistration(@Valid Spitter spitter, Errors errors) {

		if (errors.hasErrors()) {
			return "registerForm";
		}

		spitterService.save(spitter);
		return "redirect:/spitter/" + spitter.getUserName();
	}

	@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
	public String showProfile(@PathVariable("userName") String userName, Model model) {
		model.addAttribute(spitterService.findByUserName(userName));
		return "profile";
	}

	public void setSpitterService(SpitterService spitterService) {
		this.spitterService = spitterService;
	}

}
