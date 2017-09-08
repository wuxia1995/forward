package com.ntech.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

public class PictureShow {
	
	private Logger logger = Logger.getLogger(PictureShow.class);
	
	private static PictureShow instance;
	private PictureShow() {}
	  public static PictureShow getInstance(){    //对获取实例的方法进行同步
	     if (instance == null){
	         synchronized(PictureShow.class){
	            if (instance == null)
	                instance = new PictureShow(); 
	         }
	     }
	   return instance;
	 }
	public synchronized String getBase64Picture (String Url) {
		InputStream inputStream = null;
		DataInputStream dataInputStream = null;
		BufferedInputStream bufferedInputStream = null;
		HttpURLConnection connection = null;
		try {
			URL url = new URL(Url);
			logger.info("URL: "+url);
			   connection = (HttpURLConnection) url.openConnection();
	           connection.setDoInput(true); 
	           connection.setConnectTimeout(10000);
	           connection.setReadTimeout(30000);
	           connection.setRequestMethod("GET");
	           connection.connect();
        	   logger.info("http_code:"+connection.getResponseCode());
	           if(connection.getResponseCode()==200) {
	        	   inputStream =connection.getInputStream();
	        	   dataInputStream = new DataInputStream(inputStream);
	        	   bufferedInputStream = new BufferedInputStream(dataInputStream);
	        	   byte[] b = new byte[connection.getContentLength()];
	        	   logger.info("ContentLength: "+connection.getContentLength()); 
	        	   bufferedInputStream.read(b);
	        	   return "data:image/jpeg;base64,"+Base64Encrypt.byteArrayToString(b);
	           }
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			connection.disconnect();
			if(dataInputStream!=null)
				try {
					dataInputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}
}
