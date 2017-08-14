/**
 * 
 */
package org.ifly.edu.java.base;

import java.util.Map;

import org.jboss.netty.util.internal.ConcurrentHashMap;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2015-11-18 上午10:22:31
 */
public class FooTest {
  public static void main(String[] args) {
	  
	  for(int i=0; i<1000; i++){
		  System.out.println(test11() + "	"+ test12());
//		  test1();
//          test2();
	  }

   }
  
  static String test21(){
	  Map<Long,Combine> map = new ConcurrentHashMap<Long,Combine>();
	  
	  long startTime = System.nanoTime();
	  Long div = 100l;
	  Long dived = 0l;
	  Long value = 0l;
	  
	  Long tmp1 = null;
	  Long tmp2 = null;
	  Long tmp3 = null;
	  Long tmp4 = null;
	  Long tmp5 = null;
	  Long tmp6 = null;
	  Combine  cb = null;
	  for(int i =0; i< 1000; i++){
		  dived = i%div;
		  cb =map.get(dived);
		  tmp1 = (dived+2)/3;
		  tmp2 = (dived+3)/3;
		  tmp3 = (dived+4)/3;
		  tmp4 = (dived+5)/3;
		  tmp5 = (dived+5)/3;
		  tmp6 = (dived+5)/3;
		  
		  cb = new Combine("t1" +tmp1, "t1" +tmp2, "t1" +tmp3, "t1" +tmp4, "t1" +tmp5, "t1" +tmp6);
		  if(value==null){
			  value = 0l;
		  }else{
			  value += i;
		  }
		  map.put(dived, cb);
	  }
//	  System.out.println("map=" + map);
//	  System.out.println("test1 used time=" + (System.currentTimeMillis()-startTime));
	  return "" + (System.nanoTime()-startTime);
  }
  
  
  static String test22(){
	  Map<Long,Combine> map = new ConcurrentHashMap<Long,Combine>();
	  
	  long startTime = System.nanoTime();
	  Long div = 100l;
	  for(int i =0; i< 1000; i++){
		  Long  dived = i%div;
		  Combine  value =map.get(dived);
		  Long tmp1 = null;
		  Long tmp2 =  null;
		  Long tmp3= null;
		  Long tmp4 = null;
		  Long tmp5 = null;
		  Long tmp6 = null;
		  Combine cb = null;
		  
		   tmp1 = (dived+2)/3;
		   tmp2 = (dived+3)/3;
		   tmp3 = (dived+4)/3;
		   tmp4 = (dived+5)/3;
		   tmp5 = (dived+5)/3;
		   tmp6 = (dived+5)/3;
		   cb = new Combine("t2" +tmp1, "t2" +tmp2, "t2" +tmp3, "t2" +tmp4, "t2" +tmp5, "t2" +tmp6);
//		  if(value==null){
//			  value = 0l;
//		  }else{
//			  value += i;
//		  }
		  map.put(dived, cb);
	  }
//	  System.out.println("map=" + map);
//	  System.out.println("test2 used time=" + (System.currentTimeMillis()-startTime));
//  
	  return "" + (System.nanoTime()-startTime);
  }
  static String test11(){
	  Map<Long,Long> map = new ConcurrentHashMap<Long,Long>();
	  
	  long startTime = System.nanoTime();
	  Long div = 100l;
	  Long dived = 0l;
	  Long value = 0l;
	  
	  Long tmp1 = null;
	  Long tmp2 = null;
	  Long tmp3 = null;
	  Long tmp4 = null;
	  Long tmp5 = null;
	  Long tmp6 = null;
	  for(int i =0; i< 1000; i++){
		  dived = i%div;
		  value =map.get(dived);
		  tmp1 = (dived+2)/3;
		  tmp2 = (dived+3)/3;
		  tmp3 = (dived+4)/3;
		  tmp4 = (dived+5)/3;
		  tmp5 = (dived+5)/3;
		  tmp6 = (dived+5)/3;
		  
		  if(value==null){
			  value = 0l;
		  }else{
			  value += i;
		  }
		  map.put(dived, value);
	  }
//	  System.out.println("map=" + map);
//	  System.out.println("test1 used time=" + (System.currentTimeMillis()-startTime));
	  return "" + (System.nanoTime()-startTime);
  }
  
  
  static String test12(){
	  Map<Long,Long> map = new ConcurrentHashMap<Long,Long>();
	  
	  long startTime = System.nanoTime();
	  Long div = 100l;
	  for(int i =0; i< 1000; i++){
		  Long  dived = i%div;
		  Long  value =map.get(dived);
		  Long tmp1 = null;
		  Long tmp2 =  null;
		  Long tmp3= null;
		  Long tmp4 = null;
		  Long tmp5 = null;
		  Long tmp6 = null;
		  
		  tmp1 = (dived+2)/3;
		  tmp2 = (dived+3)/3;
		  tmp3 = (dived+4)/3;
		  tmp4 = (dived+5)/3;
		  tmp5 = (dived+5)/3;
		  tmp6 = (dived+5)/3;
		  
		  if(value==null){
			  value = 0l;
		  }else{
			  value += i;
		  }
		  map.put(dived, value);
	  }
//	  System.out.println("map=" + map);
//	  System.out.println("test2 used time=" + (System.currentTimeMillis()-startTime));
//  
	  return "" + (System.nanoTime()-startTime);
  }
}

class Combine{



	  String tmp1 = null;
	  String tmp2 = null;
	  String tmp3 = null;
	  String tmp4 = null;
	  String tmp5 = null;
	  String tmp6 = null;
	public String getTmp1() {
		return tmp1;
	}
	public void setTmp1(String tmp1) {
		this.tmp1 = tmp1;
	}
	public String getTmp2() {
		return tmp2;
	}
	public void setTmp2(String tmp2) {
		this.tmp2 = tmp2;
	}
	public String getTmp3() {
		return tmp3;
	}
	public void setTmp3(String tmp3) {
		this.tmp3 = tmp3;
	}
	public String getTmp4() {
		return tmp4;
	}
	public void setTmp4(String tmp4) {
		this.tmp4 = tmp4;
	}
	public String getTmp5() {
		return tmp5;
	}
	public void setTmp5(String tmp5) {
		this.tmp5 = tmp5;
	}
	public String getTmp6() {
		return tmp6;
	}
	public void setTmp6(String tmp6) {
		this.tmp6 = tmp6;
	}
	public Combine(String tmp1, String tmp2, String tmp3, String tmp4,
			String tmp5, String tmp6) {
		super();
		this.tmp1 = tmp1;
		this.tmp2 = tmp2;
		this.tmp3 = tmp3;
		this.tmp4 = tmp4;
		this.tmp5 = tmp5;
		this.tmp6 = tmp6;
	}
	  
	  
}
