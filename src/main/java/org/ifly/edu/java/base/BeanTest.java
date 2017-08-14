/**
 * 
 */
package org.ifly.edu.java.base;

/**
 * 
 * @author pangzhiwang
 * @date 2016-7-18 下午8:03:58
 */
public class BeanTest {

	private String aa;
	private long bb;
	private boolean flg;
	private int cc;
	
	private double dd;
	private float ff;
	public String getAa() {
		return aa;
	}
	public void setAa(String aa) {
		this.aa = aa;
	}
	public long getBb() {
		return bb;
	}
	public void setBb(long bb) {
		this.bb = bb;
	}
	public boolean isFlg() {
		return flg;
	}
	public void setFlg(boolean flg) {
		this.flg = flg;
	}
	public int getCc() {
		return cc;
	}
	public void setCc(int cc) {
		this.cc = cc;
	}
	public double getDd() {
		return dd;
	}
	public void setDd(double dd) {
		this.dd = dd;
	}
	public float getFf() {
		return ff;
	}
	public void setFf(float ff) {
		this.ff = ff;
	}
	public BeanTest() {
		super();
	}
	@Override
	public String toString() {
		return "BeanTest [aa=" + aa + ", bb=" + bb + ", flg=" + flg + ", cc="
				+ cc + ", dd=" + dd + ", ff=" + ff + "]";
	}
	
}
