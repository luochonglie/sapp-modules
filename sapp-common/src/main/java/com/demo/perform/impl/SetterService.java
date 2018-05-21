package com.demo.perform.impl;

import com.demo.perform.Performer;

public class SetterService {
	private Performer performer;
	private int num;

	public void doSomething() throws Exception {
		performer.perform();
		System.out.println("Num : " + num);
	}

	public void setPerformer(Performer performer) {
		this.performer = performer;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
