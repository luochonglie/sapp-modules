package com.demo.spitter.service;

import java.util.List;

import com.demo.spitter.entity.Spittle;

public interface SpittleService {

	List<Spittle> getRecentSpittles(int pageSize);

	Spittle findById(long id);

}
