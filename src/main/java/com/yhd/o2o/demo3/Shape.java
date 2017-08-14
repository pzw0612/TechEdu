/**
 * 
 */
package com.yhd.o2o.demo3;

import java.util.ArrayList;
import java.util.List;



/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-2-26 下午5:55:46
 */
public class Shape {

	private List<String> list = null; ;

	public Shape() {
		super();
	}

	public Shape(List<String> list) {
		super();
		this.list = list;
	}

	public List<String> getList() {
		
		if(list==null || list.size()==0){
			list = new ArrayList<String>();
		}
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
	
	public Shape add(String lngLat){
		getList().add(lngLat);
		return this;
	}
}
