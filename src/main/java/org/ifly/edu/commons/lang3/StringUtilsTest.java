package org.ifly.edu.commons.lang3;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author pangzhiwang
 * @date 2017-6-27
 */
public class StringUtilsTest {
	/** 
	 * @param args
	 */
	public static void main(String[] args) {

        String str = "dskeabceead";
        substringAfter(str);
        substringBefore(str);

        
        str="192.168.128.219*9000";
        if(StringUtils.contains(str, ":")){
        	System.out.println("ip:"+StringUtils.substringBefore(str,":"));
        }
       
    }
	
	public static void substringBefore(String str){
        String before = StringUtils.substringBefore(str, "e");
        System.out.println("before:"+before);
        String beforeLast = StringUtils.substringBeforeLast(str, "e");
        System.out.println("beforeLast:"+beforeLast);
	}
	
	public static void substringAfter(String str){
        String after = StringUtils.substringAfter(str, "e");
        System.out.println("after:"+after);
        String afterLast = StringUtils.substringAfterLast(str, "e");
        System.out.println("afterLast:"+afterLast);
	}
	
}
