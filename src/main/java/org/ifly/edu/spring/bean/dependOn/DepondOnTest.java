package org.ifly.edu.spring.bean.dependOn;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class DepondOnTest {

	public static void main(String[] args) {
		XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("spring.xml"));
		Goods goods = (Goods)beanFactory.getBean("goods");
		System.out.println(goods);
	} 
}
