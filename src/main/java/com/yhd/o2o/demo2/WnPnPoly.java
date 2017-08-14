package com.yhd.o2o.demo2;

/**
 * 
 */


import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-1-19 下午1:37:00
 */
public class WnPnPoly {

	public static void main(String[] args) {
		 
		Long startTime = System.currentTimeMillis();
		
		System.out.println(check(new Point(30,35),data2()));
		
		System.out.println("used time=" +  (System.currentTimeMillis()- startTime));
	}
	
	static boolean check(Point point , List<Point> list){
		 int    wn = 0;
		 int n = list.size();
		 list.add(list.get(0));
		 
		 Point startPoint = null;
		 Point endPoint = null;
		    for (int i=0; i<n; i++) {   // edge from V[i] to  V[i+1]
		    	startPoint = list.get(i);
		    	endPoint = list.get(i+1);
		        if (startPoint.lat <= point.lat) {          // start y <= P.lat
		            if (endPoint.lat  > point.lat)      // an upward crossing
		                 if (isLeft(point, startPoint,endPoint) > 0)  // P left of  edge
		                     ++wn;            // have  a valid up intersect
		        }
		        else {                        // start y > P.lat (no test needed)
		            if (endPoint.lat  <= point.lat)     // a downward crossing
		                 if (isLeft( startPoint, endPoint, point) < 0)  // P right of  edge
		                     --wn;            // have  a valid down intersect
		        }
		    }
		  
		    if(wn==0){
		    	//System.out.println("outside");
		    	return false;
		    }else{
		    	//System.out.println("inside");
		    	return true;
		    }
		 
		   
	}
		
	/**
	 * 
	 * @param testPoint  测试的点
	 * @param P0 边的起点
	 * @param P1 边的结束点
	 * @return
	 */
	static int  isLeft(Point testPoint,Point P0, Point P1){
		 
		return ( (P1.lng - P0.lng) * (testPoint.lat - P0.lat)
	            - (testPoint.lng -  P0.lng) * (P1.lat - P0.lat) );
	}
	
	static List<Point> data2(){
		List<Point> result= new ArrayList<Point>();
		result.add(new Point(25, 5));
		result.add(new Point(55, 25));
		result.add(new Point(15, 45));
		result.add(new Point(35, 22));
		result.add(new Point(10, 30));
		return result;
	}
}
