package com.demo.perform.impl;

import com.demo.perform.Poem;
import com.demo.perform.exception.PerformanceException;

public class PoeticJuggler extends Juggler {

	private Poem poem;

	public PoeticJuggler(int beanBags, Poem poem) {
		super(beanBags);
		this.poem = poem;
	}

	@Override
	public void perform() throws PerformanceException {
		super.perform();
		System.out.println("While reciting...");
		poem.recite();
	}

}
