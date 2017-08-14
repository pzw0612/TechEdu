/**
 * 
 */
package com.yhd.o2o.demo2;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-1-19 下午3:24:18
 */
//边界
class Boundary{
	
	public int minLng; //最小经度
	public int maxLng; //最大经度
	public int minLat; //最小纬度
	public int maxLat; //最大纬度
	
	public Boundary(){
		
	}
	
	public Boundary(int minLng, int maxLng, int minLat, int maxLat) {
		super();
		this.minLng = minLng;
		this.maxLng = maxLng;
		this.minLat = minLat;
		this.maxLat = maxLat;
	}


	public int getMinLng() {
		return minLng;
	}
	public void setMinLng(int minLng) {
		this.minLng = minLng;
	}
	public int getMaxLng() {
		return maxLng;
	}
	public void setMaxLng(int maxLng) {
		this.maxLng = maxLng;
	}
	public int getMinLat() {
		return minLat;
	}
	public void setMinLat(int minLat) {
		this.minLat = minLat;
	}
	public int getMaxLat() {
		return maxLat;
	}
	public void setMaxLat(int maxLat) {
		this.maxLat = maxLat;
	}
	@Override
	public String toString() {
		return "Boundary [minLng=" + minLng + ", maxLng=" + maxLng
				+ ", minLat=" + minLat + ", maxLat=" + maxLat + "]";
	}
	
}
