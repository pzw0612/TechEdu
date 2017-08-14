/**
 * 
 */
package org.ifly.edu.spring.bean.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-4-3 下午10:55:29
 */
public class EmailNotifier implements ApplicationListener {  
	   
    public void onApplicationEvent(ApplicationEvent evt) {  
        if (evt instanceof EmailListEvent) {  
            EmailListEvent emailEvent = (EmailListEvent) evt;  
            emailEvent.print();  
            System.out.println("the source is:" + emailEvent.getSource());  
            System.out.println("the address is:" + emailEvent.address);  
            System.out.println("the mail's context is :" + emailEvent.text);  
        }  
  
    }  
} 
