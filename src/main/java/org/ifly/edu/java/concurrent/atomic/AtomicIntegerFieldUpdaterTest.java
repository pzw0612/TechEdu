/**
 * 
 */
package org.ifly.edu.java.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-3-25 上午11:03:03
 */
public class AtomicIntegerFieldUpdaterTest {
	private static AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater
			.newUpdater(User.class, "old");

	public static void main(String[] args) {
		User conan = new User("conan", 10);
		System.out.println(a.getAndIncrement(conan));
		System.out.println(a.get(conan));
	}

	public static class User {
		private String name;
		public volatile int old;

		public User(String name, int old) {
			this.name = name;
			this.old = old;
		}

		public String getName() {
			return name;
		}

		public int getOld() {
			return old;
		}
	}
}
