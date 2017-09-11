package com.ntech.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.ntech.forward.Constant;
import org.json.simple.JSONObject;

public class ConnectionSDK {
	
	public static final String FORWARD_URL = Constant.SDK_IP;

	public static String httpURLConnectionPOST (Map<String,String> param,Map<String,Object> body
			,String meta) {
		URL url;
		StringBuffer stringBuffer= new StringBuffer();
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			url = new URL(FORWARD_URL+param.get("API"));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	           connection.setDoOutput(true);  
	           connection.setDoInput(true); 
	           connection.setConnectTimeout(8000);
	           connection.setReadTimeout(6000);
	           connection.setRequestMethod(param.get("Method"));
	           connection.setRequestProperty("Content-Type", param.get("Content-Type"));
	           connection.setRequestProperty("Authorization","Token "+param.get("Authorization"));
	           if(meta.equals("yes")) {
	        	   bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
		           JSONObject.writeJSONString(body, bufferedWriter);
		           bufferedWriter.close();
	           }
        	   /*System.out.println(connection.getResponseCode());*/
        	   connection.connect();
	           if(connection.getResponseCode()==200) {
	        	   InputStreamReader inputStream =new InputStreamReader(connection.getInputStream());
	               bufferedReader = new BufferedReader(inputStream);                
	               String lines;                
	               stringBuffer= new StringBuffer("");              
	               while ((lines = bufferedReader.readLine()) != null) { 
	            	   lines = new String(lines.getBytes(),"utf-8");
	            	   stringBuffer.append(lines);   
	               }
	           }
	           connection.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(bufferedReader!=null)
					bufferedReader.close();
				if(bufferedWriter!=null)
					bufferedWriter.close();	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return stringBuffer.toString();
	}
}
