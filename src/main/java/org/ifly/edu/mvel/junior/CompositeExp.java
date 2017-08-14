package org.ifly.edu.mvel.junior;

import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;

/**
 * 
	* 复合表达式
	* @author pangzhiwang
	* @date 2016-4-5 下午5:59:16
 */
public class CompositeExp {
	public static void main(String[] args) {
		String com = "a=a+3;a=a+4;a=a+5";
		Map m = new HashMap();
		m.put("a", 0);
		Object obj = MVEL. eval(com,m );
		System. out.println(obj);
	}
}
