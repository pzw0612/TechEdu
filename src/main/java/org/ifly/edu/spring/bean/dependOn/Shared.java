package org.ifly.edu.spring.bean.dependOn;

public class Shared {

	private static String value;

	public static String getValue() {
		return value;
	}

	public static void setValue(String value) {
		Shared.value = value;
	}

}
