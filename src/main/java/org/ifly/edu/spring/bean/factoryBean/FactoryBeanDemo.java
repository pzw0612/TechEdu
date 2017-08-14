package org.ifly.edu.spring.bean.factoryBean;

import java.util.Date;

import org.springframework.beans.factory.FactoryBean;

public class FactoryBeanDemo implements FactoryBean {

	private String name;

	public void setName(String name) {

		this.name = name;

	}

	public Object getObject() throws Exception {

		if ("date".equals(name))
			return new Date();

		else
			return new String("这是一个字符串!");

	}

	public Class getObjectType() {

		return "date".equals(name) ? Date.class : String.class;

	}

	public boolean isSingleton() {

		return false;

	}

}
