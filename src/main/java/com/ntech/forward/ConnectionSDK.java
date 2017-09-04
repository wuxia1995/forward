package com.ntech.forward;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.ntech.util.ErrorPrompt;

public class ConnectionSDK {
	
	private static Logger logger = Logger.getLogger(ConnectionSDK.class);
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
	public synchronized String httpURLConnectionSDK (Map<String,String> param,Map<String,Object> body
			,String meta) {
		URL url;
		StringBuffer stringBuffer= null;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		String reply="";
		try {
			url = new URL(FORWARD_URL+param.get("API"));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	           connection.setDoOutput(true);  
	           connection.setDoInput(true); 
	           connection.setConnectTimeout(8000);
	           connection.setReadTimeout(6000);
	           connection.setRequestMethod(param.get("Method"));
	           connection.setRequestProperty("Connection","keep-alive");
	           connection.setRequestProperty("Content-Type", param.get("Content-Type"));
	           connection.setRequestProperty("Authorization","Token "+param.get("Authorization"));
	           if(meta.equals("yes")) {
	        	   bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
		           JSONObject.writeJSONString(body, bufferedWriter);
		           bufferedWriter.close();
	           }
        	   connection.connect();
        	   int code = connection.getResponseCode();
               if(code!=200&&code!=204)
               	logger.error("ERROR_CODE: "+code);
               logger.info("HTTP_CODE: "+code);
	           if(code==200) {
	        	   InputStreamReader inputStream =new InputStreamReader(connection.getInputStream());
	               bufferedReader = new BufferedReader(inputStream);                
	               String lines;                
	               stringBuffer= new StringBuffer("");              
	               while ((lines = bufferedReader.readLine()) != null) { 
	            	   lines = new String(lines.getBytes(),"utf-8");
	            	   stringBuffer.append(lines);   
	               }
	           }
	           if(code==204) {
	        	   ErrorPrompt.addInfo("excute","successful");
	           }else if(code==404){
	        	   throw new FileNotFoundException();
	           }else {
	        	   reply = stringBuffer.toString();
	           }   
	           connection.disconnect();
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}catch (FileNotFoundException e) {
			ErrorPrompt.addInfo("error"+(ErrorPrompt.size()+1), "404");
			return null;
		}catch (IOException e) {
			logger.error(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(bufferedReader!=null)
					bufferedReader.close();
				if(bufferedWriter!=null)
					bufferedWriter.close();	
			} catch (IOException e) {
				logger.error(e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return reply;
	}
}
