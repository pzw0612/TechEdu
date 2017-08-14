/**
 * 
 */
package org.ifly.edu.mvel.junior;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;

/**
 * 
 * @author pangzhiwang
 * @date 2016-4-6 下午1:02:28
 */
public class Problem1 {
	public static void main(String[] args) throws Exception {
		
		System.err.println(1350 * 0.7 * (0.97 + 0.5 * 0.06));

		test1();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
		test2();

	}
	
	static void test1(){
		String exp3 = "a*b*(c+d*e)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", 1350d);
		map.put("b", 0.7);
		map.put("c", 0.97);
		map.put("d", 0.5);
		map.put("e", 0.06);
		Serializable exp4 = MVEL.compileExpression(exp3);
		System.err.println(MVEL.executeExpression(exp4, map, Double.class));
	}
	static void test2(){
		String exp3 = "a*b*(c+d*e)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", 1350);
		map.put("b", 0.7);
		map.put("c", 0.97);
		map.put("d", 0.5);
		map.put("e", 0.06);
		Serializable exp4 = MVEL.compileExpression(exp3);
		Double d = MVEL.executeExpression(exp4, map, Double.class);
		System.err.println(d);
	}
}
