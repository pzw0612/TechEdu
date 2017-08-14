package org.ifly.edu.spring.aop.aspect.test;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	
    public static void main(String[] args) {
		 ApplicationContext ac = new ClassPathXmlApplicationContext("org/ifly/edu/spring/aop/aspect/test/pss-client-aop.xml");
//		 
//		 Interface bean = (Interface)ac.getBean("testBean");
//		 
//		 try {
//			bean.hello();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		 
//		 HelloAop helloAop = (HelloAop)ac.getBean("helloAop");
//		 helloAop.handle();
//		 Hello2Aop hello2Aop = (Hello2Aop)ac.getBean("hello2Aop");
//		 hello2Aop.handle();
		 
		 ArrayList stringTest = (ArrayList)ac.getBean("arrTest");
		 
		 System.out.println(stringTest.get(-1));
	}
}
