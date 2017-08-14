/**
 * 
 */
package org.ifly.edu.java.base.hashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * hashcode 不同，equals 相同
 * @author pangzhiwang
 * @date 2016-4-4 上午11:21:15
 */
public class HashTest5 {
	private int i;
	


	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	private String other;
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public int hashCode() {
		return i % 10;
	}
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object == this) {
			return true;
		}
		if (!(object instanceof HashTest5)) {
			return false;
		}
		HashTest5 other = (HashTest5) object;
		if (other.getOther() == this.getOther()) {
			return true;
		}
		return false;
	}
	public final static void main(String[] args) {
		HashTest5 a = new HashTest5();
		HashTest5 b = new HashTest5();
		a.setI(1);
		a.setOther("1");
		b.setI(2);
		b.setOther("1");
		
		System.out.println(a.hashCode() == b.hashCode());
		System.out.println(a.equals(b));
		
		Map<HashTest5,Integer>  map = new HashMap<HashTest5,Integer>();
		
		map.put(a, 1);
		map.put(b, 1);
		
		System.out.println(map);
	}
}
