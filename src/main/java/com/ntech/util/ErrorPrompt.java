package com.ntech.util;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class ErrorPrompt {
	
	private static final Map<String,String> map = new HashMap<String,String>();
	
	public static final void addInfo(String key,String value) {
		map.put(key, value);
	}
	public static final void clear() {
		map.clear();
	}
	public static final String getJSONInfo() {
		return JSONObject.toJSONString(map);
	}
	public static final int size() {
		return map.size();
	}
}
