package org.ifly.edu.spring.aop.MethodInterceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


public class MyInterceptor implements MethodInterceptor {  
  
    /**  
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation) 
     */  
    @Override  
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {  
        String info = methodInvocation.getMethod().getDeclaringClass()+ "." +   
        methodInvocation.getMethod().getName() + "()";  
          
        System.out.println(info);  
          
        try{  
            Object result = methodInvocation.proceed();  
            return result;  
        }  
        finally{  
            System.out.println(info);  
        }  
    }  
}  
