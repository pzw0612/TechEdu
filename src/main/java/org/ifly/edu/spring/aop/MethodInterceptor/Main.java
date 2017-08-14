package org.ifly.edu.spring.aop.MethodInterceptor;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;


public class Main {  
  
    /** 
     *  
     * @param args 
     */  
    public static void main(String[] args) throws Exception{  
        ClassPathResource resource = new ClassPathResource("spring.xml");  
        XmlBeanFactory beanFactory = new XmlBeanFactory(resource);  
        Interface bean = (Interface)beanFactory.getBean("bean");  
        bean.hello();  
          
        Interface testBean = (Interface)beanFactory.getBean("testBean");  
        testBean.hello();  
    }  
}  
