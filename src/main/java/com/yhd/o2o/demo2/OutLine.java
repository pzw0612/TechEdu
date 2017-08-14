/**
 * 
 */
package com.yhd.o2o.demo2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-1-26 下午10:30:07
 */
public class OutLine {

	//上沿
	private List<OutLineEdge> upDataList = new ArrayList<OutLineEdge>();
	
	//下沿
	private List<OutLineEdge> underDataList = new ArrayList<OutLineEdge>();
	
	
	public OutLine addOutLineEdge4up(OutLineEdge outLineEdge){
		upDataList.add(outLineEdge);
		return this;
	}
	public OutLine addOutLineEdge4under(OutLineEdge outLineEdge){
		underDataList.add(outLineEdge);
		return this;
	}
	
	public OutLine deleteOutLineEdge4up(OutLineEdge outLineEdge){
		upDataList.remove(outLineEdge);
		return this;
	}
	public OutLine deleteOutLineEdge4upder(OutLineEdge outLineEdge){
		upDataList.remove(outLineEdge);
		return this;
	}
	
	public List<OutLineEdge> getUpDataList() {
		return upDataList;
	}
	public void setUpDataList(List<OutLineEdge> upDataList) {
		this.upDataList = upDataList;
	}
	public List<OutLineEdge> getUnderDataList() {
		return underDataList;
	}
	public void setUnderDataList(List<OutLineEdge> reverseList) {
		this.underDataList = reverseList;
	}
	
	public void  transform(Integer lng, List<Integer> lngData){
		 List<OutLineEdge>  edgeList = null;
		 //数据转化
		 edgeList = dataConvert(lng,lngData);
		 
		 OutLineEdge tmpEdge = null;
		 List<OutLineEdge> lastUpDataList = new ArrayList<OutLineEdge>();
		 if(underDataList.size() ==0){
			 for(OutLineEdge edge: edgeList){
				 underDataList.add(edge);
				 List<Integer> tmpList = Utils.cloneList(edge.getRawData());
				 tmpEdge = new OutLineEdge(tmpList,lng+Constants.span);
				 upDataList.add(tmpEdge);
			 }
			
			 return ;
		 }
         /**
          * 1.取出过去上沿集数据
          * 2.清除过去上沿数据，加入新的上沿数据
          * 3.将上一次的上沿数据集与当前下沿数据比较，将差异化的数据 加入下沿数据集
          */
		 
			//1. 取出过去上沿集数据
			 lastUpDataList = Utils.cloneList(upDataList);
			
			//2. 清除过去上沿数据，加入新的上沿数据
			upDataList =  new ArrayList<OutLineEdge>(edgeList.size());
			
			for(OutLineEdge edge: edgeList){
				tmpEdge = new OutLineEdge(edge.getRawData(),lng+ Constants.span);
				//最新单元格上沿数据
				upDataList.add(tmpEdge);
			}
	
		
			//将上一次的上沿数据集与当前下沿数据比较，将差异化的数据 加入下沿数据集
			handleUnderData(lastUpDataList, edgeList);
		
		 
	}
	
	/** 
	 * @param lastUpDataList
	 * @param edgeList
	 */
	
	private void handleUnderData(List<OutLineEdge> lastUpDataList,List<OutLineEdge> edgeList) {
		
		Integer lng = lastUpDataList.get(0).getLng();
		
		List<Integer> tmpLastUpDataList = new ArrayList<Integer>();
		List<Integer> tmpEdgeList = new ArrayList<Integer>();
		
		for(OutLineEdge lastEdge: lastUpDataList){
			for(Integer lat:lastEdge.getRawData()){
				if(!tmpLastUpDataList.contains(lat)){
					tmpLastUpDataList.add(lat);
				}
			}
		}
		for(OutLineEdge lastEdge: edgeList){
			for(Integer lat:lastEdge.getRawData()){
				if(!tmpEdgeList.contains(lat)){
					tmpEdgeList.add(lat);
				}
			}
		}
		
		Collections.sort(tmpLastUpDataList,Utils.integerSort);
		Collections.sort(tmpEdgeList,Utils.integerSort);
		
		@SuppressWarnings("unchecked")
		List<Integer> diff1 = (List<Integer>)CollectionUtils.subtract(tmpLastUpDataList, tmpEdgeList);
		@SuppressWarnings("unchecked")
		List<Integer> diff2 = (List<Integer>)CollectionUtils.subtract(tmpEdgeList, tmpLastUpDataList);
        
		Collections.sort(diff1,Utils.integerSort);
		Collections.sort(diff2,Utils.integerSort);
		
		Integer size1 = diff1.size();
		Integer size2 = diff2.size();
		if(size1==0 && size2==0){
			return;
		}
		
		List<OutLineEdge> diffOutLineEdge = null;
		
		Integer start  = null;
		Integer end = null;
		Integer startLast  = null;
		Integer endLast = null;
		
		//上沿只是下沿的子集
		if(size1 >0 ){
			diffOutLineEdge = splitData(lng,diff1);
			for(OutLineEdge tmp:diffOutLineEdge){
				 start = tmp.getRawData().get(0);
				 end = tmp.getRawData().get(tmp.getRawData().size()-1);
				 startLast = tmpLastUpDataList.get(0);
				 endLast = tmpLastUpDataList.get(tmpLastUpDataList.size()-1);
				 
//				System.out.println("start lat=" + start);
//				System.out.println("end lat=" + end);
//				System.out.println("tmpLastUpDataList start =" +startLast);
//				System.out.println("tmpLastUpDataList end =" + endLast);
					
//				 if(start == tmpLastUpDataList.get(0)){
//					 tmp.getRawData().add(end+Constants.span);
//				 }else if(end ==  tmpLastUpDataList.get(tmpLastUpDataList.size()-1)){
//					 tmp.getRawData().add(0, start-Constants.span);
//				 }else{
//					 tmp.getRawData().add(end+Constants.span);
//					 tmp.getRawData().add(0, start-Constants.span);
//				 }
	
			}
			underDataList.addAll(diffOutLineEdge);
		}
		
		//下沿只是上沿的子集
		if(size2 > 0){
			diffOutLineEdge = splitData(lng,diff2);
			for(OutLineEdge tmp:diffOutLineEdge){
				 start = tmp.getRawData().get(0);
				 end = tmp.getRawData().get(tmp.getRawData().size()-1);
				 
				 startLast = tmpLastUpDataList.get(0);
				 endLast = tmpLastUpDataList.get(tmpLastUpDataList.size()-1);
				
				System.out.println("start lat=" + start);
				System.out.println("end lat=" + end);
				System.out.println("tmpLastUpDataList start =" + startLast);
				System.out.println("tmpLastUpDataList end =" + endLast);
					
//				if(end <= startLast){
//					tmp.getRawData().add(end+Constants.span);
//				}else if(start <= startLast && end >= startLast && end <= endLast){
//					tmp.getRawData().add(end+Constants.span);
//				}else if(startLast <= start && startLast<= end && end <= endLast){
//					 tmp.getRawData().add(0, start-Constants.span);
//					 tmp.getRawData().add(end+Constants.span);
//				}else if(startLast <= start && start <= endLast &&  endLast <= end){
//					 tmp.getRawData().add(0, start-Constants.span);
//					 tmp.getRawData().add(end+Constants.span);
//				}else if(start >= endLast){
//					 tmp.getRawData().add(end+Constants.span);
//					
//				}
				
	
			}
			underDataList.addAll(diffOutLineEdge);
		}
	
		
	}

	
	/**
	 * 将数据以连续的数据分组,如1,2,3, 7,8 分为 1,2,3 一组, 7,8 一组
	 * @param lng
	 * @param lngData
	 * @return
	 */
	private List<OutLineEdge> splitData(Integer lng, List<Integer> lngData){

		List<OutLineEdge> result = new ArrayList<OutLineEdge>();

		Integer end =null;
		int length  = lngData.size();
		OutLineEdge  edge = null;
		
		Integer first = lngData.get(0);
		Integer tmp = first;
		edge = new OutLineEdge();
		edge.addData(first);
		edge.setLng(lng);
		result.add(edge);
		
		if(length==1){
			return result;
		}
		
		for(int i=1; i< length; i++){
			 end = lngData.get(i);
			 if(tmp+Constants.span == end){
				 tmp = end;
				 edge.addData(tmp);
				 continue;
			 }else{
				first = end;
				tmp = end;
				edge = new OutLineEdge();
				edge.addData(first);
				edge.setLng(lng);
				result.add(edge);
			 }
		}
		
		return result;
	
	}
	
	/**
	 * 将数据格式化
	 * @param lng
	 * @param lngData
	 * @return
	 */
	private List<OutLineEdge> dataConvert(Integer lng, List<Integer> lngData){
		List<OutLineEdge> result = new ArrayList<OutLineEdge>();

		Integer end =null;
		int length  = lngData.size();
		
		OutLineEdge  edge = null;
		
		Integer first = lngData.get(0);
		Integer tmp = first;
		
		edge = new OutLineEdge();
		edge.addData(first);
		edge.setLng(lng);
		result.add(edge);
		
		if(length==1){
			return result;
		}
		
		for(int i=1; i< length; i++){
			 end = lngData.get(i);
			 if(tmp+Constants.span == end){
				 tmp = end;
				 edge.addData(tmp);
				 continue;
			 }else{
				//edge.addData(tmp+Constants.span);
				first = end;
				tmp = end;
				edge = new OutLineEdge();
				edge.addData(first);
				edge.setLng(lng);
				result.add(edge);
			 }
		}
		//edge.addData(tmp+Constants.span);

		
		return result;
	}
	
	public static void main(String[] args) {
		OutLine outline = new OutLine();
		List<Integer> list = new ArrayList<Integer>();
		list.add(20);
		list.add(30);
		list.add(40);
		list.add(60);
		list.add(70);
		
		list.add(100);
		list.add(110);
//		
//		List<Integer> list2 = new ArrayList<Integer>();
//		list2.add(20);
//		list2.add(30);
//		list2.add(40);
//		list2.add(60);
//		list2.add(70);
//		
//		list2.add(100);
//		list2.add(110);
//		list2.add(120);
//		list2.add(130);
//		
//		List<Integer> intersectionList =(List<Integer>)CollectionUtils.intersection(list2, list);
//		List<Integer> subtractList =(List<Integer>)CollectionUtils.subtract(list2, list);
//		List<Integer> subtractList2 =(List<Integer>)CollectionUtils.subtract(list, list);
//		
//		
//		System.out.println("compareList=" + intersectionList);
//		System.out.println("subtractList=" + subtractList);
//		System.out.println("subtractList2=" + subtractList2);
//		
//		
//		if(list.containsAll(list2)){
//			System.out.println("list.containsAll(list2)=" + true);
//		}else{
//			System.out.println("list.containsAll(list2)=" + false);
//		}
		
		
		//outline.transform(10, list);
		
//		System.out.println("positive list=" + outline.getPositiveList());
//		System.out.println("reverse list=" + outline.getReverseList());
		
		
		System.out.println(outline.dataConvert(10,list));
	}
	
	
}
