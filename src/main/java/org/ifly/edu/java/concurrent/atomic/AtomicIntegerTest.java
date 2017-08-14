package org.ifly.edu.java.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
  public static void main(String[] args) {
	  AtomicInteger curIvkCount = new AtomicInteger(-1);
	  
	  for(int i =0 ; i< 10; ++i){
		  System.out.println(curIvkCount.incrementAndGet());
	  }

	   AtomicInteger ai = new AtomicInteger(1);
	   System.out.println(ai.getAndIncrement());
	   System.out.println(ai.get());
}
}
