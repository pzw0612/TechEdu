package org.ifly.edu.java.base;

import java.util.Collections;
import java.util.LinkedList;

/**
 * 
 * @author pangzhiwang
 * @date 2016-10-31
 */
public class CollectionsDemo {
	   public static void main(String args[]) {  
		      // create Linked List
		      LinkedList list = new LinkedList(); 
		      
		      // populate list
		      list.add("g1");  
		      list.add("g1");  
		      list.add("g1");  
		      list.add("g1");  
		      list.add("g1");  
		      list.add("g1");  
		      list.add("g1");  
		      list.add("g1");  
		      list.add("g2");  
		      list.add("g2");  
		      list.add("g2");  
		      list.add("g2");  
		      list.add("g2");  
		      list.add("g2");  
		      list.add("g2");  
		      list.add("g2");  

		      
		      System.out.println("List before shuffle: "+list);   

		      // shuffle the list
		      Collections.shuffle(list);  
			  
		      System.out.println("List after shuffle: "+list);
		   }
}
