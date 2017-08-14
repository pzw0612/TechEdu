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

import net.sf.json.JSONObject;

public class AddressToLngLatEn2 implements Runnable{
	private final static String URL = "http://api.map.baidu.com/geocoder/v2/";
	private final static Integer CONNECT_TIMEOUT = 10000;
	public  String filename;

	private void getResult(){
		InputStreamReader read=null;
		BufferedReader reader=null;
		File file = new File(filename);
		FileOutputStream out = null;
		try{
			read = new InputStreamReader(new FileInputStream(file),"GBK");
			reader = new BufferedReader(read);
			out = new FileOutputStream(filename.substring(
					0,filename.indexOf("."))+"_result.txt");
			String line;
			int span = 1;
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
					String jsonStr = getLngLat(buffer.toString());
					JSONObject jsonObject = JSONObject.fromObject(jsonStr);
					System.out.println("json:"+jsonObject);
					
					if(jsonObject.containsKey("result")){
						AddressBO bo = (AddressBO) JSONObject.toBean(
								jsonObject, AddressBO.class);
						if(bo != null){
						String message = buffer.toString()
								+" "+bo.getResult().getLocation().getLng()
								+" "+bo.getResult().getLocation().getLat()+"\r\n";
						System.out.println("lines"+message);
						out.write(message.getBytes());
					}else{
						System.out.println("json:error:"+jsonObject);
					}
					

					}
					start += span;
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

	private String getLngLat(String address){
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
			httpConn.setReadTimeout(CONNECT_TIMEOUT*2);
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

	public static void main(String[] args){
		String[] filenames = new String[1];
		filenames[0] = "D:/小雷站点四级区域地址1.txt";
//		filenames[1] = "D:/address2.txt";
		
		long start = System.currentTimeMillis();
		
		for(int i=0;i<filenames.length;i++){
			AddressToLngLatEn2 test = new AddressToLngLatEn2();
			test.filename = filenames[i];
			new Thread(test).start();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("used time:"+ (end-start)/1000/3600);
	}

	@Override
	public void run() {
		try{
		getResult();
//		Thread.sleep(1000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
