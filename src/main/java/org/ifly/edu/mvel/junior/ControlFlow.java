/**
 * 
 */
package org.ifly.edu.mvel.junior;

import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;

/**
 * 
 * @author pangzhiwang
 * @date 2016-4-6 上午12:35:34
 */
public class ControlFlow {

	public static void main(String[] args) {
		ifElseExp();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		sanmuExp();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		foreachExp1();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		foreachExp2();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		dowhile();
	}
	/**
	 * if else
	 */
	static void ifElseExp() {
		String compoite = "if (a > 0) {" +
				       "System.out.println('Greater than zero!');}" +
				"else if (a == -1) {" +
				"System.out.println('Minus one!');}"
				+ "else { " +
				"System.out.println('Something else!');}";
		String com = "a=a+3;a=a+4;a=a+5";
		Map m = new HashMap();
		m.put("a", 0);
		Object obj = MVEL.eval(compoite, m);
		System.out.println(obj);
	}
	
	/**
	 * 三目运算
	 */
	static void sanmuExp() {
        String expression = "foo>0?'大于0':'小于等于0'" ;
        Map m = new HashMap();
        m.put("foo", -11);
        Object obj = MVEL. eval(expression,m );
        System. out.println(obj);
	}
	
	static void foreachExp1(){
		String expression = "foreach (x : 9) {  System.out.println(x);} " ;
		 Map<String, Object> m = new HashMap<String, Object>();
         m.put( "x", 1);
        MVEL. eval(expression);
        
	}
	static void foreachExp2(){
	       String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
           String expression = "foreach(el:str){System.out.println(el);}" ;
           Map<String, Object> m = new HashMap<String, Object>();
           m.put( "str", str);
           MVEL.eval (expression,m);
	}
	
	static void dowhile(){
		String expression = "do {System.out.println(i);i++;} while (i <10);" ;
        Map m = new HashMap();
        m.put("i", 1);
       Object obj = MVEL.eval (expression,m);
	}
	
}
