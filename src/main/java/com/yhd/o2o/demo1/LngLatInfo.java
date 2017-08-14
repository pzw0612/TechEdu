/**
 * 
 */
package com.yhd.o2o.demo1;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2015-12-3 上午9:22:29
 */
public class LngLatInfo {

	private long lng;
	private long lat;
	public long getLng() {
		return lng;
	}
	public void setLng(long lng) {
		this.lng = lng;
	}
	public long getLat() {
		return lat;
	}
	public void setLat(long lat) {
		this.lat = lat;
	}
	@Override
	public String toString() {
		return "LngLatInfo [lng=" + lng + ", lat=" + lat + "]";
	}
	
	
}
