package org.ifly.edu.java.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class DateTimeTest {
    public static void main(String[] args) {
    	after();
    	
    	formate();
    	System.out.println(DateFormatUtils.format(new Date(),"yyyyMMdd"));
    	
    	try {
			Date inputDate = DateUtils.parseDate("2015-09-22 00:00:00", "yyyy-MM-dd hh:mm:ss".split(","));
			System.out.println(inputDate.getTime());
    	} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
	}
	
	private static void before(){
		
	    Calendar calendar = Calendar.getInstance();
		long currentTime = calendar.getTime().getTime();
		
		long currentTime2 =currentTime +60;
		
		
		System.out.println(" currentTime.before( currentTime2)=" + new Date(currentTime).before(new Date(currentTime2)));
	}
	
	private static void after(){
		 Calendar calendar = Calendar.getInstance();
	     Date currentDate = calendar.getTime();
	     
//	     calendar.add(Calendar.DAY_OF_MONTH, -1);
//	     System.out.println("currentDate=" + currentDate.getTime());
	     
	     calendar.set(calendar.MINUTE, currentDate.getMinutes()+20);
	     Date afterDate = calendar.getTime();
	     
	     System.out.println("currentDate=" + currentDate.getTime());
	     
	     calendar.add(Calendar.MARCH, -12);
	     
	     System.out.println("lastDate=" + calendar.getTime().getTime());
	     
	     System.out.println("after 用法,afterDate.after(currentDate)="+ afterDate.after(currentDate));
	     System.out.println("before 用法,currentDate.before(afterDate)="+ currentDate.before(afterDate));
	     
	     System.out.println(currentDate.compareTo(afterDate));
	     
	}
	
	
	private static void formate(){
		Date date = new Date(1440060141000L);
		
		DateFormat df = DateFormat.getDateInstance();
		
		System.out.println(df.format(date));
	}
}
