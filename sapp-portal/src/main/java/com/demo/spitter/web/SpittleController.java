package com.demo.spitter.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.spitter.service.SpittleService;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

	@Resource
	private SpittleService spittleService;

	@RequestMapping(method = RequestMethod.GET)
	public String spittles(Model model) {
		model.addAttribute(spittleService.getRecentSpittles(20));
		return "spittles";
	}

	@RequestMapping("/showSpittleByPathVariable/{spittleId}")
	public String showSpittleByPathVariable(@PathVariable("spittleId") long spittleId, Model model) {
		model.addAttribute(spittleService.findById(spittleId));
		return "spittle";
	}

	@RequestMapping("/showSpittleByRequestParam")
	public String showSpittleByRequestParam(@RequestParam("spittleId") long spittleId, Model model) {
		model.addAttribute(spittleService.findById(spittleId));
		return "spittle";
	}
	
	public void setSpittleService(SpittleService spittleService) {
		this.spittleService = spittleService;
	}

}
