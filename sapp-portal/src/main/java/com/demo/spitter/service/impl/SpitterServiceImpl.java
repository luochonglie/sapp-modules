package com.demo.spitter.service.impl;

import org.springframework.stereotype.Service;

import com.demo.spitter.entity.Spitter;
import com.demo.spitter.service.SpitterService;

@Service
public class SpitterServiceImpl implements SpitterService {

	@Override
	public Spitter findByUserName(String userName) {
		Spitter result = new Spitter();
		result.setUserName(userName);
		result.setFirstName("fn");
		result.setLastName("ln");
		return result;
	}

	@Override
	public Spitter save(Spitter spitter) {
		System.out.println("Save : " + spitter);
		return spitter;
	}

}
