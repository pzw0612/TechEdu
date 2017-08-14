package org.ifly.edu.java.cls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * getResourceAsStream 与 getResource
 * 
 * @author pangzhiwang
 * 
 */
public class ClassTest {
	public static void main(String[] args) {
//		getResource1();
//		getResource2();
//		getResource3();
//		getResource4();
//
//		System.out.println("----------------------------");

//		getResourceAsStream1();
//		getResourceAsStream2();
//		getResourceAsStream3();
		getResourceAsStream4();
	}

	/**
	 * 类的 getResource 如使用 了 "/" 需要从包名处开始 ，并且包名前需要有"/"，如果没有使用 "/"，则是从 当前类的位置
	 * 开始查找。 ClassLoader 的getResource,直接从包名开始查找
	 */
	public static void getResource1() {
		System.out.println("getResource1="
				+ ClassTest.class.getResource("file1.txt").getPath());
	}

	public static void getResource2() {
		System.out.println("getResource2="
				+ ClassTest.class.getResource(
						"/org/ifly/edu/java/cls/file1.txt").getPath());
	}

	public static void getResource3() {
		System.out.println("getResource3="
				+ ClassTest.class.getClassLoader()
						.getResource("org/ifly/edu/java/cls/file1.txt")
						.getPath());
	}

	public static void getResource4() {
		System.out.println("getResource4="
				+ ClassTest.class.getResource("/file2.txt").getPath());
	}

	/**
	 * 还有一个getResourceAsStream()方法，参数是与getResouce()方法是一样的，
	 * 它相当于你用getResource()取得File文件后，
	 * 再new InputStream(file)一样的结果 
	 */
	public static void getResourceAsStream1() {
		try {
			System.out.println("getResourceAsStream1="
					+ new BufferedReader(new InputStreamReader(ClassTest.class
							.getResourceAsStream("file1.txt"))).readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getResourceAsStream2() {

		try {
			System.out.println("getResourceAsStream2="
					+ new BufferedReader(new InputStreamReader(ClassTest.class
							.getResourceAsStream("/org/ifly/edu/java/cls/file1.txt"))).readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getResourceAsStream3() {
		try {
			System.out.println("getResourceAsStream3="
					+ new BufferedReader(new InputStreamReader(ClassTest.class.getClassLoader()
							.getResourceAsStream("org/ifly/edu/java/cls/file1.txt"))).readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void getResourceAsStream4() {
		
		try {
			System.out.println("getResourceAsStream1="
					+ new BufferedReader(new InputStreamReader(ClassTest.class
							.getResourceAsStream("/file2.txt"))).readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
