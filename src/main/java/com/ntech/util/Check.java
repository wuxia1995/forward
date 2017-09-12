package com.ntech.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ntech.model.LibraryKey;
import com.ntech.service.impl.CustomerService;
import com.ntech.service.impl.LibraryService;
import com.ntech.service.inf.ICustomerService;
import com.ntech.service.inf.ILibraryService;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ntech.exception.IllegalIDException;
import com.ntech.forward.ConnectionSDK;

import com.ntech.util.Mysql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Component
public class Check {
	
	private static Logger logger = Logger.getLogger(Check.class);
	private static JSONParser jsonParser = new JSONParser();
	
	private static JSONArray jsonArray = null;
	@Autowired
	 private ICustomerService customerService ;
	@Autowired
	 private ILibraryService libraryService;

	public List<String> checkId(String id) {
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
			ErrorPrompt.addInfo("error","bad_id");
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public  boolean checkToken(String inputToken)  {
		return customerService.checkToken(inputToken);
	}
	public  String getUserName(String inputToken)  {

		return customerService.findByToken(inputToken);
	}
	public  List<String> getGalleries(String inputToken){
		List<String> galleryList = new ArrayList<String>();
		List<LibraryKey> list = libraryService.findByToken(inputToken);
		Iterator<LibraryKey> iterator = list.iterator();
		while(iterator.hasNext()){
			galleryList.add(iterator.next().getLibraryName());
		}
		return galleryList;
	}
	public  int createGallery(String userName,String galleryName){
		LibraryKey libraryKey = new LibraryKey();
		libraryKey.setLibraryName(galleryName);
		libraryKey.setUserName(userName);
		return libraryService.insert(libraryKey);
	}
	public  int deleteGallery(String userName,String galleryName){
		LibraryKey libraryKey = new LibraryKey();
		libraryKey.setLibraryName(galleryName);
		libraryKey.setUserName(userName);
		return libraryService.delete(libraryKey);
	}
/*	public static boolean timesCount(String userName){
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
	}*/
}
