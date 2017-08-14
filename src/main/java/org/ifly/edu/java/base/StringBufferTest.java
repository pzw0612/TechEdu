/**
 * 
 */
package org.ifly.edu.java.base;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2015-11-10 下午5:18:59
 */
public class StringBufferTest {

	public static void main(String[] args) {
		
		StringBuffer sb = new StringBuffer("");
		String abc = null;
		sb.append(abc);
		
		System.out.println(sb.toString());
	}
}
