package com.ntech.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ntech.exception.IllegalIDException;
import com.ntech.forward.ConnectionSDK;
import com.ntech.forward.Constant;

import com.ntech.util.Mysql;

public class Check {
	
	private static Logger logger = Logger.getLogger(Check.class);
	private static Map<String, String> header = new HashMap<String,String>();
	private static JSONParser jsonParser = new JSONParser();
	private static JSONObject jsonObject = new JSONObject();
	private static JSONArray jsonArray = new JSONArray();
	private static List<String> galleries = new ArrayList<String>();
	private static Mysql mysql = new Mysql();
	static {
		header.put("Method","GET");
		header.put("Authorization",Constant.TOKEN);
		header.put("Content-Type", "application/json");
	}
	
	public static List<String> checkId(String id) {
		galleries.clear();
		header.put("API","/v0/face/id"+id);
		String reply = ConnectionSDK.getInstance().httpURLConnectionSDK(header, null, "no");
		if(reply!=null)
		try {
			jsonObject = (JSONObject) jsonParser.parse(reply);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("BAD ID");
			ErrorPrompt.addInfo("error"+(ErrorPrompt.size()+1),"bad_id");
			e.printStackTrace();
			return null;
		}
		jsonArray =  (JSONArray) jsonObject.get("galleries");
		if(jsonArray!=null)
		if(jsonArray.size()!=0) {
			Iterator<String> iterator = jsonArray.iterator();
			while(iterator.hasNext()) {
				String gallery = iterator.next();
				if(!gallery.equals("default"))
					galleries.add(gallery);  
			}
			return galleries;
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
}
