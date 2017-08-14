/**
 * 
 */
package org.ifly.edu.java.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-3-25 上午10:21:31
 */
public class AtomicIntegerArrayTest {

	static int[] value = new int[] { 1, 2 };

	static AtomicIntegerArray ai = new AtomicIntegerArray(value);

	public static void main(String[] args) {
		ai.getAndSet(0, 3);
		System.out.println(ai.get(0));
        System.out.println(value[0]);
       
	}
}
