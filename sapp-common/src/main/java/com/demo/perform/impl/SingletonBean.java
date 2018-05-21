package com.demo.perform.impl;

public class SingletonBean {

	private SingletonBean() {
	}

	private static class InstanceHolder {
		static SingletonBean instance = new SingletonBean();
	}

	public static SingletonBean getInstance() {
		return InstanceHolder.instance;
	}

	public void doSomething() {
		System.out.println("Do some thing!");
	}

}
