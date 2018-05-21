package com.demo.jpa.dao;

import com.demo.jpa.entities.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class UserDaoTest {

	@Autowired
	UserDao userDao;

	@Test
	public void testGetOne() {
		User user = userDao.getOne(1);
		Assert.assertNotNull(user);
	}

}
