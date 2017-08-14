/**
 * 
 */
package com.yhd.o2o.demo2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-1-28 上午10:03:06
 */
public class Utils {

	public static <T> List<T> cloneList(List<T> list){
		
		int length = list.size();
		List<T> result = new ArrayList<T>(length);
		for(T point : list){
			result.add(point);
		}
			
		return result;
	}
	
	public static <T> List<T> cloneListEn(List<T> list){
		int length = list.size();
		List<T> result = new ArrayList<T>(length);
		
		for(T point : list){
			result.add(cloneBean(point));
		}
			
		return result;
	}
	
	public static <T> T cloneBean(T bean){
		T result = null;
		try {
			result = (T)BeanUtils.cloneBean(bean);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	public static Comparator<Point> lngsort = new Comparator<Point>(){
		@Override
		public int compare(Point o1, Point o2) {
			
			return o1.getLng() - o2.getLng();
		}
	};
	
	public static Comparator<Point> latsort = new Comparator<Point>(){
		@Override
		public int compare(Point o1, Point o2) {
			
			return o1.getLat() - o2.getLat();
		}
	};
	public static Comparator<String> stringSort = new Comparator<String>(){
		@Override
		public int compare(String o1, String o2) {
			
			return o1.compareTo(o2);
		}
	};
	public static Comparator<Integer> integerSort = new Comparator<Integer>(){
		@Override
		public int compare(Integer o1, Integer o2) {
			
			return o1.compareTo(o2);
		}
	};
	
	
	public static  List<Point> string2LngLat(String lngLatStr){
		List<Point> result = new ArrayList<Point>();
//		121.70164,31.051872,121.520366,30.858731,121.878795,30.856373,121.855449,31.005978,121.781291,31.070694
		if(lngLatStr==null || lngLatStr.trim().equals("")){
			return result;
		}
		String[] arr = lngLatStr.split(",");
		
		if(arr.length % 2 !=0){
			throw new RuntimeException("数据有误");
		}
			
		 Integer lng ;
		 Integer lat ;
		 Point point;
		for(int i=0,j= arr.length / 2; i<j; i++){
			
			lng = ((Double)(Double.valueOf(arr[i*2]) * 100000)).intValue();
			lat = ((Double)(Double.valueOf(arr[i*2+1]) * 100000)).intValue();
			point = new Point(lng,lat);
			result.add(point);
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(string2LngLat("121.70164,31.051872,121.520366,30.858731,121.878795,30.856373,121.855449,31.005978,121.781291,31.070694"));
	}
	
}

