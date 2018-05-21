package com.demo.perform.impl;

import com.demo.perform.Performer;
import com.demo.perform.exception.PerformanceException;

public class PerformerImpl implements Performer {

	@Override
	public void perform() throws PerformanceException {
		System.out.println("Perform!");
	}

}
