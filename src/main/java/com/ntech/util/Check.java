package com.ntech.util;

import java.util.*;

import com.ntech.model.LibraryKey;
import com.ntech.model.Log;
import com.ntech.model.SetMeal;
import com.ntech.service.inf.ICustomerService;
import com.ntech.service.inf.ILibraryService;
import com.ntech.service.inf.ILogService;
import com.ntech.service.inf.ISetMealService;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ntech.exception.IllegalIDException;
import com.ntech.forward.ConnectionSDK;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Check {
	
	private static Logger logger = Logger.getLogger(Check.class);
	private static JSONParser jsonParser = new JSONParser();

	@Resource
	 private ICustomerService customerService ;
	@Resource
	 private ILibraryService libraryService;
	@Resource
	 private ISetMealService setMealService;
	@Resource
	 private ILogService logService;

	public List<String> checkId(String id) {
		List<String> galleries = new ArrayList<String>();
		Map<String, String> header = new HashMap<String,String>();
		header.put("API","/v0/face/id"+id);
		String reply = ConnectionSDK.getInstance().httpURLConnectionSDK(header);
		try {
			if(reply==null||"".equals(reply)) 
					throw new IllegalIDException("bad_id");
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reply);
			JSONArray jsonArray =  (JSONArray) jsonObject.get("galleries");
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
	public  boolean createGallery(String userName,String galleryName){
		LibraryKey libraryKey = new LibraryKey();
		libraryKey.setLibraryName(galleryName);
		libraryKey.setUserName(userName);
		int result =  libraryService.create(libraryKey);
		if(result==0)
			try {
				throw new Exception("delete failed");
			} catch (Exception e) {
				ErrorPrompt.addInfo("error",e.getMessage());
				e.printStackTrace();
				return false;
			}
		return true;
	}
	public boolean deleteGallery(String userName,String galleryName){
		LibraryKey libraryKey = new LibraryKey();
		libraryKey.setLibraryName(galleryName);
		libraryKey.setUserName(userName);
		int result =  libraryService.delete(libraryKey);
		if(result==0)
			try {
				throw new Exception("delete failed");
			} catch (Exception e) {
				ErrorPrompt.addInfo("error",e.getMessage());
				e.printStackTrace();
				return false;
			}
		return true;
	}
	public boolean mealTimesCount(String userName){
		SetMeal setMeal = setMealService.findByName(userName);
		if(setMeal.getTotalTimes()==null||!(setMeal.getTotalTimes()>0))
			try {
				throw new Exception("You don't have enough times");
			} catch (Exception e) {
				ErrorPrompt.addInfo("error",e.getMessage());
				e.printStackTrace();
				return false;
			}
		setMeal.setLeftTimes(setMeal.getLeftTimes()-1);
		return setMealService.modify(setMeal);
	}
    public boolean mealDateCheck(String userName){
        SetMeal setMeal = setMealService.findByName(userName);
        boolean result = setMeal.getEndTime().after(new Date());
        if(!result)
            try {
                throw new Exception("The set meal has expired");
            } catch (Exception e) {
                ErrorPrompt.addInfo("error",e.getMessage());
                e.printStackTrace();
            }
        return result;
    }
    public void setLog(String userName,String content,int result) {
        Log log = new Log();
        log.setUserName(userName);
        log.setContent(content);
        log.setResult(result);
        log.setTime(new Date());
        logService.add(log);
    }
    public String getContype(String userName) {
	    return setMealService.findByName(userName).getContype();
    }
}
