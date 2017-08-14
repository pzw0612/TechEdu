/**
 * 
 */
package com.yhd.o2o.demo2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *  切割任意多边形
 * @Description: 
 * @author pangzhiwang
 * @date 2016-1-19 下午1:37:00
 */
public class PolySplit4Exclusive {

	//单元格长宽
	final public static int span = 10;
	//切割后单元格
	private static Set<String>  cellSet = new HashSet<String>();

	private static List<String>  displayList = new ArrayList<String>();
	
	private static Map<Integer,List<Integer>>  lineData = new HashMap<Integer,List<Integer>> ();
	
	/**
	 * 多边形切割
	 * @param list
	 * @return
	 */
	static Set<String> split(List<Point> list){

		
		/**
		 * 1. 获取边界
		 * 2. 生成多变形的边（y= kx + b) 的 k 和b
		 * 3.计算经度上下界的各线\计算纬度上下界基准线
		 * 4. 求经度基准线与边相交的交点集合，
		 * 5. 求经度基准线与边相交的交点集合
		 * 7. 返回
		 */
		
		//1. 获取边界
		Boundary boundary = calcBoundary(list);
		
		//3.计算经度上下界的各线\计算纬度上下界基准线
//		BaseLines lines = genBaseLine(boundary,edgeList);
		BaseLines lines = genBaseLine(boundary);
		
		//span 太大问题处理
		if(lines.getLatLines().size()==0 && lines.getLngLines().size()==0){
			dealSmallRange(boundary,cellSet);
			return cellSet;
		}
		
		Map<Point,Boolean> map = new HashMap<Point,Boolean>();
		
		boolean topLeftFlg = false;
		Point topLeftPoint = null;
		boolean topRightFlg = false;
		Point topRightPoint = null;
		boolean bottonLeftFlg = false;
		Point bottonLeftPoint = null;
		boolean bottonRightFlg = false;
		Point bottonRightPoint = null;
		
//		for(Integer lng: lines.getLngLines()){
//			for(Integer  lat: lines.getLatLines()){
//				bottonLeftPoint = new Point(lng,lat);
//				bottonLeftFlg = WnPnPoly.check(bottonLeftPoint, list);
//				map.put(bottonLeftPoint, bottonLeftFlg);
//				
//				bottonRightPoint=new Point(lng,lat+span);
//				bottonRightFlg = WnPnPoly.check(bottonRightPoint, list);
//				map.put(bottonRightPoint, bottonRightFlg);
//				
//				topLeftPoint=new Point(lng+span,lat);
//				topLeftFlg = WnPnPoly.check(topLeftPoint, list);
//				map.put(topLeftPoint, topLeftFlg);
//				
//				topRightPoint=new Point(lng+span,lat+span);
//				topRightFlg = WnPnPoly.check(topRightPoint, list);
//				map.put(topRightPoint, topRightFlg);
//				
//				if(bottonLeftFlg && bottonRightFlg && topLeftFlg && topRightFlg){
//					cellSet.add(""+lng+"_" + lat);
//				}
//				
//				
//				
//			}
//		}
		
		WnPnPolyEn.check(new Point(30,50), list);
		WnPnPolyEn.check(new Point(20,50), list);
		WnPnPolyEn.check(new Point(20,60), list);
		WnPnPolyEn.check(new Point(30,60), list);
		WnPnPolyEn.check(new Point(20,80), list);
		WnPnPolyEn.check(new Point(30,80), list);
		WnPnPolyEn.check(new Point(30,90), list);
		WnPnPolyEn.check(new Point(20,90), list);
		return cellSet;
	}
 
	/**
	 * 处理小区域问题
	 * @param boundary
	 * @param cellSet
	 */
	public static void dealSmallRange(Boundary boundary,Set<String>  cellSet){
		 int minLng = boundary.getMinLng(); //最小经度
		 int minLat= boundary.getMinLat(); //最小纬度
		 while(minLng>=0){
			 if(minLng % span ==0){
				 break;
			 }
			 --minLng;
		 }
		 while(minLat>=0){
			 if(minLat % span ==0){
				 break;
			 }
			 --minLat;
		 }
		
		cellSet.add(""+minLng+"_" + minLat);
	
	}
	/** 
	 * 经度不变基准线与多边形切割，帅选单元格
	 * @param lines
	 * @param edgeList
	 * @param cellSet2
	 */
	
	private static void horizionSplitCell(BaseLines lines, List<Edge> edgeList,Set<String> cellSet) {
		//基准线与多边形各边的交集
		List<Integer> lngLines = lines.getLngLines();
		if(lngLines.size()==0){
			return;
		}
		if(edgeList.size()==0){
			return;
		}
	    
	
	    //用经度不变的基准线切割
		Integer tmpLat = null;
		Point tmpPoint = null;
		for(Integer lng: lngLines){
			  Integer lineType = null;
			  List<Point> intersectionList = new ArrayList<Point>();
			 //计算基准线与多边形各边的交点
			 for(Edge edge: edgeList){
				 lineType =  edge.getLineType();
				 if(lineType == 3){ //与水平线平行线
					 continue;
				 }else if(lineType==2){ //与水平线垂直线
					 tmpLat =  edge.getContValue();
					 
					 /**
					  * 检查数据合法性（水平线与垂直线 是否有交集）
					  */
					 if(min(edge.getStartPoint().getLng(),edge.getEndPoint().getLng()) > lng){
						 continue;
					 }
					 if(max(edge.getStartPoint().getLng(),edge.getEndPoint().getLng()) < lng){
						 continue;
					 }
					 
				 }else{
					 Double tmpLatDouble = ((new Double(lng) - edge.getFactorB()) / edge.getFactorK());
					 tmpLat = tmpLatDouble.intValue();
				 }
				 

				 /**
				  * 检查交点是否 在 一条边的范围内
				  */
				Integer max =  null;
				Integer min =  null;
				if(edge.getStartPoint().getLat() >= edge.getEndPoint().getLat()){
					max = edge.getStartPoint().getLat();
					min =  edge.getEndPoint().getLat();
				}else{
					max = edge.getEndPoint().getLat();
					min = edge.getStartPoint().getLat();
				}
				
				 if(tmpLat < min || tmpLat > max){
					 continue;
				 }
					 
				 tmpPoint = new Point(lng,tmpLat);
				 intersectionList.add(tmpPoint);
			 }
			 System.out.println("horizion intersectionList=" + intersectionList);
			 
			 if(intersectionList.size() % 2 != 0){
				filterIntersectionPoint(intersectionList,edgeList,1);
			 }
			 
			 // 经度不变方向 上 帅选单元格
			 filteCell(intersectionList,cellSet,1,edgeList);
		}
		
	}
	
	/** 
	 * 垂直方向基准线与多边形切割，帅选单元格
	 * @param lines
	 * @param edgeList
	 * @param cellSet2
	 */
	
	private static void verticalSplitCell(BaseLines lines, List<Edge> edgeList,Set<String> cellSet) {
		//基准线与多边形各边的交集
		List<Integer> latLines = lines.getLatLines();
		if(latLines.size()==0){
			return;
		}
		if(edgeList.size()==0){
			return;
		}
		
		//用纬度不变的基准线切割
		Integer tmpLng = null;
		Point tmpPoint = null;
		for(Integer lat: latLines){
			Integer lineType = null;
			List<Point> intersectionList = new ArrayList<Point>();
			//计算基准线与多边形各边的交点
			for(Edge edge: edgeList){
				lineType =  edge.getLineType();
				if(lineType == 3){ //与水平线平行线
					tmpLng =  edge.getContValue();
					 /**
					  * 检查数据合法性（水平线与垂直线 是否有交集）
					  */
					 if(min(edge.getStartPoint().getLat(),edge.getEndPoint().getLat()) > lat){
						 continue;
					 }
					 if(max(edge.getStartPoint().getLat(),edge.getEndPoint().getLat()) < lat){
						 continue;
					 }
					 
				}else if(lineType==2){ //与水平线垂直线
					continue;
				}else{
					Double tmpLngDouble =lat *  edge.getFactorK() + edge.getFactorB() ;
					tmpLng = tmpLngDouble.intValue();
				}
				
				/**
				 * 检查交点是否 在 一条边的范围内
				 */
				Integer maxLng =  null;
				Integer minLng =  null;
				if(edge.getStartPoint().getLng() >= edge.getEndPoint().getLng()){
					maxLng = edge.getStartPoint().getLng();
					minLng =  edge.getEndPoint().getLng();
				}else{
					maxLng = edge.getEndPoint().getLng();
					minLng = edge.getStartPoint().getLng();
				}
				
				 if(tmpLng < minLng || tmpLng > maxLng){
					 continue;
				 }
				 
				tmpPoint = new Point(tmpLng,lat);
				intersectionList.add(tmpPoint);
			}
			System.out.println("vertical intersectionList=" + intersectionList);
			
			 if(intersectionList.size() % 2 != 0){
				// filterIntersectionPoint(intersectionList,edgeList,2);
			 }
			// 纬度不变方向 上 帅选单元格
			filteCell(intersectionList,cellSet,2,edgeList);
		}
		
	}
	

    /**
     *  处理边与基准点重合问题,导致数据量为奇数(由于小概率事件)
     * 
     * @param intersectionList
     * @param edgeList
     * @param type 1:水平方向（经度不变）  2： 垂直方向 （纬度不变）
     */
	private static void filterIntersectionPoint(List<Point> intersectionList, List<Edge> edgeList,int type) {
		if(intersectionList== null || intersectionList.size() ==0) {
			return;
		}
		
		if(intersectionList.size() % 2==0){
			return ;
		}
		Point addPoint = null;
		
		boolean flg = false;
		int insertIndex = 0;
		if(type ==1){
			Integer lng = intersectionList.get(0).getLng();
			for(Edge edge: edgeList){
				if(edge.getLineType()==3 && edge.getContValue() == lng) {
					for(int i=0; i< intersectionList.size()-1; i++){
						Point point1 = intersectionList.get(i);
						Point point2 = intersectionList.get(i+1);
						if(flg && isOnLine(point1.getLat(), point2.getLat(), edge, 1)){
							addPoint = point1;
							insertIndex = i;
							break;
						}else{
							flg = true;
						}
					}
				}
			}
			
			if(addPoint!=null){
				intersectionList.add(insertIndex, addPoint);
				insertIndex = 0;
				addPoint = null;
			}
			
			
		} else if(type ==2){
			Integer lat = intersectionList.get(0).getLat();
			for(Edge edge: edgeList){
				if(edge.getLineType()==2 && edge.getContValue() == lat) {
					for(int i=0; i< intersectionList.size()-1; i++){
						Point point1 = intersectionList.get(i);
						Point point2 = intersectionList.get(i+1);
						if(flg && isOnLine(point1.getLng(), point2.getLng(), edge, 2)){
							addPoint = point1;
							insertIndex = i;
						}else{
							flg = true;
						}
					}
				}
			}
			if(addPoint!=null){
				intersectionList.add(insertIndex, addPoint);
				insertIndex = 0;
				addPoint = null;
			}
		}
	}

	/** 
	 * @param intersectionList
	 * @param cellSet2
	 * @param directType 1: 经度不变方向，2：纬度不变方向
	 */
	private static void filteCell(List<Point> intersectionList,Set<String> cellSet, int directType, List<Edge> edgeList) {
		
		int size = intersectionList.size();
		//交点为0 或者为奇数个，无须处理
//		if(size == 0 || (size % 2 !=0)){
//			return;
//		}
		Integer fstData = null;
		Integer secData = null;
		Integer tmpLng = null;
		Integer tmpLat = null;
		if(directType==1){
			Collections.sort(intersectionList,latsort);
			tmpLng = intersectionList.get(0).getLng();
			for(int i=0;i< size/2; i=i+1){
				fstData = intersectionList.get(2*i).getLat();
				secData = intersectionList.get(2*i+1).getLat();
				if(fstData.intValue() == secData.intValue()){
					continue;
				}
				
				boolean flg = false;
                for(Edge edge: edgeList){
//					if(edge.getLineType() !=3  &&  edge.getContValue() !=tmpLng){
//						continue;
//					}
//					if(edge.compare(new Point(tmpLng,fstData), new Point(tmpLng,secData))){
//						flg = true;
//						break;
//					}
					if(edge.getLineType() ==3  &&  edge.getContValue() ==tmpLng){
						if(isOnLine(fstData, secData,edge,1)){
							flg = true;
							break;
						}
					}

				}
                if(flg) continue;
                
			    while(fstData > 0){
			    	if(fstData % span ==0){
			    		break;
			    	}
			    	
			    	--fstData;
			    }
			    
			   while(fstData < secData){
				   cellSet.add("" + tmpLng+"_"+ fstData);
				  
					   if(tmpLng- span >=0){
						   cellSet.add("" + (tmpLng-span)+"_"+ fstData);
					   }

				   fstData = fstData + span;
			   }
			    
			}
			return;
		}
		
		if(directType==2){
			Collections.sort(intersectionList,lngsort);
			tmpLat = intersectionList.get(0).getLat();
			for(int i=0;i< size/2; i=i+1){
				fstData = intersectionList.get(2*i).getLng();
				secData = intersectionList.get(2*i+1).getLng();
				if(fstData.intValue() == secData.intValue()){
					continue;
				}
				boolean flg = false;
                for(Edge edge: edgeList){
					if(edge.getLineType() ==2  &&  edge.getContValue() ==tmpLat){
						if(isOnLine(fstData, secData,edge,2)){
							flg = true;
							break;
						}
					}

				}
                if(flg) continue;
				
			    while(fstData > 0){
			    	if(fstData % span ==0){
			    		break;
			    	}
			    	
			    	--fstData;
			    }
			   while(fstData < secData){
				   cellSet.add("" + fstData+"_"+ tmpLat);
				   if(tmpLat-span >=0){
					   cellSet.add("" + fstData+"_"+ (tmpLat-span));
				   } 

				   fstData = fstData + span;
			   }
			    
			}
			return;
		}
	}
//	
//	/** 
//	 * @param intersectionList
//	 * @param cellSet2
//	 * @param directType 1: 经度不变方向，2：纬度不变方向
//	 */
//	private static void filteCell(List<Point> intersectionList,Set<String> cellSet, int directType) {
//		
//		int size = intersectionList.size();
//		//交点为0 或者为奇数个，无须处理
//		if(size == 0 || (size % 2 !=0)){
//			return;
//		}
//		Integer fstData = null;
//		Integer secData = null;
//		Integer tmpLng = null;
//		Integer tmpLat = null;
//		if(directType==1){
//			Collections.sort(intersectionList,latsort);
//			tmpLng = intersectionList.get(0).getLng();
//			for(int i=0;i< size/2; i=i+1){
//				fstData = intersectionList.get(2*i).getLat();
//				secData = intersectionList.get(2*i+1).getLat();
//				if(fstData.intValue() == secData.intValue()){
//					continue;
//				}
//				
//				while(fstData > 0){
//					if(fstData % span ==0){
//						break;
//					}
//					
//					--fstData;
//				}
//				
//				while(fstData < secData){
//					cellSet.add("" + tmpLng+"_"+ fstData);
//					
//					if(tmpLng- span >=0){
//						cellSet.add("" + (tmpLng-span)+"_"+ fstData);
//					}
//					
//					
//					fstData = fstData + span;
//				}
//				
//			}
//			return;
//		}
//		
//		if(directType==2){
//			Collections.sort(intersectionList,lngsort);
//			tmpLat = intersectionList.get(0).getLat();
//			for(int i=0;i< size/2; i=i+1){
//				fstData = intersectionList.get(2*i).getLng();
//				secData = intersectionList.get(2*i+1).getLng();
//				if(fstData.intValue() == secData.intValue()){
//					continue;
//				}
//				
//				
//				while(fstData > 0){
//					if(fstData % span ==0){
//						break;
//					}
//					
//					--fstData;
//				}
//				while(fstData < secData){
//					cellSet.add("" + fstData+"_"+ tmpLat);
//					if(tmpLat-span >=0){
//						cellSet.add("" + fstData+"_"+ (tmpLat-span));
//					} 
//					
//					fstData = fstData + span;
//				}
//				
//			}
//			return;
//		}
//	}

	private static Integer max(Integer first, Integer second){
		if(first >= second) return first;
		return second;
	}
	private static Integer min(Integer first, Integer second){
		if(first <= second) return first;
		return second;
	}
	
	private static String point2String(Point point){
		StringBuffer sb = new StringBuffer("");
		sb.append(point.getLng()).append("_").append(point.getLat());
		return sb.toString();
	}
	 /** 
	 * @param point
	 * @return
	 */
	private static Point calcBasePointByPoint(Point point) {
		Point result = new Point();
	    int lng = result.getLng();
	    int lat = result.getLat();
	    
		if(lng % span ==0 || lat % span ==0 ){
			//TODO
			return null;
		}
		//经度处理
		boolean flg = false;
		while(lng > 0 ){
			if(lng % span ==0){
				flg  = true;
				break;
			}
			--lng;
		}
		if(flg || lng==0){
			result.setLng(lng);
		}
		
		//纬度处理
		flg = false;
		while(lat > 0 ){
			if(lat % span ==0){
				flg  = true;
				break;
			}
			--lat;
		}
		if(flg || lat==0){
			result.setLat(lat);
		}
		
		return result;
	}

	/** 
	  * 根据边界产生基准线
	 * @param boundary
	 * @return
	 */
	private static BaseLines genBaseLine(Boundary boundary) {
		BaseLines  result= new BaseLines();
		//纬度基准线
		Integer minLng = boundary.getMinLng();
		Integer maxLng = boundary.getMaxLng();
		Integer lng = minLng;
		while(lng <= maxLng){
			if(lng % span ==0){
				result.addLngLines(lng);
			}
			++lng;
		}
		System.out.println("lng lines = " + result.getLngLines());
		
		//纬度基准线
		Integer minLat = boundary.getMinLat();
		Integer maxLat = boundary.getMaxLat();
		Integer lat = minLat;
		while(lat <= maxLat){
			if(lat % span ==0){
				result.addLatLines(lat);
			}
			++lat;
		}
		System.out.println("lat lines = " + result.getLatLines());
		
		if(result.getLatLines().size()==0 && result.getLngLines().size()==0){
			System.out.println("span is too big,please modify" );
		}
		
		return result;
	}
	

	/** 
	  * 生成多变形的边（y= kx + b) 的 k 和b
	 * @param list
	 * @return
	 */
	private static List<Edge> genEdgesByVertix(List<Point> list) {
		List<Edge> result =  new ArrayList<Edge>();
		
		int length = list.size();
		List<Point> totalList = cloneList(list);
		
		totalList.add(list.get(0));
		
		for(int i= 0; i< length; i++){
			result.add(new Edge(totalList.get(i),totalList.get(i+1)));
		}
		System.out.println("Edge lines=" + result);
		return result;
	}

	
	public static <T> List<T> cloneList(List<T> list){
		
		int length = list.size();
		List<T> result = new ArrayList<T>(length);
		for(T point : list){
			result.add(point);
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
	public static Comparator<String> stringSort2 = new Comparator<String>(){
		@Override
		public int compare(String o1, String o2) {
			
			return o1.compareTo(o2);
		}
	};
	
	static Boundary calcBoundary(List<Point> list){
		Boundary result = new Boundary();
		int length = list.size()-1;
		
		List<Point> cloneList =  cloneList(list);
		
		Collections.sort(cloneList,lngsort);
		result.setMinLng(cloneList.get(0).getLng());
		result.setMaxLng(cloneList.get(length).getLng());
		
		Collections.sort(cloneList,latsort);
		result.setMinLat(cloneList.get(0).getLat());
		result.setMaxLat(cloneList.get(length).getLat());
		
		return result;
	}
	
	static boolean isOnLine(Integer first, Integer second, Edge edge, int type){
		Integer edgeStart=0;
		Integer edgeEnd =0;
		Integer max =0;
		Integer min = 0;

		if(type==2){
			edgeStart = edge.getStartPoint().getLng();
			edgeEnd = edge.getEndPoint().getLng();
			if(edgeStart<=edgeEnd){
				max = edgeEnd;
				min = edgeStart;
			}else{
				max = edgeStart;
				min = edgeEnd;
			}
		}else if(type==1){
			edgeStart = edge.getStartPoint().getLat();
			edgeEnd = edge.getEndPoint().getLat();
			if(edgeStart<=edgeEnd){
				max = edgeEnd;
				min = edgeStart;
			}else{
				max = edgeStart;
				min = edgeEnd;
			}
		}
		
	    if(first >= min && first <= max  && second >=min && second <= max){
	    	return true;
	    }
		
		return false;
	}

	static List<String> outLineCellHandle(List<String> list){
		List<String>  result = new ArrayList<String>();
		Map<Integer, List<Integer>>  boundaryMap = boundaryCell(list);
				

		SortedSet<Integer> lngSet = new TreeSet(boundaryMap.keySet());
		
		Iterator<Integer> lngIterator= lngSet.iterator();
		
		Integer fisrtLng  = lngSet.first();
		Integer endLng  = lngSet.last();
		
		List<Integer>  lineboundaryList = null;
		List<Integer>  lngDataList = null;
		
		OutLine outLine = new OutLine();
		
		List<OutLineEdge> lastLinePositiveData = new ArrayList<OutLineEdge>();
		
		while(lngIterator.hasNext()){
			Integer key = lngIterator.next();
			lineboundaryList = boundaryMap.get(key);
			System.out.println(key +":" + lineboundaryList);
			
			lngDataList =  lineData.get(key);
			
		}
		

		
		System.out.println("keys=" );
		
		return result;
	}
	
	
    private OutLine covertFromList2OutLine( List<Integer> list){
    	
    	return null;
    }
    /**
     * 
     * @param lineboundaryList
     * @param displayList2
     */
	private static void dealLine(Integer lng , List<OutLineEdge> lastLinePositiveData,
			List<OutLineEdge> positiveData,List<OutLineEdge> reverseData){
//		
//		List<Integer> lineCells = lineData.get(lng);
//		
//		Integer firstPoint = lineCells.get(0);
//	    Integer lat = firstPoint.getLat();
//	    Integer max = null;
//	    int size = lineCells.size();
//	    OutLine positiveOutLine = null;
//	    OutLine reverseOutLine = null;
//	    List<OutLine> lastLineData = null;
//	    if(lastLinePositiveData !=null ){
//	    	lastLineData = cloneList(lastLinePositiveData);
//	    	lastLinePositiveData.clear();
//	    }
//	    
//	    if(size==1 ){
//	    	positiveOutLine = new OutLine(new Point(lng+ span, lat),new Point(lng+ span, lat + span) ,0,lng);
//	    	positiveData.add(positiveOutLine);
//	    	lastLinePositiveData.add(positiveOutLine);
//	    	reverseOutLine = new OutLine(new Point(lng, lat+span),firstPoint,1,lng);
//	    	positiveData.add(reverseOutLine);
//	    }else{
//	    	Point pairStartPoint = firstPoint;
//	    	Point tmpPoint = firstPoint;
//	    	Point pairEndPoint = null;
//	    	
//	    	for(int i=1; i< size; i++){
//	    		pairEndPoint = lineCells.get(i);
//	    		if(pairEndPoint.getLat() == tmpPoint.getLat()+span){
//	    			tmpPoint = pairEndPoint;
//	    			continue;
//	    		}else{
//	    			positiveData.add(new OutLine(pairStartPoint,new Point(lng,tmpPoint.getLat()+span),0,lng));
//	    			pairStartPoint = pairEndPoint;
//	    			tmpPoint = pairEndPoint;
//	    		}
//	    	}
//	    	if(pairStartPoint.getLat() != tmpPoint.getLat()){
//	    		positiveData.add(new OutLine(pairStartPoint,new Point(lng,tmpPoint.getLat()+span),0,lng));
//	    	}
//	    	
//	    	
//	    	
//	    }
//	    
//	    //上次正向数据为空，无需调整 反向数据
//	    if(lastLineData==null){
//		   
//	    }else{
//	    	
//	    	
//	    }
//
//	   
//	    
	    
	    


		
	}
	
	

	static Map<Integer, List<Integer>>  boundaryCell(List<String> list){
		
		Map<Integer, List<Integer>>  result = new HashMap<Integer, List<Integer>>();
		
		String[] arrStr =  null;
		Integer lng;
		Integer lat;
		String sb = null;
		boolean flag = false;
		List<Integer> tmpList = null;
		List<Integer> latList = null;
		Point tmpPoint = null;
		for(String point: list){
			arrStr = point.split("_");
			lng = new Integer(arrStr[0]);
			lat = new Integer(arrStr[1]);
			
			tmpList = lineData.get(new Integer(lng));
			
			if(tmpList ==null){
				tmpList = new ArrayList<Integer>();
				lineData.put(new Integer(lng), tmpList);
			}
			tmpList.add(lat);
			
			latList = result.get(new Integer(lng));
			if(latList ==null){
				latList = new ArrayList<Integer>();
				result.put(new Integer(lng), latList);
			}

			
			if(lat-span >=0){
				sb= new StringBuffer("").append(lng).append("_").append(lat-span).toString();
				flag = list.contains(sb);
			}else{
				flag = false;
			}
			if(!flag) {
				latList.add(lat);
				continue;
			}
			
			sb= new StringBuffer("").append(lng).append("_").append(lat+span).toString();
			flag = list.contains(sb);
			if(!flag) {
				latList.add(lat);
				continue;
			}
			
			sb= new StringBuffer("").append(lng+span).append("_").append(lat).toString();
			flag = list.contains(sb);
			
			if(!flag) {
				latList.add(lat);
				continue;
			}
			
			if(lng-span>=0){
				sb= new StringBuffer("").append((lng-span)).append("_").append(lat).toString();
				flag = list.contains(sb);
			}else{
				flag = false;
			}
			
			if(flag){
				continue;
			}
			latList.add(lat);
		}
		
		return result;
	}
	
	
	static List<Point> data2(){
		List<Point> result= new ArrayList<Point>();
//		result.add(new Point(15, 15));
//		result.add(new Point(35, 15));
//		result.add(new Point(35, 25));
//		result.add(new Point(15, 25));
		
//基准线上的测试数据		
//		result.add(new Point(10, 30));
//		result.add(new Point(10, 80));
//		result.add(new Point(20, 80));
//		result.add(new Point(20, 90));
//		result.add(new Point(60, 90));
//		result.add(new Point(60, 70));
//		result.add(new Point(70, 70));
//		result.add(new Point(70, 30));

		//基准线上多个凹凸测试数据
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
		
//		result.add(new Point(10, 30));
//		result.add(new Point(10, 80));
//		result.add(new Point(20, 80));
//		result.add(new Point(20, 60));
//		result.add(new Point(60, 60));
//		result.add(new Point(60, 70));
//		result.add(new Point(70, 70));
//		result.add(new Point(70, 30));
		
//		result.add(new Point(25, 5));
//		result.add(new Point(55, 25));
//		result.add(new Point(15, 45));
//		result.add(new Point(35, 30));
//		result.add(new Point(5, 30));
		
//		result.add(new Point(25, 5));
//		result.add(new Point(55, 25));
//		result.add(new Point(15, 45));
//		result.add(new Point(35, 22));
//		result.add(new Point(5, 22));
		
		
//		result.add(new Point(64, 31));
//		result.add(new Point(40, 50));
//		result.add(new Point(65, 65));
//		result.add(new Point(32, 85));
//		result.add(new Point(79, 75));
		
//		result.add(new Point(35, 10));
//		result.add(new Point(55, 49));
//		result.add(new Point(25, 80));
//		result.add(new Point(45, 125));
//		result.add(new Point(5, 135));
//		result.add(new Point(15, 85));
//		result.add(new Point(35, 35));
//		
//		result.add(new Point(10, 10));
//		result.add(new Point(20, 10));
//		result.add(new Point(20, 20));
//		result.add(new Point(10, 20));
		
//		result.add(new Point(1, 1));
//		result.add(new Point(5, 1));
//		result.add(new Point(5, 5));
//		result.add(new Point(1, 5));
	
		return result;
	}
	
	public static void main(String[] args) {
		 
		Set<String> lngLatSet = split(data2());
		List<String> lngLatList = new ArrayList<String>(lngLatSet);
		Collections.sort(lngLatList, stringSort);
		System.out.println("split=");
		System.out.println(lngLatList); 
		
		System.out.println("displayCell=");
	
		List<String> displayList = outLineCellHandle(lngLatList);
		
		System.out.println(displayList); 
		
		
		System.out.println("raw data list =" + lineData);
		
	}
	

}
