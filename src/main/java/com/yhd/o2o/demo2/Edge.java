/**
 * 
 */
package com.yhd.o2o.demo2;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-1-19 下午1:34:25
 */
public class Edge {

	public Point startPoint;
	public Point endPoint;
	
	public double factorK;
	public double factorB;

	/**
	 * 线的类型
	 * 1: 普通线
	 * 2：与水平垂直线（纬度不变）
	 * 3：与水平平行线（经度不变）
	 */
	public int lineType ; 
	
	public int  contValue;
	
	
	public int getLineType() {
		return lineType;
	}

	public void setLineType(int lineType) {
		this.lineType = lineType;
	}

	public int getContValue() {
		return contValue;
	}

	public void setContValue(int contValue) {
		this.contValue = contValue;
	}

	public Edge(Point startPoint, Point endPoint) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		calcFactor();
	}
	
	 public void calcFactor(){
		 if(startPoint.lng == endPoint.lng && startPoint.lat == endPoint.lat){
			 System.out.println(" error ");
			 System.exit(1);
		 }
		 if(startPoint.lng == endPoint.lng){//与水平线平行
			 lineType = 3;
			 contValue = startPoint.lng;
		 }else if(startPoint.lat == endPoint.lat){ //与水平线垂直
			 lineType = 2;
			 contValue = startPoint.lat;
		 }
		 else{
			 lineType = 1;
			 factorK = new Double((endPoint.lng - startPoint.lng)) / new Double((endPoint.lat - startPoint.lat));
			 factorB = startPoint.lng - factorK * startPoint.lat ;
			 
		 }
	 }
	 
	 public boolean compare(Point startPoint, Point endPoint){
		
		 Boolean startPointEq = null;
		 boolean result = false;

		 
		 if(startPoint.equals(this.startPoint)){
			 startPointEq =true;
		 }else if(startPoint.equals(this.endPoint)){
			 startPointEq = false;
		 }
		 
		 if(startPointEq==null ) return false;
		 
		 if(startPointEq){
			 if(endPoint.equals(this.endPoint)){
				 result =true;
			 }
		 }else{
			 if(endPoint.equals(this.startPoint)){
				 result =true;
			 }
		 }
		
		 return result;
	 }
	
	public Point getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	public Point getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	
	public double getFactorK() {
		return factorK;
	}

	public void setFactorK(double factorK) {
		this.factorK = factorK;
	}

	public double getFactorB() {
		return factorB;
	}

	public void setFactorB(double factorB) {
		this.factorB = factorB;
	}

	@Override
	public String toString() {
		return "Edge [startPoint=" + startPoint + ", endPoint=" + endPoint
				+ ", factorK=" + factorK + ", factorB=" + factorB
				+ ", lineType=" + lineType + ", contValue=" + contValue + "]";
	}


	
}
