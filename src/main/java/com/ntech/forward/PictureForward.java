package com.ntech.forward;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ntech.exception.ErrorTokenException;
import com.ntech.util.Base64Encrypt;


public class PictureForward {
	
	private Logger logger = Logger.getLogger(PictureForward.class);
	private static final String FORWARD_URL = Constant.PIC;
	private static List<Byte> pic = new ArrayList<Byte>();
	
	private static PictureForward instance;
	private PictureForward() {}
	  public static PictureForward getInstance(){    //对获取实例的方法进行同步
	     if (instance == null){
	         synchronized(PictureForward.class){
	            if (instance == null)
	                instance = new PictureForward(); 
	         }
	     }
	   return instance;
	 }
	public synchronized List<Byte> requestForward (HttpServletRequest request,HttpServletResponse response) throws ErrorTokenException  {
		URL url;
		InputStream inputStream = null;
		DataInputStream dataInputStream = null;
		pic.clear();
		try {
			String userName = (String) request.getAttribute("userName");
			logger.info("USER: "+userName);
			String pictureLocation = request.getRequestURI().split("uploads")[1];
			String encrypt = pictureLocation.split("/")[1];
			String picMaster = Base64Encrypt.decryptUserName(encrypt);
			logger.info("PIC_MASTER: "+picMaster);
			if(userName==null||!userName.equals(picMaster))
				throw new ErrorTokenException("getPictureError");
			url = new URL(FORWARD_URL+pictureLocation.substring(encrypt.length()+1));
			logger.info("URL: "+url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	           connection.setDoInput(true); 
	           connection.setConnectTimeout(10000);
	           connection.setReadTimeout(30000);
	           connection.setRequestMethod("GET");
	           connection.setRequestProperty("Connection","keep-alive");
	           connection.connect();
        	   logger.info("http_code:"+connection.getResponseCode());
	           if(connection.getResponseCode()==200) {
	        	   inputStream =connection.getInputStream();
	        	   dataInputStream = new DataInputStream(inputStream);
	        	   int n;
	               while ((n=dataInputStream.read())!=-1) { 
	            	   pic.add((byte)n);
	               }
	           }
	           connection.disconnect();
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			logger.error(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			if(dataInputStream!=null)
				try {
					dataInputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
		}
		logger.info("PIC_LENGTH: "+pic.size());
		return pic;
	}
}
