package org.ifly.edu.java.base;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;



public class StringTest {

	public static void main(String[] args) {
		
		test();
	}
	public static void test2(){
		String file = "aaa.jpg";
		int index = file.lastIndexOf(".");
		System.out.println("suffix="+ file.substring(index+1));
		
		
		String aa = "aa";
		
		String [] arr = aa.split(",");
		
		System.out.println(arr.length);
		System.out.println(arr[0]);
		
		String filename="aaa";
		
		
		// 处理原始图片的名称 ,防止重名
//		int dot_index = filename.lastIndexOf(".");
//		System.out.println(filename.substring(dot_index, filename.length())); 
		
		BeanTest test = new BeanTest();
		System.out.println(test);
		
		
		
		StringBuffer sb  = new StringBuffer("\"");
		sb.append(" text ").append(10).append(",").append(10).append(" '").append("中文测试").append("' \"");
		
		
		System.out.println(sb.toString());
	}
	
	public static void test(){
		String str ="http://d8.yihaodianimg.com/N04/M08/BD/D2/CgQDrlfwa2uAZNq8AAO-3u_YiBI358.png";
		System.out.println(FilenameUtils.getName(str));
		URL url;
		try {
			url = new URL(str);
			String picturePath= url.getPath();
			System.out.println(picturePath);
			
			int index = picturePath.indexOf("/", 1);
			System.out.println("context=" + picturePath.substring(1, index));
			
			String app = url.getHost();
			System.out.println("app="+ app);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
