package org.ifly.edu.java.base;

import java.util.concurrent.atomic.AtomicBoolean;



/**
 * 
 * @author pangzhiwang
 * @date 2017-3-9
 */
public class FinallyTest {
	private static volatile AtomicBoolean requestFlg= new AtomicBoolean(false); 

	public static void main(String[] args) {
		for(int i=0; i< 10 ; i++){
			new Thread(new MyRunnable(i)).start();
		}
	}
	
	static class MyRunnable implements Runnable{
	    int count;
		private MyRunnable(int count){
			this.count =count;
		}
		@Override
		public void run() {
			if(requestFlg.compareAndSet(false, true)){
				try{
					if(count==5){
						throw new Exception("test");
					}
				}catch (Exception e) {
					System.out.println(e.getMessage() + " "+ count);
				}finally{
					System.out.println("requstHandle invoke times=" +count);
					requestFlg.set(false);
				}
			}
		}
	}
	
}



