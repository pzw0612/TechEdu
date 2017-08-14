package org.ifly.edu.spring.aop.aspect.test;

import org.aspectj.lang.JoinPoint;

public class AopTest {
      
	private void doAction(JoinPoint joinPoint, Throwable ex) throws Exception{
                  
		System.out.println("aaa ........................................");
	}  
}
