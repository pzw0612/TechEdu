package org.ifly.edu.java.base;

import java.util.Date;



/**
 * 
 * @author pangzhiwang
 * @date 2016-10-24
 */
public class DataTest {

	public static void main(String[] args) {
		
		
		Date date = new Date();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("cost:"+ (new Date().getTime()- date.getTime()));
	}
}
