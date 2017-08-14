package org.ifly.edu.powermock.sample2;

import java.io.File;

public class ClassUnderTest {

	public boolean callArgumentInstance(File file) {

		return file.exists();

	}

	public boolean callInternalInstance(String path) {

		File file = new File(path);

		return file.exists();

	}

	public boolean callFinalMethod(ClassDependency refer) {

		return refer.isAlive();

	}

	public boolean callStaticMethod() {

		return ClassDependency.isExist();

	}

	public boolean callPrivateMethod() {

		return isExist();

	}

	private boolean isExist() {

		return false;

	}

	public boolean callSystemFinalMethod(String str) {

		return str.isEmpty();

	}

	public static String callJDKStaticMethod(String str) {

		return System.getProperty(str);

	}
}