package com.yhd.o2o.map.crawler;


public class LocationBO {
	private double lng;
	private double lat;
	private int precise;
	private int confidence;
	private String level;
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public int getPrecise() {
		return precise;
	}
	public void setPrecise(int precise) {
		this.precise = precise;
	}
	public int getConfidence() {
		return confidence;
	}
	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
}
