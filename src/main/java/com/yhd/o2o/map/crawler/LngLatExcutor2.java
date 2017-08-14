/**
 * 
 */
package com.yhd.o2o.map.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import net.sf.json.JSONObject;

/**
 * @Description: TODO
 * @author pangzhiwang
 * @date 2016-2-2 下午1:55:11
 */
public  class LngLatExcutor2 implements Runnable{
	
	public final static Integer CONNECT_TIMEOUT = 10000;
	private final static String URL = "http://api.map.baidu.com/geocoder/v2/";

	List<String> addressList  = null;
	
	public LngLatExcutor2(final List<String> addressList){
		this.addressList = addressList;
	}
	
	@Override
	public void run() {
		try{
			StringBuffer sb = null;
			for(String address: addressList){
				String jsonStr = getLngLat(address);
				JSONObject jsonObject = JSONObject.fromObject(jsonStr);
				System.out.println("json:"+jsonObject);
	
				if(jsonObject.containsKey("result")){
					JSONObject resultObj =(JSONObject)jsonObject.getJSONObject("result");
					if(resultObj!=null){
						JSONObject locationObj = resultObj.getJSONObject("location");
						sb = new StringBuffer(address).append(" ").append(locationObj.get("lng")).append(" ")
                                .append(locationObj.get("lat")).append("\r\n");
                        WriterQueue.getQueue().put(sb.toString());
					}
				}else{
					System.out.println("json:error:"+jsonObject);
				}

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
