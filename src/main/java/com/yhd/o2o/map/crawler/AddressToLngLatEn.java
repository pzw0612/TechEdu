package com.yhd.o2o.map.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddressToLngLatEn{

	public  static String filename="D:/tmp/小雷站点四级区域地址1.txt";
	static ExecutorService exec=Executors.newFixedThreadPool(4);  

	private void excute(){
		InputStreamReader read=null;
		BufferedReader reader=null;
		File file = new File(filename);
	
		try{
			read = new InputStreamReader(new FileInputStream(file),"GBK");
			reader = new BufferedReader(read);
            List<String> addressList = null;
			String line;
			int span = 1;
			
			int i=0;
			addressList = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				String[] info = line.split("[|]");
				int start = Integer.parseInt(info[8].trim());
				int end = Integer.parseInt(info[9].trim());
				if(!info[10].trim().equals("全部")){
					span = 2;
				}else{
					span = 1;
				}
				
				while(start <= end){
					i++;
					StringBuffer buffer = new StringBuffer();
					//市
					buffer.append(info[4].trim());
					//区
					buffer.append(info[5].trim());
					//街
					buffer.append(info[7].trim());
					//门牌号
					buffer.append(start+"");
					buffer.append("号");
					
					addressList.add(buffer.toString());
					
					if(i% 10000 == 0){
						exec.execute(new LngLatExcutor2(addressList));
						addressList = new ArrayList<String>();
					}
					start += span;
				}
			}
			if(i%10000 !=0){
				exec.execute(new LngLatExcutor2(addressList));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(read != null){
				try {
					read.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	
	public static void main(String[] args) throws InterruptedException{

		long start = System.currentTimeMillis();
		AddressToLngLatEn addressToLngLatEn = new AddressToLngLatEn();
		addressToLngLatEn.excute();
		
		
		Thread.sleep(1000);
		
		OutputTask output = new OutputTask("D:/tmp/小雷站点四级区域地址1_result.txt");
		new Thread(output).start();
		
		long end = System.currentTimeMillis();
		
		System.out.println("used time:"+ (end-start)/1000/3600);
		
		//exec.shutdown();
	}
	
}




