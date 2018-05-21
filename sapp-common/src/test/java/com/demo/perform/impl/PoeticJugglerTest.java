package com.demo.perform.impl;

import org.junit.Test;

import com.demo.AbstractTestCase;
import com.demo.perform.Performer;
import com.demo.perform.exception.PerformanceException;

public class PoeticJugglerTest extends AbstractTestCase {

	@Test
	public void testPerform() throws PerformanceException {
		Performer duck = context.getBean("poeticDuke", Performer.class);
		duck.perform();
	}

}
