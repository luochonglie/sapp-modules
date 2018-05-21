package com.demo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public abstract class AbstractTestCase {

	protected static ApplicationContext context;

	@BeforeClass
	public static void beforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("classpath*:application.xml");
	}

	@AfterClass
	public static void afterClass() throws Exception {

	}

}
