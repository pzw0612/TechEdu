/**
 * 
 */
package org.ifly.edu.java.set;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-1-25 下午5:57:47
 */
public class OrderSetTest {
	 public static void main(String[] args)  {
	        SortedSet<String> allSet=new TreeSet<String>();
	        allSet.add("A");
	        allSet.add("B");
	        allSet.add("B");
	        allSet.add("B");
	        allSet.add("C");
	        allSet.add("D");
	        allSet.add("E");
	        System.out.println("第一个元素："+allSet.first());
	        System.out.println("第一个元素："+allSet.first());
	        System.out.println("最后一个元素："+allSet.last());
	        System.out.println("headSet元素："+allSet.headSet("C"));//返回从第一个元素到指定元素的集合
	        System.out.println("tailSet元素："+allSet.tailSet("C"));    //返回从指定元素到最后
	        System.out.println("subSet元素："+allSet.subSet("B", "D"));//指定区间元素
	        
	        Iterator it= allSet.iterator();
	        
	        while(it.hasNext()){
	        	System.out.println(it.next());
	        }
	        
	    }
}

