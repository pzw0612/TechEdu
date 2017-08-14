package org.ifly.edu.spring.bean.dependOn;

public class Goods {

	public Goods() {
		System.out.println("Goods initialized");
	}

	public String toString() {
		return "Shared.getValue() = " + Shared.getValue();
	}

}
