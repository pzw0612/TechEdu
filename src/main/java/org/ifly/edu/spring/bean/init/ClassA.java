package org.ifly.edu.spring.bean.init;

import org.springframework.beans.factory.InitializingBean;

public class ClassA implements InitializingBean {

	 private ClassB b;
	 private String name; // = b.funb();

	 public void setB(ClassB b) {
	  System.out.println("A.setB initialed");
	  this.b = b;
	 }

	 public ClassA() {
	  System.out.println("A initialed");
	 }

	 public void init() {
	  System.out.println("init");
	  this.name = b.funb();
	 }

	 @Override
	 public String toString() {
	  return super.toString() + this.name;
	 }

	 public void afterPropertiesSet() throws Exception {

	  //其实放在这里也可以

	  //this.name = b.funb();
	  System.out.println("afterPropertiesSet");

	 }

	}


