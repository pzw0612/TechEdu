package org.ifly.edu.spring.bean.factoryMethod;

class ExampleBeanFactory {
	  public static ExampleBean createExampleBean(String string) {
		  System.out.println(" createExampleBean befor ");
	      return new ExampleBean(string);
	  }
}
