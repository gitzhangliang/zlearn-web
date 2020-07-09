package com.zl.spring.circledi;

public class CircleA {
	private CircleB circleB;

	public CircleA() {
	}

	public CircleA(CircleB circleB) {
		this.circleB = circleB;
	}

	public void setCircleB(CircleB circleB) {
		this.circleB = circleB;
	}

	public void a() {
		circleB.b();
	}
}