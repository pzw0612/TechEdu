/**
 * 
 */
package com.yhd.o2o.demo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yhd.o2o.demo2.Constants;
import com.yhd.o2o.demo2.Data;
import com.yhd.o2o.demo2.Point;
import com.yhd.o2o.demo2.Utils;
import com.yhd.o2o.demo2.WnPnPolySplit;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-2-26 上午11:17:46
 */
public class OverlapCellCalc {
  
	static public List<Shape> handleOverlap(List<Point> list){
		
		/**
		 * 重叠部分分组算法
		 * 1查询所有重叠单元格list1
		 * 2.取出经纬度最小单元格（lng,lat) A
		 * 3.以A为起点迭代找出相邻单元格list2（即上，右，右上， 下、左、左下 有关联的单元格 ）
		 * 4. list3= list1- list2
		 * 5. if(list3>0){
		 *       list1 =list3
		 *        重复2,3,4
		 *  }
		 * 6. if(list3=0),结束
		 */
		List<Shape> shapeList = new ArrayList<Shape>();
		
		List<String> lngLatStrList= convert(list);
		
		while(lngLatStrList.size()>0){
			String startPoint = lngLatStrList.get(0);
			lngLatStrList.remove(0);
			List<String> searchList = shapeGen(startPoint,lngLatStrList);
			Collections.sort(searchList, Utils.stringSort);
			System.out.println(searchList);
			shapeList.add(new Shape(searchList));
		}
		
		return shapeList;
		
	}
	
	/**
	 * 以A为起点找出相邻单元格list2（向上，向右，向右上， 向下、向左、向左下 ）
	 * @param startPoint
	 * @param lnglatStrList
	 * @return
	 */
	static List<String> shapeGen(String startPoint,List<String> lngLatStrList){
		List<String> result = new ArrayList<String>();
		
		result.add(startPoint);
		
		int index = startPoint.indexOf("_");
		Integer lng = Integer.valueOf(startPoint.substring(0,index));
		Integer lat = Integer.valueOf(startPoint.substring(index+1));
		
		List<String> searchList = lookup(lng,lat,lngLatStrList);
		
		lngLatStrList.removeAll(searchList);
		
		if(searchList.size()==0){
			return result;
		}
		int size= searchList.size();
		
		List<String> tmpList = new ArrayList<String>();
		
		for(int i=0; i< size; i++){
			tmpList = shapeGen(searchList.get(i),lngLatStrList);
			for(String lngLat: tmpList){
				if(result.contains(lngLat)){
					continue;
				}else{
					result.add(lngLat);
				}
			}
		}
		
		return result;
	}
	
	
	static List<String> lookup(Integer lng, Integer lat,List<String> lngLatStrList){
		List<String> result = new ArrayList<String>();
		
		//向上
		StringBuffer sb = new StringBuffer("");
		sb.append(lng + Constants.span).append("_").append(lat);
		String topPoint = sb.toString();
		if(lngLatStrList.contains(topPoint)){
			result.add(topPoint);
		}
		
		//右上
		sb = new StringBuffer("");
		sb.append(lng + Constants.span).append("_").append(lat+Constants.span);
		String rightTopPoint = sb.toString();
		if(lngLatStrList.contains(rightTopPoint)){
			result.add(rightTopPoint);
		}
		
		//右
		sb = new StringBuffer("");
		sb.append(lng ).append("_").append(lat+Constants.span);
		String rightPoint = sb.toString();
		if(lngLatStrList.contains(rightPoint)){
			result.add(rightPoint);
		}
		
		//下
		sb = new StringBuffer("");
		sb.append(lng- Constants.span).append("_").append(lat);
		String bottonPoint = sb.toString();
		if(lngLatStrList.contains(bottonPoint)){
			result.add(bottonPoint);
		}
		//左下
		sb = new StringBuffer("");
		sb.append(lng- Constants.span).append("_").append(lat- Constants.span);
		String leftBottonPoint = sb.toString();
		
		if(lngLatStrList.contains(leftBottonPoint)){
			result.add(leftBottonPoint);
		}
		
		//左
		sb = new StringBuffer("");
		sb.append(lng).append("_").append(lat- Constants.span);
		String leftPoint = sb.toString();
		if(lngLatStrList.contains(leftPoint)){
			result.add(leftPoint);
		}
		
		return result;
	}
	
	/**
	 * 将point 转换 为 string
	 * @param list
	 * @return
	 */
	static List<String> convert(List<Point> list){
		List<String> result = new ArrayList<String>();
		for(Point point: list){
			result.add("" + point.lng +"_" + point.lat);
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		List<Point>  data= Data.getOverlapData();
		List<Shape> shapeList = handleOverlap(data);
		
		for(Shape shape: shapeList){
			
			String outLine = WnPnPolySplit.outLineCellHandle(shape.getList());
			
			System.out.println("outLine=" + outLine); 
			
		}
		
//		String outLine = WnPnPolySplit.outLineCellHandle(shapeList.get(1).getList());		
//		System.out.println("outLine=" + outLine); 
		
	}
	 
}
