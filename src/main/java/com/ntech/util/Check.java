package com.ntech.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ntech.exception.IllegalIDException;
import com.ntech.forward.ConnectionSDK;

import com.ntech.util.Mysql;

public class Check {
	
	private static Logger logger = Logger.getLogger(Check.class);
	private static JSONParser jsonParser = new JSONParser();
	
	private static JSONArray jsonArray = null;
	private static Mysql mysql = new Mysql();
	
	public static List<String> checkId(String id) {
		List<String> galleries = new ArrayList<String>();
		Map<String, String> header = new HashMap<String,String>();
		header.put("API","/v0/face/id"+id);
		String reply = ConnectionSDK.getInstance().httpURLConnectionSDK(header);
		try {
			if(reply==null||"".equals(reply)) 
					throw new IllegalIDException("bad_id");
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reply);
			jsonArray =  (JSONArray) jsonObject.get("galleries");
			if(jsonArray!=null&&jsonArray.size()!=0) {
				@SuppressWarnings("unchecked")
				Iterator<String> iterator = jsonArray.iterator();
				while(iterator.hasNext()) {
					String gallery = iterator.next();
					if(!gallery.equals("default"))
						galleries.add(gallery);  
				}
				return galleries;
			}
		}catch (IllegalIDException e) {
			logger.error("BAD ID");
			ErrorPrompt.addInfo("error"+(ErrorPrompt.size()+1),"bad_id");
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean checkToken(String inputToken)  {
		boolean flag = false;
		try {
			flag =  mysql.isToken(inputToken);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	public static String getUserName(String inputToken)  {
		String userName = null;
		try {
			userName = mysql.getUserName(inputToken);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userName;
	}
	public static List<String> getGalleries(String inputToken){
		List<String> list = null;
		try {
			list = mysql.getGalleries(inputToken);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static boolean createGallery(String inputToken,String userName,String galleryName){
		boolean flag = false;
		try {
			flag =  mysql.createGallery(inputToken, userName, galleryName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	public static boolean deleteGallery(String galleryName){
		boolean flag = false;
		try {
			flag =  mysql.deleteGallery(galleryName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	public static boolean timesCount(String userName){
		boolean flag = false;
		try {
			flag =  mysql.timesCount(userName);
			if(!flag)
				throw new Exception("timesCountException");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			ErrorPrompt.addInfo("error","timesCountFail");
			e.printStackTrace();
		}
		return flag;
	}
}
