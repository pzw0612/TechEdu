package com.yhd.o2o.map.yuanyuan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

public class AddressToLngLatEn{

	public  static String filename="D:/tmp/小雷站点四级区域地址1.txt";
	static FileOutputStream out = null;
	static ExecutorService exec=Executors.newFixedThreadPool(2);  

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
					
					if(addressList.size()==10000){

						exec.execute(new LngLatExcutor(addressList,out));
						i=0;
						addressList = new ArrayList<String>();
					}
					
					start += span;
				}
				if(i%10000 !=0){
					out = new FileOutputStream(filename.substring(
							0,filename.indexOf("."))+"_result"+(i%10000)+".txt",true);
					
					exec.execute(new LngLatExcutor(addressList,out));
					addressList = new ArrayList<String>();
				}
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
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	

	

	public static void main(String[] args){
//		String[] filenames = new String[1];
//		filenames[0] = "D:/小雷站点四级区域地址1.txt";
////		filenames[1] = "D:/address2.txt";
//		for(int i=0;i<filenames.length;i++){
//			AddressToLngLatEn test = new AddressToLngLatEn();
//			test.filename = filenames[i];
//			//new Thread(test).start();
//		}
		
		AddressToLngLatEn addressToLngLatEn = new AddressToLngLatEn();
		addressToLngLatEn.excute();
		
		//exec.shutdown();
	}
	
}


final  class LngLatExcutor implements Runnable{
	public final static Integer CONNECT_TIMEOUT = 10000;
	private final static String URL = "http://api.map.baidu.com/geocoder/v2/";
	FileOutputStream out = null;
	 List<String> addressList  = null;
	
	public LngLatExcutor(final List<String> addressList, FileOutputStream out){
		this.addressList = addressList;
		this.out = out;
	}
	
	@Override
	public void run() {
		try{
			for(String address: addressList){
				String jsonStr = getLngLat(address);
				JSONObject jsonObject = JSONObject.fromObject(jsonStr);
				System.out.println("json:"+jsonObject);
				AddressBO bo = (AddressBO) JSONObject.toBean(
						jsonObject, AddressBO.class);
				if(bo != null){
					String message = address
							+" "+bo.getResult().getLocation().getLng()
							+" "+bo.getResult().getLocation().getLat()+"\r\n";
					System.out.println("lines"+message);
					out.write(message.getBytes());
				}
//			Thread.sleep(1000);
				
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	

	private  String getLngLat(String address){
		HttpURLConnection httpConn = null;
		InputStreamReader isr = null;
		InputStream is = null;
		BufferedReader reader = null;
		String lines = "";
		String info = "?address=" + address + "&output=json&ak=XjUHrIG78y5ypW08sFBGiG3h";
		try{
			URL url = new URL(URL+info);
			httpConn = (HttpURLConnection) url
					.openConnection();
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setConnectTimeout(CONNECT_TIMEOUT);
			httpConn.setReadTimeout(CONNECT_TIMEOUT);
			httpConn.setRequestMethod("GET");
			httpConn.connect();

			if (200 == httpConn.getResponseCode()) {
				is = httpConn.getInputStream();
				isr = new InputStreamReader(is);
				reader = new BufferedReader(isr);
				lines = reader.readLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(isr != null){
				try{
					isr.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(reader != null){
				try{
					reader.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(httpConn != null){
				httpConn.disconnect();
			}
		}
		
		return lines;
	}
	
	
}

