/**
 * 
 */
package com.yhd.o2o.map.crawler;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-2-2 下午1:20:41
 */
public class TestWrite {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		test();
		test02();
	}

	private static void test02() {
		WriteTask_New write = new WriteTask_New();
		for(int i=0;i<4;i++){
			new Thread(write).start();
		}
		
		OutputTask output = new OutputTask("D:/tmp/小雷站点四级区域地址1_result.txt");
		new Thread(output).start();
	}

//	private static void test() {
//		WriteTask write = new WriteTask("abc.txt");
//		for(int i=0;i<5;i++){
//			new Thread(write).start();
//		}
//	}
}
