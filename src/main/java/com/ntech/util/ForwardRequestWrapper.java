package com.ntech.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.ntech.exception.ErrorTokenException;
import com.ntech.exception.PermissionDeniedException;


public class ForwardRequestWrapper extends HttpServletRequestWrapper {
	
	private Logger logger = Logger.getLogger(ForwardRequestWrapper.class);
	ConfigManager sdk = ConfigManager.getInstance();
	
	public ForwardRequestWrapper(HttpServletRequest request) throws ErrorTokenException, PermissionDeniedException, ParseException {
		
		super(request);
		
		String inputToken = request.getHeader("Token");
		
		logger.info("InputToken :"+inputToken);
		boolean isToken = new CheckAPI().checkToken(inputToken);
		//Retrieve token from database
		if(!isToken||inputToken==null||inputToken.equals(""))
				throw new ErrorTokenException("Bad Token");
				
		//Retrieve userName from database
		String username = "user";
		// TODO Auto-generated constructor stub
		String method = request.getMethod();
		String API = request.getRequestURI().split(sdk.getParameter("PATH"))[1];
		while(true) {
			if(API.startsWith("/detect")) {
				API = sdk.getParameter("DOC")+"/detect";
				break;
			}
			if(API.startsWith("/verify")) {
				API = sdk.getParameter("DOC")+"/verify";
				break;
			}
			if(API.startsWith("/identify")) {
				API = sdk.getParameter("DOC")+"/identify";
				break;
			}
			if(API.equals("/face")||API.equals("/face/")) {
				API = sdk.getParameter("DOC")+"/face";
				request.setAttribute("userName", username);
				request.setAttribute("face", true);
				break;
			}	
			if(API.indexOf("faces")!=-1) {
				API = sdk.getParameter("DOC")+"/faces/gallery/"+username;
				break;
			}	
			if(API.indexOf("face/meta")!=-1) {
				API = sdk.getParameter("DOC")+"/face/gallery/"+username+"/meta"+API.split("meta")[1];
				break;
			}	
			if(API.indexOf("meta")!=-1&&API.indexOf("face")==-1){
				API = sdk.getParameter("DOC")+"/meta/gallery/"+username;
			break;
			}
			if(API.indexOf("face/id")!=-1&&(method.equals("PUT")||method.equals("DELETE"))){
				String id = API.split("id")[1];
				boolean isMaster = username.equals(new CheckAPI().checkId(id));
				if (isMaster) {
					API = sdk.getParameter("DOC")+"/face/id"+API.split("id")[1];
				}else {
						throw new PermissionDeniedException("Permission denied--BAD_ID");
				}
			break;
			}
			break;
		}
		request.setAttribute("API", API);
	}
}
