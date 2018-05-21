package com.demo.perform.impl;

import org.junit.Assert;
import org.junit.Test;

import com.demo.AbstractTestCase;

public class InitialAndDestoryBeanTest extends AbstractTestCase {

	@Test
	public void test() {
		InitialAndDestoryBean bean = context.getBean("initialAndDestoryBean", InitialAndDestoryBean.class);
		Assert.assertNotNull(bean);
	}

}
