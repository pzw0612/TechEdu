package org.ifly.edu.spring.aop.aspect.test.sub;

public class HelloAop {
    
	public void handle() {
		
		System.out.println("test employ:" +(1/0));
	}
}
