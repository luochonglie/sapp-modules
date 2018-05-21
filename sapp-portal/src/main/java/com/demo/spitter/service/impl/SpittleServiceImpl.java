package com.demo.spitter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.demo.spitter.entity.Spittle;
import com.demo.spitter.service.SpittleService;

@Service
public class SpittleServiceImpl implements SpittleService {

	@Override
	public List<Spittle> getRecentSpittles(int pageSize) {
		List<Spittle> list = new ArrayList<Spittle>();
		list.add(new Spittle("msg1", new Date()));
		list.add(new Spittle("msg2", new Date()));
		return list;
	}

	public Spittle findById(long id) {
		return new Spittle("msg1", new Date());
	}

}
