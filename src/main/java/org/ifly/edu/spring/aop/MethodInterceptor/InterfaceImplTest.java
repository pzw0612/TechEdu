package org.ifly.edu.spring.aop.MethodInterceptor;

public class InterfaceImplTest implements Interface {  
	  
    /**  
     * @see aop.Interface#hello() 
     */  
    @Override  
    public void hello() {  
        System.out.println("helo world!!");  
    }  
  
}
