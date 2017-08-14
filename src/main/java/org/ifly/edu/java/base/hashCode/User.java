/**
 * 
 */
package org.ifly.edu.java.base.hashCode;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-4-4 上午10:20:30
 */
public class User {

	private Long id;
	private String  name;
	private String  clsId;
	
	
	public User() {
		super();
		
	}
	public User(Long id, String name, String clsId) {
		super();
		this.id = id;
		this.name = name;
		this.clsId = clsId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClsId() {
		return clsId;
	}
	public void setClsId(String clsId) {
		this.clsId = clsId;
	}
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
        if (!(obj instanceof User))
        {
            return false;
        }
        User rhs = (User) obj;
        return new EqualsBuilder().append(this.id, rhs.id).isEquals();
	}
	
	
	public static void main(String[] args) {
		User u1 = new User(1l,"zhangsan","1");
		User u2 = new User(1l,"zhangsan","1");
		
		System.out.println(u1.equals(u2));
	}
	
}
