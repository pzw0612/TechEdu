/**
 * 
 */
package org.ifly.edu.mvel.junior;

import java.util.HashMap;
import java.util.Map;

import org.ifly.edu.mvel.model.User;
import org.mvel2.MVEL;

/**
 * 
 * @author pangzhiwang
 * @date 2016-4-6 上午12:10:22
 */
public class NullSafeOpt {
	public static void main(String[] args) {
		test2();
	}
	
	static void test1(){
        String expression = "user.?name";
        User u = new User("zhangsan",11);
        Map m = new HashMap();
        m.put("user",u);
        Object obj = MVEL.eval(expression ,m);
        System.out.println(obj);
	}
	
	static void test2(){
        String expression = "user.inner!=null?user.inner.?id:'abc'";
        User u = new User();
        Map m = new HashMap();
        m.put("user",u);
    
        System.out.println(MVEL.eval(expression ,m));
       

	}
}
