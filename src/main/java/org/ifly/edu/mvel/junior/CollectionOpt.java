/**
 * 
 */
package org.ifly.edu.mvel.junior;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import org.ifly.edu.mvel.model.User;
import org.mvel2.MVEL;

/**
 * 
 * @author pangzhiwang
 * @date 2016-4-5 下午11:55:38
 */
public class CollectionOpt {
	public static void main(String[] args) {
		listOpt();
		System.out.println("~~~~~~~~~~~~");
		arrayOpt();
		System.out.println("~~~~~~~~~~~~");
		mapOpt();
	}

	static void listOpt() {
		String expression = "['Jim','Bob','Tom']";
		List<String> l = (List<String>) MVEL.eval(expression);
		for (String str : l) {
			System.out.println(str);
		}
	}

	static void arrayOpt() {
		String expression = "{'Jim','Bob','Tom'}";
		Object str = MVEL.eval(expression);
		if (str.getClass().isArray()) {
			System.out.println(String.valueOf(Array.get(str, 0)));
		}
	}

	static void mapOpt() {
		String expression = "['Bob' : new org.ifly.edu.mvel.model.User('Bob'), 'Michael' : new org.ifly.edu.mvel.model.User('Michael')]";
		Map o = (Map) MVEL.eval(expression);
		User u = (User) o.get("Bob");
		System.out.println(u.getName());
	}
}
