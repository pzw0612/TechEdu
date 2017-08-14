package org.ifly.edu.mvel.junior;

import java.util.HashMap;
import java.util.Map;

import org.ifly.edu.mvel.model.User;
import org.mvel2.MVEL;
/**
 * MVEL基本语法-属性表达式
* 
* @author pangzhiwang
* @date 2016-4-5 下午3:38:51
 */
public class AttributeExpress {

	public static void main(String[] args) {

		getAttribute();
		println();
		setAttribute();
		println();
		setAttribute4with();
	}
	
	static void getAttribute(){
		User user = new User( "zhangs", 11);
		Map<String,User> vars = new HashMap<String,User>();
		vars.put("foo", user);
		Object result = MVEL. eval("foo.age", vars);
		System. out.println(result);
	}
	
	@SuppressWarnings("unused")
	static void setAttribute(){
		User user = new User();
		Map<String,User> vars = new HashMap<String,User>();
		vars.put("foo", user);
		Object result = MVEL.eval ("foo.age=20" , vars);
		System. out.println(user.getAge());
	}
	
	@SuppressWarnings("unused")
	static void setAttribute4with(){
		User user = new User();
		Map<String,User> vars = new HashMap<String,User>();
		vars.put("foo", user);
		Object result = MVEL.eval ("with(foo){age=20,name='zhangsan'}" , vars);
		System. out.println(user.getName());
	}
	
	static void println(){
		System.out.println("---------------------");
	}
}
