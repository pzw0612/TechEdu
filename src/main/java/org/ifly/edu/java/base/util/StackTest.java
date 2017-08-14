/**
 * 
 */
package org.ifly.edu.java.base.util;

import java.util.Stack;



/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-1-29 上午10:53:32
 */
public class StackTest {

	public static void main(String[] args) {
		   Stack<Integer> stack = new Stack<Integer>();
		   for(int i=0; i<10; i++){
			   stack.add(i);
		   }
		   
		   System.out.println("peek:" + stack.peek());
		   
		   while(!stack.isEmpty()){
			   System.out.println("pop:" + stack.pop());
		   }
	}
}
