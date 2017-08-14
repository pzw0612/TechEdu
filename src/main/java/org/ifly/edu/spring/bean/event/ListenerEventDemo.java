/**
 * 
 */
package org.ifly.edu.spring.bean.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-4-3 下午10:58:34
 */
public class ListenerEventDemo {
    public static void main(String[] args) {  
        ApplicationContext context = new ClassPathXmlApplicationContext(  
                "spring.xml");  
        EmailListEvent emailListEvent = new EmailListEvent("hello",  
                "helloSpring@sina.com", "this is a test eamil content");  
        //在ApplicationContext中发布一个 ApplicationEvent  
        context.publishEvent(emailListEvent);  
    } 
}
