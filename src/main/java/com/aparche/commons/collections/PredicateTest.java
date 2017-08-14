/**
 * 
 */
package com.aparche.commons.collections;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.functors.EqualPredicate;
import org.apache.commons.collections.functors.NotNullPredicate;
import org.apache.commons.collections.functors.UniquePredicate;
import org.apache.commons.collections.list.PredicatedList;
import org.hamcrest.core.IsInstanceOf;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-1-29 下午5:59:05
 */
public class PredicateTest {

	

	 
	/**
	 * 函数式编程之Predicate 断言
	 * 封装条件或判别式if else替代
	 *  1、 new EqualPredicate<类型>(值);
	 *     EqualPredicate.equalPredicate(值);
	 *     
	 *  2、 NotNullPredicate.notNullPredicate
	 *     NotNullPredicate.INSTANCE
	 *     
	 *     PredicatedList.predicatedXxx(容器，判断)
	 *     
	 *  3、 UniquePredicate.uniquePredicate()
	 *  
	 *  4、 自定义  new Predicate类 + 重写evaluate方法
	 *          PredicateUtils.allPredicate   多于两个
	 *                         andPredicate   两个
	 *                         anyPredicate   其中一个
	 *        
	 */
	@SuppressWarnings("all")

	    public static void main(String[] args) {
	        Test001();
	        Test002();
	        Test003();
	        Test004();      
	    }
	 
	    /**
	     * 比较相等判断
	     */
	   public static void Test001()
	   {
	        System.out.println("=====相等判断=======");
	        Predicate pre = new EqualPredicate("liguodong");
	        //Predicate<string> pre = EqualPredicate.equalPredicate("liguodong");//同上
	        boolean flag = pre.evaluate("li");
	        System.out.println(flag);       
	   }
	 
	 
	   /**
	    * 非空判断
	    */
	   public static void Test002()
	   {
	        System.out.println("=====非空判断=======");
	        Predicate  notNull = NotNullPredicate.INSTANCE;
	        //Predicate  notNull = NotNullPredicate.notNullPredicate();//同上
	        String str = "lgd";
	        System.out.println(notNull.evaluate(str));//非空为true,否则为false。
	 
	        //添加容器值得判断
	        List<Long> list = PredicatedList.decorate(new ArrayList<Long>(), notNull);
	        list.add(1000L);
	        //list.add(null);//null值为false， 验证失败，出现异常
	   }
	 
	 
	   public static void Test003()
	    {
	        System.out.println("=====唯一性判断=======");
	        Predicate uniquePre = UniquePredicate.getInstance();      
	        List<Long> list = PredicatedList.decorate(new ArrayList<Long>(),uniquePre);
	        list.add(100L);
	        list.add(200L);
	        //list.add(100L);//出现重复值，抛出异常
	 
	    }
	 
	 
	   public static void Test004(){
	       System.out.println("=====自定义判断=======");
	        //自定义的判别式
	        Predicate selfPre = new Predicate() {
	            @Override
	            public boolean evaluate(Object object) {
	            	boolean result = true;
	            	if(object instanceof  String){
	            		String str= (String)object;
	            		result = str.length()>=5 && str.length()<=20;
	            	}
	                return result;
	            }       
	        };
	 
	        Predicate notNull = NotNullPredicate.getInstance();//非空
	 
	        Predicate all = PredicateUtils.allPredicate(new Predicate[]{selfPre,notNull});  
	        
	        List<String> list = PredicatedList.decorate(new ArrayList<String>(), all);
	        list.add("liguodong");
	        //list.add(null);//java.lang.NullPointerException
	        //list.add("byby");//java.lang.IllegalArgumentException
	   }

}
