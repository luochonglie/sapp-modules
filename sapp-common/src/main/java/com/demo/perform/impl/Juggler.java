package com.demo.perform.impl;

import com.demo.perform.Performer;
import com.demo.perform.exception.PerformanceException;

public class Juggler implements Performer {

	private int beanBags = 3;

	public Juggler(int beanBags) {
		this.beanBags = beanBags;
	}

	@Override
	public void perform() throws PerformanceException {
		System.out.println("JUGGLING " + beanBags + " BEANBAGS");
	}

}
