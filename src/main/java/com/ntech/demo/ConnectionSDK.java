package com.ntech.demo;

import com.ntech.forward.Constant;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ConnectionSDK {
	
	private final Logger logger = Logger.getLogger(ConnectionSDK.class);
	public static final String FORWARD_URL = Constant.SDK_IP;
	
	private static ConnectionSDK instance;
	private ConnectionSDK() {}
	  public static ConnectionSDK getInstance(){    //对获取实例的方法进行同步
	     if (instance == null){
	         synchronized(ConnectionSDK.class){
	            if (instance == null)
	                instance = new ConnectionSDK();
	         }
	     }
	   return instance;
	 }
	public synchronized String httpURLConnectionSDK (Map<String,String> header) {
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		StringBuilder stringBuilder = null;
		try {
			URL url = new URL(FORWARD_URL+header.get("API"));
			logger.info("URL: "+url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true); 
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(50000);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization","Token "+Constant.TOKEN);
			connection.connect();
			
			if(200==connection.getResponseCode()) {
				InputStreamReader inputStream =new InputStreamReader(connection.getInputStream());
				bufferedReader = new BufferedReader(inputStream);                
				String lines;                
				stringBuilder = new StringBuilder();  
				while ((lines = bufferedReader.readLine()) != null) { 
					lines = new String(lines.getBytes(),"utf-8");
					stringBuilder.append(lines);   
				}
			}
			connection.disconnect();
		}catch (IOException e) {
			logger.error(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(bufferedReader!=null)
					bufferedReader.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				return null;
			}
			try {
				if(bufferedWriter!=null)
					bufferedWriter.close();	
			} catch (IOException e) {
				logger.error(e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		if(stringBuilder!=null)
			return stringBuilder.toString();
		logger.info("ERROR: noReply");
		return null;
		
	}
}
