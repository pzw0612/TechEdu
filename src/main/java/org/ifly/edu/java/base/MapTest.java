/**
 * 
 */
package org.ifly.edu.java.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;


/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author pangzhiwang
 * @date 2015-10-25 下午3:19:25
 */
public class MapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map<String,String> map = new HashMap<String,String>();
		
		
		map.put("key1", "3");
		map.put("key1", null);
		
		map.put("key2", null);
		map.put("key2", "8");
		
		for(Entry<?, ?> entry : map.entrySet()){
			System.out.println("key:value=" + entry.getKey() + ":" + entry.getValue());
		}
		
		System.out.println("map=" + map);

		Map<String,String> map2 = MapUtils.synchronizedMap(map);
		System.out.println("map2=" + map2);
		
		
	}

}
