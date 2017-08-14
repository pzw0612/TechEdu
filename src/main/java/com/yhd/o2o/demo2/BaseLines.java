/**
 * 
 */
package com.yhd.o2o.demo2;

import java.util.ArrayList;
import java.util.List;

/**
 * 基准线
 * 
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-1-19 下午10:26:38
 */
public class BaseLines {

	//平行纬度线（经度不变)
	private List<Integer> lngLines = null;
	
	//垂直纬度线（纬度不变)
	private List<Integer> latLines = null;

	public BaseLines() {
		lngLines = new ArrayList<Integer>();
		latLines = new ArrayList<Integer>();
	}

	public BaseLines addLngLines(Integer lng) {
		lngLines.add(lng);
		return this;
	}
	
	public BaseLines plusLngLines(Integer lng) {
		lngLines.remove(lng);
		return this;
	}

	public BaseLines addLatLines(Integer lat) {
		latLines.add(lat);
		return this;
	}

	public BaseLines plusLatLines(Integer lat) {
		latLines.remove(lat);
		return this;
	}
	
	public List<Integer> getLngLines() {
		return lngLines;
	}

	public void setLngLines(List<Integer> lngLines) {
		this.lngLines = lngLines;
	}

	public List<Integer> getLatLines() {
		return latLines;
	}

	public void setLatLines(List<Integer> latLines) {
		this.latLines = latLines;
	}

	@Override
	public String toString() {
		return "BaseLines [lngLines=" + lngLines + ", latLines=" + latLines + "]";
	}
}
