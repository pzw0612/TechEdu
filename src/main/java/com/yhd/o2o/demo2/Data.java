/**
 * 
 */
package com.yhd.o2o.demo2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-1-29 下午3:42:19
 */
public class Data {

	/**
	 *  在一个单元格内
	 * @return
	 */
	public static List<Point> getTooSmalSpanData(){
		List<Point> result= new ArrayList<Point>();
		result.add(new Point(1, 1));
		result.add(new Point(5, 1));
		result.add(new Point(5, 5));
		result.add(new Point(1, 5));
		return result;
	}
	
	/**
	 *  基准线上的测试数据		
	 * @return
	 */
	public static List<Point> getOnBaseLineData(){
		List<Point> result= new ArrayList<Point>();

		//基准线上的测试数据		
		result.add(new Point(10, 30));
		result.add(new Point(10, 80));
		result.add(new Point(20, 80));
		result.add(new Point(20, 90));
		result.add(new Point(60, 90));
		result.add(new Point(60, 70));
		result.add(new Point(70, 70));
		result.add(new Point(70, 30));
		
		return result;
	}
	
	/**
	 *  基准线上多个凹凸测试数据	
	 * @return
	 */
	public static List<Point> getOnBaseLineConcaveData(){
		List<Point> result= new ArrayList<Point>();
		
		result.add(new Point(10, 30));
		result.add(new Point(10, 80));
		result.add(new Point(20, 80));
		result.add(new Point(20, 110));
		result.add(new Point(60, 110));
		result.add(new Point(60, 90));
		result.add(new Point(30, 90));
		result.add(new Point(30, 60));
		result.add(new Point(60, 60));
		result.add(new Point(60, 70));
		result.add(new Point(70, 70));
		result.add(new Point(70, 30));
		
		return result;
	}
	
	/**
	 *  非准线上多个凹凸测试数据	
	 * @return
	 */
	public static List<Point> getNoBaseLineConcaveData(){
		List<Point> result= new ArrayList<Point>();
		
		result.add(new Point(25, 5));
		result.add(new Point(55, 25));
		result.add(new Point(15, 45));
		result.add(new Point(35, 30));
		result.add(new Point(5, 30));
		
		return result;
	}
	
	/**
	 * 自交叉数据测试
	 * @return
	 */
	public static List<Point> getInterSectionData(){
		List<Point> result= new ArrayList<Point>();
		
		result.add(new Point(20, 60));
		result.add(new Point(50, 60));
		result.add(new Point(20, 30));
		result.add(new Point(50, 20));
		
		return result;
	}
	
	/**
	 * 三角形
	 * @return
	 */
	public static List<Point> getTangaeData(){
		List<Point> result= new ArrayList<Point>();
		
		result.add(new Point(116390703,39891855));
		result.add(new Point(116391292,39891553));
		result.add(new Point(116390846,39891365));
		
		return result;
	}
	
	
	
	/**
	 * 重叠数据
	 * @return
	 */
	public static List<Point> getOverlapData(){
		List<Point> result= new ArrayList<Point>();
		
		//pologen1
		result.add(new Point(10,10));
		result.add(new Point(10,20));
		result.add(new Point(10,30));
		result.add(new Point(10,40));
		
		
		//pologen2 start
		result.add(new Point(10,60));
		result.add(new Point(10,70));
		result.add(new Point(20,60));
		result.add(new Point(20,70));
		
		//pologen2 end
		
		result.add(new Point(20,20));
		result.add(new Point(20,30));
		result.add(new Point(20,40));
		result.add(new Point(30,20));
		
		//pologen2 start
		result.add(new Point(30,60));
		result.add(new Point(30,70));
		
		//pologen2 end
		
		result.add(new Point(30,30));
		


		return result;
	}
//	/**
//	 * 重叠数据
//	 * @return
//	 */
//	public static List<Point> getOverlapData(){
//		List<Point> result= new ArrayList<Point>();
//		
//		//pologen1
//		result.add(new Point(10,10));
//		result.add(new Point(10,20));
//		result.add(new Point(10,30));
//		result.add(new Point(10,40));
//		
//		
//		result.add(new Point(20,20));
//		result.add(new Point(20,30));
//		result.add(new Point(20,40));
//		result.add(new Point(30,20));
//		
//		
//		
//		result.add(new Point(30,30));
//		
//		//pologen2
//		result.add(new Point(10,60));
//		result.add(new Point(10,70));
//		result.add(new Point(20,60));
//		result.add(new Point(20,70));
//		result.add(new Point(30,60));
//		result.add(new Point(30,70));
//		
//		return result;
//	}
	
//	result.add(new Point(15, 15));
//	result.add(new Point(35, 15));
//	result.add(new Point(35, 25));
//	result.add(new Point(15, 25));
	
	
	
//	result.add(new Point(64, 31));
//	result.add(new Point(40, 50));
//	result.add(new Point(65, 65));
//	result.add(new Point(32, 85));
//	result.add(new Point(79, 75));
	
//	result.add(new Point(35, 10));
//	result.add(new Point(55, 49));
//	result.add(new Point(25, 80));
//	result.add(new Point(45, 125));
//	result.add(new Point(5, 135));
//	result.add(new Point(15, 85));
//	result.add(new Point(35, 35));
//	
//	result.add(new Point(10, 10));
//	result.add(new Point(20, 10));
//	result.add(new Point(20, 20));
//	result.add(new Point(10, 20));
	
	public static void main(String[] args) {
		List<Point> list = getNoBaseLineConcaveData();
		list.add(0,new Point(11111, 5));
		for(Point p : list){
			System.out.println(p);
		}
		
	}
}
