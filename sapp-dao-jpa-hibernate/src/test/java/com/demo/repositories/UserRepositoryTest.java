package com.demo.repositories;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.entity.User;
import com.demo.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:spring-*.xml" })
public class UserRepositoryTest {

	@Resource
	private UserRepository userRepository;

	@Test
	public void testGetOne() {
		User user = userRepository.getOne(1);
		Assert.assertNotNull(user);
	}

	@Test
	public void testFindByName() {
		String userName = "aa";
		List<User> users = userRepository.findByName(userName);
		Assert.assertNotNull(users);
		for (User user : users) {
			Assert.assertEquals(userName, user.getName());
		}
	}

	@Test
	public void testFindByNameOrderById() {
		String userName = "aa";
		List<User> users = userRepository.findByName(userName);

		Assert.assertNotNull(users);
		Assert.assertTrue(users.size() > 1);
		for (User user : users) {
			Assert.assertEquals(userName, user.getName());
		}
		Assert.assertTrue(users.get(0).getId() < users.get(users.size() - 1).getId());
	}

}
