/**
 * 
 */
package org.ifly.edu.spring.bean.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-4-3 下午10:53:53
 */
public class EmailListEvent extends ApplicationEvent {  
	  
    private static final long serialVersionUID = 1L;  
    public String address;  
    public String text;  
  
    public EmailListEvent(Object source) {  
        super(source);  
    }  
  
    public EmailListEvent(Object source, String address, String text) {  
        super(source);  
        this.address = address;  
        this.text = text;  
    }  
  
    public void print() {  
        System.out.println("Hello,Spring Event!!!");  
    }  
} 
