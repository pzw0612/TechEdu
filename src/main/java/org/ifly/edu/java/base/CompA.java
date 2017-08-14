package org.ifly.edu.java.base;

public class CompA {

	private CompB b;

	public CompB getB() {
		return b;
	}

	public void setB(CompB b) {
		this.b = b;
	}
	
	public static void main(String[] args) {
		CompA a=	new CompA();
		CompB b=	new CompB("aa","bb");
		a.setB(b);
		
		System.out.println(b.getId() +" " + b.getName());
		
		b.setId("cc");
		
		System.out.println(a.getB().getId() +" " + a.getB().getName());
	}
	
}
