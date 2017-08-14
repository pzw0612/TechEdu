/**
 * 
 */
package org.ifly.edu.java.base.hashCode;

import java.util.HashMap;
import java.util.Map;


/**
 * hash code 不同,equals 相同
 * @author pangzhiwang
 * @date 2016-4-4 上午11:22:48
 */
public class HashTest4 {
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
		if (!(object instanceof HashTest4)) {
			return false;
		}
		HashTest4 other = (HashTest4) object;
		if (other.getOther() == this.getOther()) {
			return true;
		}
		return false;
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
	public final static void main(String[] args) {
		HashTest4 a = new HashTest4();
		HashTest4 b = new HashTest4();
		a.setI(1);
		a.setOther("1");
		b.setI(1);
		b.setOther("2");

		System.out.println(a.hashCode() == b.hashCode());
		System.out.println(a.equals(b));
		
		Map<HashTest4,Integer>  map = new HashMap<HashTest4,Integer>();
		
		map.put(a, 1);
		map.put(b, 1);
		
		System.out.println(map);
	}
}
