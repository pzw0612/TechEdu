package org.ifly.edu.spring.bean.init;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class PersonService  implements InitializingBean,DisposableBean{
  
	private String  message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
		System.out.println("set property message=" + message);
	}

	public PersonService(){
		System.out.println("PersonService instance");
	}
	@Override
	public void destroy() throws Exception {
	
		System.out.println("DisposableBean interface...."+ message);
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("InitializingBean  interface...." + message);
		
	}
	

}

