package org.ifly.edu.spring.bean.dependOn;

public class User{

	public User() {
		System.out.println("User initialized");
		Shared.setValue("Set value in User Constructor!");
	}

}


