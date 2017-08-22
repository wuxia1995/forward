package com.ntech.util;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ntech.forward.ConnectionSDK;
import com.ntech.forward.Constant;

public class CheckAPI {
	
	private static Map<String, String> header = new HashMap<String,String>();
	private static JSONParser jsonParser = new JSONParser();
	private static JSONObject jsonObject = new JSONObject();
	private static JSONArray jsonArray = new JSONArray();
	static {
		header.put("Method","GET");
		header.put("Authorization",Constant.TOKEN);
		header.put("Content-Type", "application/json");
	}
	
	public String checkId(String id) throws ParseException {
		header.put("API","/v0/face/id"+id);
		String reply = ConnectionSDK.httpURLConnectionPOST(header, null, "no");
		jsonObject = (JSONObject) jsonParser.parse(reply);
		jsonArray =  (JSONArray) jsonObject.get("galleries");
		
		return (String) jsonArray.get(1);
	}
	
	public boolean checkToken(String inputToken) {
		return true;
	}
}
