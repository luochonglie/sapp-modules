package com.demo.perform.impl;

import org.junit.Test;

import com.demo.AbstractTestCase;

public class SetterServiceTest extends AbstractTestCase {

	@Test
	public void testDoSomething() throws Exception {
		SetterService bean = context.getBean("setterService", SetterService.class);
		bean.doSomething();
	}

}
