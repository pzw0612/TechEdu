/**
 * 
 */
package com.yhd.o2o.demo2;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-1-19 下午1:32:22
 */
public class Point {
	public Integer lng ;
	public Integer lat ;

	public Point(){
		
	}
	public Point(Integer lng, Integer lat) {
		this.lng = lng;
		this.lat = lat;
	}

	public Integer getLng() {
		return lng;
	}
	public void setLng(Integer lng) {
		this.lng = lng;
	}
	public int getLat() {
		return lat;
	}
	public void setLat(Integer lat) {
		this.lat = lat;
	}
	@Override
	public String toString() {
		return "(" + lng + "," + lat + ")";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lat;
		result = prime * result + lng;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (lat != other.lat)
			return false;
		if (lng != other.lng)
			return false;
		return true;
	}

}
