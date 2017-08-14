package org.ifly.edu.spring.bean.factoryBean;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class Main {
	public static void main(String[] args) {
	 Resource res=new ClassPathResource("spring.xml");

	BeanFactory factory=new XmlBeanFactory(res);

	System.out.println(factory.getBean("myFactoryBean").getClass());

	System.out.println(factory.getBean("myFactoryBean2").getClass()); 
}
}
