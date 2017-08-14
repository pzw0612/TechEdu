/**
 * 
 */
package org.ifly.edu.mvel.junior;

import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;

/**
 * 返回值
 * @author pangzhiwang
 * @date 2016-4-5 下午6:03:02
 */
public class RuturnExp {
	public static void main(String[] args) {
		test1();
		System.out.println("~~~~~~~~~~~~~~");
		test2();
	}
	
	/**
	 * 返回b
	 */
	static void test1(){
		String com = "a=10;b=(a=2)+10;b"; //返回b
		Map m = new HashMap();
		m.put("a", 0);
		Object obj = MVEL. eval(com,m);
		System. out.println(com + "=" + obj);
	}
	
	/**
	 * 返回b
	 */
	static void test2(){
		String com = "a=10;b=(a=2)+10;a"; //返回a
		Map m = new HashMap();
		m.put("a", 0);
		Object obj = MVEL.eval(com,m);
		
		System. out.println(com + "=" + obj);
	}
}
