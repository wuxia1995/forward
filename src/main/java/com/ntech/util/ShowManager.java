package com.ntech.util;

import java.io.IOException;
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

import com.ntech.forward.Constant;
import com.ntech.forward.HttpUploadFile;

public class ShowManager {
	
	private static Logger logger = Logger.getLogger(ShowManager.class);
	
	private  Map<String ,String> header = new HashMap<String,String>();
	private  JSONParser jsonParser = new JSONParser();
	private  JSONObject jsonObject = new JSONObject();
	private  JSONArray jsonArray = new JSONArray();
	private  List<String> list = new ArrayList<String>();
	
	public static void main(String[] args) throws ParseException, IOException {
		System.out.println(new ShowManager().getGalleries());
	}
	public  boolean createGallery(String galleryName) throws IOException {
		
		boolean flag = false;
		if(!containsGallery(galleryName)) {
			header.put("Method","POST");
			header.put("Authorization",Constant.TOKEN);
			header.put("API","/v0/galleries/"+galleryName);
			String reply = HttpUploadFile.getInstance().httpURLConnectionSDK(header, null,null, "no");
		}
		if(containsGallery(galleryName))
			flag = true;
		return flag; 
	}
	public  List<String> getGalleries() throws IOException {
		
		boolean flag = false;
		header.put("Method","GET");
		header.put("Authorization",Constant.TOKEN);
		header.put("API","/v0/galleries");
		String reply = HttpUploadFile.getInstance().httpURLConnectionSDK(header,null,null, "no");
		try {
			jsonObject = (JSONObject) jsonParser.parse(reply);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.info("ServerNoResponse");
			e.printStackTrace();
		}
		jsonArray = (JSONArray) jsonObject.get("results");
		Iterator<String> iterator = jsonArray.iterator();
		list.clear();
		while(iterator.hasNext()) {
			String gallery = iterator.next();
			if(!gallery.equals("default"))
				list.add(gallery);
		}
		return list; 
	}
	public  boolean deleteGallery(String galleryName) throws IOException {
		
		boolean flag = false;
		header.put("Method","DELETE");
		header.put("Authorization",Constant.TOKEN);
		header.put("API","/v0/galleries/"+galleryName);
		String reply = HttpUploadFile.getInstance().httpURLConnectionSDK(header,null,null, "no");
		if(!containsGallery(galleryName))
			flag = true;
		return flag; 
	}
	public  boolean containsGallery(String galleryName) throws IOException {
		
		boolean flag = false;
		list.clear();
		list = getGalleries();
		if(list.contains(galleryName))
			flag = true;
		return flag; 
	}
}
