/**
 * 
 */
package com.yhd.o2o.demo2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

/**
 *  切割任意多边形
 * @Description: 
 * @author pangzhiwang
 * @date 2016-1-19 下午1:37:00
 */
public class WnPnPolySplit_160226 {


	//切割后单元格
	private static Set<String>  cellSet = new HashSet<String>();

	
	private static Map<Integer,List<Integer>>  lineData = new HashMap<Integer,List<Integer>> ();
	
	
	static List<Point> pointList  = new ArrayList<Point>();
	
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
		
		//2. 生成多变形的边（y= kx + b) 的 k 和b
		List<Edge> edgeList = genEdgesByVertix(list);

		//3.计算经度上下界的各线\计算纬度上下界基准线
//		BaseLines lines = genBaseLine(boundary,edgeList);
		BaseLines lines = genBaseLine(boundary);
		
		
		//span 太大问题处理
		if(lines.getLatLines().size()==0 && lines.getLngLines().size()==0){
			dealSmallRange(boundary,cellSet);
			return cellSet;
		}
		
		//4. 求水平基准线与边相交的交点集合，并帅选出单元格
		horizionSplitCell(lines,edgeList,cellSet);
		
		//5. 求垂直基准线与边相交的交点集合，，并帅选出单元格
		verticalSplitCell(lines,edgeList,cellSet);
		
		
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
			 if(minLng % Constants.span ==0){
				 break;
			 }
			 --minLng;
		 }
		 while(minLat>=0){
			 if(minLat % Constants.span ==0){
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
     *  两个交点的连线恰好是一条边的处理方式
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

		Integer fstData = null;
		Integer secData = null;
		Integer tmpLng = null;
		Integer tmpLat = null;
		if(directType==1){
			Collections.sort(intersectionList,Utils.latsort);
			tmpLng = intersectionList.get(0).getLng();
			for(int i=0;i< size/2; i=i+1){
				fstData = intersectionList.get(2*i).getLat();
				secData = intersectionList.get(2*i+1).getLat();
				if(fstData.intValue() == secData.intValue()){
					continue;
				}
				
				boolean flg = false;
                for(Edge edge: edgeList){

					if(edge.getLineType() ==3  &&  edge.getContValue() ==tmpLng){
						if(isOnLine(fstData, secData,edge,1)){
							flg = true;
							break;
						}
					}

				}
                if(flg) continue;
                
			    while(fstData > 0){
			    	if(fstData % Constants.span ==0){
			    		break;
			    	}
			    	
			    	--fstData;
			    }
			    
			   while(fstData < secData){
				   cellSet.add("" + tmpLng+"_"+ fstData);
				  
					   if(tmpLng- Constants.span >=0){
						   cellSet.add("" + (tmpLng-Constants.span)+"_"+ fstData);
					   }

				   fstData = fstData + Constants.span;
			   }
			    
			}
			return;
		}
		
		if(directType==2){
			Collections.sort(intersectionList,Utils.lngsort);
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
			    	if(fstData % Constants.span ==0){
			    		break;
			    	}
			    	
			    	--fstData;
			    }
			   while(fstData < secData){
				   cellSet.add("" + fstData+"_"+ tmpLat);
				   if(tmpLat-Constants.span >=0){
					   cellSet.add("" + fstData+"_"+ (tmpLat-Constants.span));
				   } 

				   fstData = fstData + Constants.span;
			   }
			    
			}
			return;
		}
	}

	private static Integer max(Integer first, Integer second){
		if(first >= second) return first;
		return second;
	}
	private static Integer min(Integer first, Integer second){
		if(first <= second) return first;
		return second;
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
		Integer lng = minLng+1;
		while(lng < maxLng){
			if(lng % Constants.span ==0){
				result.addLngLines(lng);
			}
			++lng;
		}
		System.out.println("lng lines = " + result.getLngLines());
		
		//纬度基准线
		Integer minLat = boundary.getMinLat();
		Integer maxLat = boundary.getMaxLat();
		Integer lat = minLat+1;
		while(lat < maxLat){
			if(lat % Constants.span ==0){
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
		List<Point> totalList = Utils.cloneList(list);
		
		totalList.add(list.get(0));
		
		for(int i= 0; i< length; i++){
			result.add(new Edge(totalList.get(i),totalList.get(i+1)));
		}
		System.out.println("Edge lines=" + result);
		return result;
	}

	

	

	
	static Boundary calcBoundary(List<Point> list){
		Boundary result = new Boundary();
		int length = list.size()-1;
		
		List<Point> cloneList =  Utils.cloneList(list);
		
		Collections.sort(cloneList,Utils.lngsort);
		result.setMinLng(cloneList.get(0).getLng());
		result.setMaxLng(cloneList.get(length).getLng());
		
		Collections.sort(cloneList,Utils.latsort);
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

	/**
	 * 计算轮廓线条
	 * @param list
	 * @return
	 */
	public static String outLineCellHandle(List<String> list){
		
		Map<Integer, List<Integer>>  boundaryMap = boundaryCell(list);
		
		SortedSet<Integer> lngSet = new TreeSet(boundaryMap.keySet());
		
		List<Integer>  lineboundaryList = null;
		OutLine outLine = new OutLine();
		
		Iterator<Integer> lngIterator= lngSet.iterator();
		while(lngIterator.hasNext()){
			Integer key = lngIterator.next();
			lineboundaryList = lineData.get(key);
			Collections.sort(lineboundaryList,Utils.integerSort);
			System.out.println(key +":" + lineboundaryList);
			
			outLine.transform(key, lineboundaryList);
		}
		
			for(OutLineEdge egde:  outLine.getUpDataList()){
				egde.addData(egde.getRawData().get(egde.getRawData().size()-1) + Constants.span);
			}

			for(OutLineEdge egde:  outLine.getUnderDataList()){
				egde.addData(egde.getRawData().get(egde.getRawData().size()-1) + Constants.span);
			}

	
		// 上沿有多个分支情况
        if(outLine.getUpDataList().size()>1){
        	for(int i=1; i< outLine.getUpDataList().size(); i++){
        		outLine.getUnderDataList().add(outLine.getUpDataList().get(i));
        	}
        }
        
        // 根据各边信息推断各个顶点信息
        Stack<OutLineEdge> vertex = genVertex(outLine.getUpDataList().get(0),outLine.getUnderDataList());
        
		return printVertex(vertex);
	}

	
	/**
	 * 取各顶点
	 * @param egde
	 * @param list
	 * @return
	 */
	static Stack<OutLineEdge>  genVertex(OutLineEdge egde, List<OutLineEdge>  list){
		
		Stack<OutLineEdge>  stack = new Stack<OutLineEdge>();
		egde.setStartNodeFlg(true);
		stack.add(egde);
		
		Stack<OutLineEdge> relativeEdgeStack = null;
		OutLineEdge stackEdge = null;
		OutLineEdge tmpEdge =null;
		
		int size = list.size();
		
		//可能相邻边的映射
		Map<OutLineEdge,Stack<OutLineEdge>> relativeEdgeMap = new HashMap<OutLineEdge,Stack<OutLineEdge>>();
		
		while(!stack.empty() && stack.size()- size !=1){
			
		     stackEdge = stack.peek();
			 
			// 寻找相邻边
		     relativeEdgeStack = relativeEdgeMap.get(stackEdge);
		     
		     //validateGoneRelativeEdge(relativeEdgeStack,stackEdge);
		     
			if(relativeEdgeStack==null){
				relativeEdgeStack= findRelativeOutLineEdge(stackEdge,list);
			}
				
			if(relativeEdgeStack.size() >0){
				relativeEdgeMap.put(stackEdge, relativeEdgeStack);
				OutLineEdge relativeEdge = relativeEdgeStack.pop();
				
				if (stackEdge.isEndNodeFlg()) {
					if (stackEdge.getStartLng().equals(relativeEdge.getEndLng())) {
						relativeEdge.setEndNodeFlg(true);
						stack.add(relativeEdge);
					} else if (stackEdge.getStartLng().equals(relativeEdge.getStartLng())) {
						relativeEdge.setStartNodeFlg(true);
						stack.add(relativeEdge);
					}
				} else {
					if (stackEdge.getEndLng().equals(relativeEdge.getEndLng())) {
						relativeEdge.setEndNodeFlg(true);
						stack.add(relativeEdge);
						
					} else if (stackEdge.getEndLng().equals(relativeEdge.getStartLng())) {
						relativeEdge.setStartNodeFlg(true);
						stack.add(relativeEdge);
					}
				}
				
			}else{
				
			    if(egde !=stackEdge &&  egde.getStartLng() .equals(stackEdge.getStartLng()) 
			    		&& stackEdge.isEndNodeFlg() && stack.size()-size ==1 ){
			    	break;
			    } 
			    
			    if(egde !=stackEdge && egde.getStartLng().equals(stackEdge.getEndLng()) 
			    		&& stackEdge.isStartNodeFlg()  && stack.size()-size ==1){
			    	break;
			    }
			    
			   stack.pop();
			   
			    if(stack.empty()){
                    break;
                } 
			    
			    tmpEdge =null;
			    for(OutLineEdge tmpEdge2 :list){
			    	if(tmpEdge2.getLng() .equals(stackEdge.getLng()) && 
			    			tmpEdge2.getEndLng().equals(stackEdge.getEndLng())
			    			&& tmpEdge2.getStartLng().equals(stackEdge.getStartLng())){
			    		tmpEdge = tmpEdge2;
			    		break;
			    	}
			    }
			    if(tmpEdge !=null){
				    tmpEdge.setStartNodeFlg(false);
				    tmpEdge.setEndNodeFlg(false);
			    }
			    
			    relativeEdgeStack = null;
			    relativeEdgeMap.remove(stackEdge);
			    
			    
			}
		}
		
		return stack;
	}
	
	static String printVertex(Stack<OutLineEdge>  stack){
		
		StringBuffer result = new StringBuffer();
		OutLineEdge tmpEdge= null;
		while(!stack.isEmpty()){
			tmpEdge = stack.pop();
			if(result.length()==0){
				if(tmpEdge.isStartNodeFlg()){
					result.append("(").append(tmpEdge.getLng()).append(",").append(tmpEdge.getEndLng()).append(")");
					result.append("(").append(tmpEdge.getLng()).append(",").append(tmpEdge.getStartLng()).append(")");
					
				}else{
					result.append("(").append(tmpEdge.getLng()).append(",").append(tmpEdge.getStartLng()).append(")");
					result.append("(").append(tmpEdge.getLng()).append(",").append(tmpEdge.getEndLng()).append(")");
				}
			}else{
				if(tmpEdge.isStartNodeFlg()){
					result.append("(").append(tmpEdge.getLng()).append(",").append(tmpEdge.getEndLng()).append(")");
					result.append("(").append(tmpEdge.getLng()).append(",").append(tmpEdge.getStartLng()).append(")");
					
				}else{
					result.append("(").append(tmpEdge.getLng()).append(",").append(tmpEdge.getStartLng()).append(")");
					result.append("(").append(tmpEdge.getLng()).append(",").append(tmpEdge.getEndLng()).append(")");
				}
			}
			
		}
		
		return result.toString();
	}
/** 
	 * @param relativeEdgeStack
	 * @param stackEdge
	 */
	
	private static void validateGoneRelativeEdge(Stack<OutLineEdge> relativeEdgeStack, OutLineEdge stackEdge) {
		  if(relativeEdgeStack == null){
			  return ;
		  }
		 OutLineEdge tmp= null;
		 boolean flag = false;
		 while(!relativeEdgeStack.isEmpty()){
			 tmp = relativeEdgeStack.peek();
			 if(tmp.isEndNodeFlg() || tmp.isStartNodeFlg()){
				 flag = true;
				 break;
			 }
			 if(stackEdge.isStartNodeFlg()){
				 if(!stackEdge.getEndLng().equals(tmp.getEndLng()) && !stackEdge.getEndLng().equals(tmp.getStartLng())){
					 flag = true;
					 break;
				 }
			 }else{
				 if(!stackEdge.getStartLng().equals(tmp.getEndLng()) && !stackEdge.getStartLng().equals(tmp.getStartLng())){
					 flag = true;
					 break;
				 }
			 }
		 }
		 if(flag){
			 relativeEdgeStack = null;
		 }
	}

	
	/**
	 * 寻找相邻边
	 * @param egde
	 * @param list
	 * @return
	 */
     static Stack<OutLineEdge> findRelativeOutLineEdge(OutLineEdge egde, List<OutLineEdge>  list){
    	 
    	 Stack<OutLineEdge> stack = new Stack<OutLineEdge>();

    	 OutLineEdge  edge = null;
    	 for(OutLineEdge iteEdge: list){
				if(!iteEdge.isEndNodeFlg() && !iteEdge.isStartNodeFlg()){
					if(egde.isEndNodeFlg()){
						if(egde.getStartLng().equals(iteEdge.getEndLng())){
						   // edge =Utils.cloneBean(iteEdge);
						    stack.add(iteEdge);
						}else if(egde.getStartLng().equals(iteEdge.getStartLng())){
						   // edge =Utils.cloneBean(iteEdge);
						    stack.add(iteEdge);
						}
					}else{
						if(egde.getEndLng().equals(iteEdge.getEndLng())){
						  //  edge =Utils.cloneBean(iteEdge);
						    stack.add(iteEdge);
						}else if(egde.getEndLng().equals(iteEdge.getStartLng())){
						    //edge =Utils.cloneBean(iteEdge);
						    stack.add(iteEdge);
						}
					}
				}
			}
    	 
    	 return stack;
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

			
			if(lat-Constants.span >=0){
				sb= new StringBuffer("").append(lng).append("_").append(lat-Constants.span).toString();
				flag = list.contains(sb);
			}else{
				flag = false;
			}
			if(!flag) {
				latList.add(lat);
				continue;
			}
			
			sb= new StringBuffer("").append(lng).append("_").append(lat+Constants.span).toString();
			flag = list.contains(sb);
			if(!flag) {
				latList.add(lat);
				continue;
			}
			
			sb= new StringBuffer("").append(lng+Constants.span).append("_").append(lat).toString();
			flag = list.contains(sb);
			
			if(!flag) {
				latList.add(lat);
				continue;
			}
			
			if(lng-Constants.span>=0){
				sb= new StringBuffer("").append((lng-Constants.span)).append("_").append(lat).toString();
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
	

	
	
	
	public static void main(String[] args) {
		 
		Set<String> lngLatSet = split(Data.getInterSectionData());
		List<String> lngLatList = new ArrayList<String>(lngLatSet);
		Collections.sort(lngLatList, Utils.stringSort);
		System.out.println("split=");
		System.out.println(lngLatList); 
		
	
		String outLine = outLineCellHandle(lngLatList);
		
		System.out.println("outLine=" + outLine); 
		
		
	}
	

}
