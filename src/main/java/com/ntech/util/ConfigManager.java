package com.ntech.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigManager {
	
	private static Logger logger = Logger.getLogger(ConfigManager.class);
	
	private static ConfigManager instance;
	private static Properties properties;
	static {
		logger.info("*********start reading properties**********");
		String configFile = "SDK.properties";
 	   	properties = new Properties();
 	   	InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream(configFile);
 	   	try {
			properties.load(is);
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	       private ConfigManager() {}
	       public static ConfigManager getInstance(){    //对获取实例的方法进行同步
	         if (instance == null){
	             synchronized(ConfigManager.class){
	                if (instance == null)
	                    instance = new ConfigManager(); 
	             }
	         }
	       return instance;
	       }
	       public String getParameter(String key) {
	    	   return properties.getProperty(key);
	       }
	       
}
