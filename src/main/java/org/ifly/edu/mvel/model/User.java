package org.ifly.edu.mvel.model;

public class User {
	private String name;
	private Integer age;
	private Inner inner;
	
	public User() {
		super();
	}

	public User(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Inner getInner() {
		return inner;
	}

	public void setInner(Inner inner) {
		this.inner = inner;
	}


	public class Inner {
		int id;
		String name;
		
		public Inner() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
}
