/**
 * 
 */
package org.ifly.edu.java.concurrent.happensBefore;

/**
 * 
 * @author pangzhiwang
 * @date 2016-7-22 下午9:47:26
 */

public class NoVisibility {
	private static boolean ready;
	private static int number;

	private static class ReaderThread extends Thread {
		public void run() {
			while (!ready) {
				Thread.yield();
			}
			System.out.println(number);
		}
	}

	public static void main(String[] args) throws Exception {
		new ReaderThread().start();
		number = 1;
		ready = true;
	}
}
