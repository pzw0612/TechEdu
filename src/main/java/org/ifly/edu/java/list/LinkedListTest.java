package org.ifly.edu.java.list;

import java.util.ArrayList;
import java.util.List;

public class LinkedListTest {
	   public static void main(String[] a) {  
		   testObj();
		  } 
	   
	   
	   public static void test() {  
		   List<List<String>> list = new ArrayList<List<String>>();  
		   List<String> nearlist = new ArrayList<String>();  
		   list.add(nearlist);
		   
		   nearlist.add("A");  
		   nearlist.add("B");  
		   nearlist.add("C"); 
		   System.out.println(list.toString());
		   System.out.println("--------------------------");
		   nearlist = null;
		   System.out.println(list.toString());
		  
		  } 
	   
	   public static void testObj() {  
		   List<List<Object>> list = new ArrayList<List<Object>>();  
		   
			   List<Object> sublist = new ArrayList<Object>();  
			   list.add(sublist);
			   sublist.add("A");  
			   sublist.add("B");     
			   sublist.add("C"); 
			   System.out.println(list.toString());
			   
			   System.out.println("--------------------------");
			   sublist = new ArrayList<Object>();
		  
		   System.out.println(list.toString());
		   
	   } 
	   
	   public static void testObj2() {  
		   List<List<Object>> list = new ArrayList<List<Object>>();  
		   {
			   List<Object> sublist = new ArrayList<Object>();  
			   list.add(sublist);
			   sublist.add(new Object());  
			   System.out.println(list.toString());
			   System.out.println("--------------------------");
			   sublist = null; 
		   }
		   System.out.println(list.toString());
		   
	   } 
}
