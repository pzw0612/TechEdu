package org.ifly.edu.spring.aop.aspect.advisor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
 
public class GenericAdvisor {
 
         /**
          * 对于要增强的方法，执行环绕处理检查心跳是否正常
          * @param joinPoint
          * @return
          * @throws Throwable
          */
         Object heartbeat(ProceedingJoinPoint joinPoint) throws Throwable{
                  
//               if(checkHeartbeat()) {
//                        
//               }
                   System.out.println("2joinPoint.Signature.name："+joinPoint.getSignature().getName());
                   //记得在调用执行方法的时候异常不要try-catch，要thrwos出去，不然可能事务层没法起效，或者增强异常处理也无法起效
                   Object obj=joinPoint.proceed();
                   //下边的参入参数可以修改，但是类型和方法的个数要和原来一致，不然原方法无法执行
//               Objectobj=joinPoint.proceed(joinPoint.getArgs());
                  
                   //对于返回后对象，有可能要做处理，对返回参数的某一些值处理，
                   //可以通过org.springframework.beans.BeanUtils.copyProperties把一些值赋值
//                   if(obj==null) {
//                            return new AccountBank();
//                   }
                   return obj;
         }
         /**
          * 对于要增强的方法，在方法之前执行
          * @param joinPoint 连接点信息
          */
         void before(JoinPoint joinPoint){
                   Object[] objs=joinPoint.getArgs();
                   System.out.println("1objs:"+objs[0]);
                   System.out.println("1joinPoint:"+joinPoint);
         };
        
         /**
          * 对于要增强的方法，在方法之后执行
          * @param joinPoint  连接点信息
          */
         void after(JoinPoint joinPoint){
                   Object[] objs=joinPoint.getArgs();
                   System.out.println("4objs:"+objs[0]);
                   System.out.println("4joinPoint:"+joinPoint);
         };
         /**
          * 对于要增加的方法，方法返回结果后，对结果进行记录或者分析
          * 方法
          * @param obj target执行后返回的结果
          */
         void afterReturning(Object obj){
                   System.out.println("3obj:"+obj);
         };
         /**
          * 对于要增强的方法，方法抛出异常后，对异常的增强处理，比如记录异常，或者根据异常分析数据什么的
          * @param e target执行后抛出的异常
          */
         void handlerException(Throwable e){
                   System.out.println("handingException......");
                   e.printStackTrace();
         }
        
         boolean checkHeartbeat(){
                   return true;
         }
}
