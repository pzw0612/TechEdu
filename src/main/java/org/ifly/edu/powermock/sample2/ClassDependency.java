package org.ifly.edu.powermock.sample2;

import org.powermock.core.classloader.annotations.PrepareForTest;


public class ClassDependency {

	public  final boolean isAlive() {

		return false;

	}

	public static boolean isExist() {

		return false;

	}
}
