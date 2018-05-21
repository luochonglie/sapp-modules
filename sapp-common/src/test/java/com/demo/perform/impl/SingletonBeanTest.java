package com.demo.perform.impl;

import org.junit.Test;

import com.demo.AbstractTestCase;

public class SingletonBeanTest extends AbstractTestCase {

	@Test
	public void testDoSomething() {
		SingletonBean bean = context.getBean("singletonBean", SingletonBean.class);
		bean.doSomething();
	}

}
