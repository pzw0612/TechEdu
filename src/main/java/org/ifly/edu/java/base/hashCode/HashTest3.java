/**
 * 
 */
package org.ifly.edu.java.base.hashCode;

import java.util.HashSet;
import java.util.Set;


/**
 * hash code 不同,equals 相同
 * @author pangzhiwang
 * @date 2016-4-4 上午11:22:48
 */
public class HashTest3 {
	private int i;

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
	
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object == this) {
			return true;
		}
		if (!(object instanceof HashTest3)) {
			return false;
		}
		HashTest3 other = (HashTest3) object;
		if (other.getI() == this.getI()) {
			return true;
		}
		return false;
	}
	
	public int hashCode() {
		return i % 10;
	}
	
	public final static void main(String[] args) {
		HashTest3 a = new HashTest3();
		HashTest3 b = new HashTest3();
		a.setI(1);
		b.setI(1);
		Set<HashTest3> set = new HashSet<HashTest3>();
		set.add(a);
		set.add(b);
		System.out.println(a.hashCode() == b.hashCode());
		System.out.println(a.equals(b));
		System.out.println(set);
		

	}
}
