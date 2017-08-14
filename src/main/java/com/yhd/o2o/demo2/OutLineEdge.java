/**
 * 
 */
package com.yhd.o2o.demo2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-1-26 下午1:41:24
 */
public class OutLineEdge {
	
	private Integer lng =null;
	
	private Integer startLng =null;
	
	private Integer endLng =null;
	
	private boolean startNodeFlg =false;
	
	private boolean endNodeFlg =false;
	
	private List<Integer> rawData = new ArrayList<Integer>();
	
	public OutLineEdge() {
		super();
	}
	
	public OutLineEdge(List<Integer> rawData, Integer lng) {
		this.rawData = rawData;
		this.lng = lng;
		dataFlush();
	}
	
	public void dataFlush(){
		if(rawData!= null && rawData.size()>=2){
			this.startLng = rawData.get(0);
			this.endLng = rawData.get(rawData.size()-1) ;
		}
	}
	

	public OutLineEdge addData(Integer lat){
		rawData.add(lat);
		dataFlush();
		
		return this;
	}
	public List<Integer> getRawData() {
		return rawData;
	}

	public void setRawData(List<Integer> rawData) {
		this.rawData = rawData;
	}

	public Integer getLng() {
		return lng;
	}

	public void setLng(Integer lng) {
		this.lng = lng;
	}

	public Integer getStartLng() {
		return startLng;
	}

	public void setStartLng(Integer startLng) {
		this.startLng = startLng;
	}

	public Integer getEndLng() {
		return endLng;
	}

	public void setEndLng(Integer endLng) {
		this.endLng = endLng;
	}


	public boolean isStartNodeFlg() {
		return startNodeFlg;
	}

	public void setStartNodeFlg(boolean startNodeFlg) {
		this.startNodeFlg = startNodeFlg;
	}

	public boolean isEndNodeFlg() {
		return endNodeFlg;
	}

	public void setEndNodeFlg(boolean endNodeFlg) {
		this.endNodeFlg = endNodeFlg;
	}

	@Override
	public String toString() {
		dataFlush();
		return "OutLineEdge [lng=" + lng + ", startLng=" + startLng
				+ ", endLng=" + endLng + ", startNodeFlg=" + startNodeFlg
				+ ", endNodeFlg=" + endNodeFlg + ", rawData=" + rawData + "]";
	}
}
