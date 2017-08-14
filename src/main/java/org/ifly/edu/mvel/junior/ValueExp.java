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
 * @date 2016-4-5 下午11:43:09
 */
public class ValueExp {

	public static void main(String[] args) {
		  Map<String,String> context = new HashMap<String,String>();
		  context.put("foo", "111");
		  String compoit = "foo== '323'";
          Object obj = MVEL. eval(compoit,context);
          System.out.println(obj);
	}
}
