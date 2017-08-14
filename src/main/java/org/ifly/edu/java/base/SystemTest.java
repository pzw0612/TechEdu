package org.ifly.edu.java.base;

public class SystemTest {
  
	public static void main(String[] args) {
		getEnv();
		getProperty();
	}
	public static String getEnv(){
		String env = System.getenv("java_home");
		System.out.println("System.getEnv=" + env);
		
		return env;
	}
	public static String getProperty(){
		
		String property = System.getProperty("global.config.path");
		System.out.println("System.getProperty=" + property);
		
		return property;
	}
}
