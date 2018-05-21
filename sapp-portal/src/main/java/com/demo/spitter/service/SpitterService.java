package com.demo.spitter.service;

import com.demo.spitter.entity.Spitter;

public interface SpitterService {

	Spitter findByUserName(String userName);

	Spitter save(Spitter spitter);

}
