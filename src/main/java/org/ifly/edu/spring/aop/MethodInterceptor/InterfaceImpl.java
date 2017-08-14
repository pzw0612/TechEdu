package org.ifly.edu.spring.aop.MethodInterceptor;



public class InterfaceImpl implements Interface {  
	  
    /**  
     * @see com.alipay.aop.BusinessInterface#hello() 
     */  
    @Override  
    public void hello() throws Exception{  
        System.out.println("AOP TEST!!");  
        throw new Exception("aaa");
    }  
} 
