/**
 * 
 */
package com.yhd.o2o.demo1;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2015-12-3 上午9:20:21
 */
public class MapReqData {
    private String type;
    private String name;
    private Integer strokeWeight;
    private String strokeColor;
    private double strokeOpacity;
    
    private List<LngLatInfo> lnglat = new ArrayList<LngLatInfo>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStrokeWeight() {
		return strokeWeight;
	}

	public void setStrokeWeight(Integer strokeWeight) {
		this.strokeWeight = strokeWeight;
	}

	public String getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}

	public double getStrokeOpacity() {
		return strokeOpacity;
	}

	public void setStrokeOpacity(double strokeOpacity) {
		this.strokeOpacity = strokeOpacity;
	}

	public List<LngLatInfo> getLnglat() {
		return lnglat;
	}

	public void setLnglat(List<LngLatInfo> lnglat) {
		this.lnglat = lnglat;
	}

	@Override
	public String toString() {
		return "MapData [type=" + type + ", name=" + name + ", strokeWeight="
				+ strokeWeight + ", strokeColor=" + strokeColor
				+ ", strokeOpacity=" + strokeOpacity + ", lnglat=" + lnglat
				+ "]";
	}
    
}

