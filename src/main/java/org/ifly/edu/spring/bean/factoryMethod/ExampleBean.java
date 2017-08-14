package org.ifly.edu.spring.bean.factoryMethod;

class ExampleBean {
	  private String string;
	  public ExampleBean(String string) {
	    this.string = string;
	  }
	  public void write(){
		  System.out.println(string);
	  }
	}
