/**
 * 
 */
package com.yhd.o2o.demo1;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2015-12-3 上午10:00:22
 */
public class MapCell {

	private long leftTopLng;
	private long leftTopLat;
	private long rightBottonLng;
	private long rightBottonLat;
	
	public MapCell() {
		super();
	}
	public MapCell(long leftTopLng, long leftTopLat, long rightBottonLng,
			long rightBottonLat) {
		super();
		this.leftTopLng = leftTopLng;
		this.leftTopLat = leftTopLat;
		this.rightBottonLng = rightBottonLng;
		this.rightBottonLat = rightBottonLat;
	}
	public long getLeftTopLng() {
		return leftTopLng;
	}
	public void setLeftTopLng(long leftTopLng) {
		this.leftTopLng = leftTopLng;
	}
	public long getLeftTopLat() {
		return leftTopLat;
	}
	public void setLeftTopLat(long leftTopLat) {
		this.leftTopLat = leftTopLat;
	}
	public long getRightBottonLng() {
		return rightBottonLng;
	}
	public void setRightBottonLng(long rightBottonLng) {
		this.rightBottonLng = rightBottonLng;
	}
	public long getRightBottonLat() {
		return rightBottonLat;
	}
	public void setRightBottonLat(long rightBottonLat) {
		this.rightBottonLat = rightBottonLat;
	}
	@Override
	public String toString() {
		return "MapCell [leftTopLng=" + leftTopLng + ", leftTopLat="
				+ leftTopLat + ", rightBottonLng=" + rightBottonLng
				+ ", rightBottonLat=" + rightBottonLat + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (leftTopLat ^ (leftTopLat >>> 32));
		result = prime * result + (int) (leftTopLng ^ (leftTopLng >>> 32));
		result = prime * result
				+ (int) (rightBottonLat ^ (rightBottonLat >>> 32));
		result = prime * result
				+ (int) (rightBottonLng ^ (rightBottonLng >>> 32));
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
		MapCell other = (MapCell) obj;
		if (leftTopLat != other.leftTopLat)
			return false;
		if (leftTopLng != other.leftTopLng)
			return false;
		if (rightBottonLat != other.rightBottonLat)
			return false;
		if (rightBottonLng != other.rightBottonLng)
			return false;
		return true;
	}
	
	
	
}
