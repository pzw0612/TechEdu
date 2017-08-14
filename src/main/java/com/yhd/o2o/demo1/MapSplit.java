/**
 * 
 */
package com.yhd.o2o.demo1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.yihaodian.common.JSONUtil;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2015-12-3 上午9:18:56
 */
public class MapSplit {

	private static long LNG_LEN = 200; //切割维度200 地图单位
	private static long LAT_LEN = 100; //切割维度100地图单位
	
	public static void main(String[] args) {
		MapReqData data = generateData();
		if(data==null || CollectionUtils.isEmpty(data.getLnglat())){
			System.out.println("无数据");
			return ;
		}
		List<LngLatInfo>  list = data.getLnglat();
		if(list.size()<3){
			System.out.println("数据异常,数据必须大于等于3");
			return;
		}
		Set<MapCell> result = new HashSet<MapCell>();
		
		
		System.out.println(result);
	}
	
	
	static Set<MapCell>  filterMethod(List<LngLatInfo>  list){
		Set<MapCell> result = new HashSet<MapCell>();
		
		LngLatInfo lngLatInfo  = null;
		for(int i=0; i < list.size();++i){
		    lngLatInfo = list.get(i);
			result.add(getBaseCell(lngLatInfo));
		}
		
		return result;
	}
	static Set<MapCell>  recursiveMethod(List<LngLatInfo>  list){
		Set<MapCell> result = new HashSet<MapCell>();
		Set<MapCell> tmp = new HashSet<MapCell>();
		
		LngLatInfo fst = list.get(0);
		LngLatInfo sec = list.get(1);
		LngLatInfo thd = list.get(2);
		
		if(list.size() ==3){
			result=split(fst, sec,thd);
			return null;
		}
		
		for(int i=0; i < list.size()-2;++i){
			sec = list.get(i+1);
			thd = list.get(i+2);
			tmp=split(fst,sec,thd);
			result.addAll(tmp);
		}
		return result;
	}
	
	
	public static Set<MapCell> split(LngLatInfo fst, LngLatInfo sec,LngLatInfo thd){
		Set<MapCell> result = new HashSet<MapCell>();
		result.add(getBaseCell(fst));
		result.add(getBaseCell(sec));
		result.add(getBaseCell(thd));
		
		
		return result;
	}
	
	private static MapCell getBaseCell(LngLatInfo lngLat){
		
		long lng = lngLat.getLng();
		long lat= lngLat.getLat();
		MapCell result = null;
		
	    if(lng % LNG_LEN ==0 && lat % LAT_LEN ==0 ){ //右下角
	    	result = new MapCell(lng-LNG_LEN, lat+ LAT_LEN,lng,lat);
	    	return result;
	    }
	    
	    Long tmp = null;
        if(lng % LNG_LEN ==0 && lat % LAT_LEN !=0){ //维度在基准线上， 经度不在经度线上
        	tmp = getBaseNum(lat,"1",null);
	    	result = new MapCell(lng-LNG_LEN, tmp+ LAT_LEN,lng,tmp);
	    	return result;
        }
        
        if(lng % LNG_LEN !=0 && lat % LAT_LEN ==0){ //维度不在基准线上， 经度在经度线上
        	tmp = getBaseNum(lng,"2",null);
        	result = new MapCell(tmp-LNG_LEN, lat+ LAT_LEN,tmp,lat);
        	return result;
        }
        
        //维度不在基准线上， 经度也不在经度线上
       long tmpLng = getBaseNum(lng,"2",null);
       long tmpLat = getBaseNum(lat,"1",null);

       result = new MapCell(tmpLng-LNG_LEN, tmpLat+ LAT_LEN,tmpLng,tmpLat);
       
		return result;
	}
	
	/**
	 * 获取右下角基准点经度
	 * 
//	 * @author pangzhiwang
//	 * @date 2015-12-3
//	 * @param point (经度或者维度）
//	 * @param type   1：沿经度， 2： 沿维度
//	 * @param direct 1：向上(或向右），2：向下（向左）
//	 * @return
	 */
	private static Long getBaseNum(long point, String type, String direct){
		if("1".equals(type)){
			for(long i = point; i>=0; --i){
				if(i % LAT_LEN ==0){
					return i;
				}
			}
		}
		if("2".equals(type)){
			for(long i = point; true; ++i){
				if(i % LNG_LEN ==0){
					return i;
				}
			}
		}

		return 0l;
	}
	
	public static  MapReqData generateData(){
		MapReqData result = null;
		
		String jsonStr = "{type: 'Polyline', name: '', desc: '', strokeWeight: 5, strokeColor: '#19A4EB', strokeOpacity: 0.8,"
				  + "lnglat: [{lng: 121623459, lat: 31208397}," 
				            + "{lng: 121651268, lat: 31181085}," 
				            + "{lng: 12158432, lat: 31188721}," 
				            + "{lng: 121620369, lat: 31190777}," 
				            + "{lng: 121623802, lat: 31208397}]}";
		result=(MapReqData) JSONUtil.toObject(jsonStr, MapReqData.class);

//		if(result!=null && !CollectionUtils.isEmpty(result.getLnglat())){
//			for(LngLatInfo info: result.getLnglat()){
//				info.setLat(info.getLat()*100000);
//				info.setLng(info.getLng()*100000);
//			}
//		}
		
		return result;
	}
}






