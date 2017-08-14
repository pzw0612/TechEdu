package org.ifly.edu.spring.bean.factoryMethod;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	  public static void main(String[] args) {
	    ApplicationContext context = new ClassPathXmlApplicationContext(
	                    "org/ifly/edu/spring/bean/factoryMethod/spring-factory-method.xml");
	   
	    ExampleBean exampleBean = 
	            (ExampleBean)context.getBean("exampleBean");
	    
	    exampleBean.write();
	  }
	  
}
