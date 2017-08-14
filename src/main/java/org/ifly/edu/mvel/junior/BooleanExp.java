package org.ifly.edu.mvel.junior;

import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;

public class BooleanExp {

	public static void main(String[] args) {
		gt();
		println();
		soundslike();
	}
	
	static void gt(){
		Map m = new HashMap();
		m.put("a", 100);
		Boolean obj = (Boolean) MVEL. eval("a>100", m);
		if(obj){
		         System. out.println("a大于100" );
		} else{
		         System. out.println("a小于100" );
		}
	}
	static void soundslike(){
		String composite = "'foobar' soundslike 'fubar'";
		Object obj = MVEL. eval(composite);
		System.out.println(obj);
	}
	
	static void println(){
		System.out.println("---------------------");
	}
}


