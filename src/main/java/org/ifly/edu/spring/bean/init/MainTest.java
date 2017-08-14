package org.ifly.edu.spring.bean.init;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTest {
	public static void main(String[] args) {

		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"org/ifly/edu/spring/bean/init/init.xml");
//		ClassA a = (ClassA) context.getBean("a");
//		System.out.println(a);

		PersonService personService = (PersonService) context
				.getBean("personService");
		

		LifeCycleBean bean1 = (LifeCycleBean)context.getBean("lifeCycleBean",LifeCycleBean.class); 
		
		System.out.println("***********" + bean1 +  "   " + bean1.getStr());  
		
		
		
		context.registerShutdownHook();
	}
}
