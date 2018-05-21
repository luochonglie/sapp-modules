package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * 通过名字查找User
	 * 
	 * @param name
	 *            名字
	 * @return User列表
	 */
	List<User> findByName(String name);

	/**
	 * 通过名字查找User，根据Id升序排序
	 * 
	 * @param name
	 *            名字
	 * @return User列表
	 */
	List<User> findByNameOrderById(String name);
}
