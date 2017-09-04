package com.ntech.util;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.ntech.exception.ErrorTokenException;
import com.ntech.exception.IllegalAPIException;
import com.ntech.exception.IllegalIDException;
import com.ntech.forward.Constant;
import com.ntech.exception.IllegalGalleryException;


public class ForwardRequestWrapper extends HttpServletRequestWrapper {
	
	private Logger logger = Logger.getLogger(ForwardRequestWrapper.class);
	private static List<String> galleries = null;
	
	public ForwardRequestWrapper(HttpServletRequest request) throws ErrorTokenException, IllegalAPIException, IllegalIDException, IllegalGalleryException {
		
		super(request);
		String API = request.getRequestURI().split(Constant.PATH)[1];
	
		if(API.equals("/test")) {
			return;
		}
		String inputToken = request.getHeader("Token");
		String userName = null;
		String requestIP = request.getRemoteHost();
		logger.info("requestIP :"+requestIP);
		boolean isToken = Check.checkToken(inputToken);
		//Retrieve token from database
		if(!isToken||inputToken==null||inputToken.equals(""))
				throw new ErrorTokenException("bad_token");
		userName = Check.getUserName(inputToken);
		logger.info("UserName: "+userName);
		request.setAttribute("userName",userName);
		if(API.startsWith("/picture")) {
			return;
		}
		logger.info("InputToken :"+inputToken);
		galleries = Check.getGalleries(inputToken);
		
		//Retrieve userName and galleriesList from database
	
		if(galleries==null)
			throw new IllegalGalleryException("isToken_ButNoGalleries!!!");
		// TODO Auto-generated constructor stub
		String method = request.getMethod();
		String version = API.split("/")[1];
		logger.info("version: "+version);
		if(!version.equals("v0")&&!version.equals("v1"))
			throw new IllegalAPIException("bad_version");
		API = API.substring(3);
		while(true) {
			if(API.equals("/detect")) {
				API = "/"+version+"/detect";
				logger.info("CHECK_API :"+API);
				break;
			}
			if(API.equals("/verify")) {
				API = "/"+version+"/verify";
				logger.info("CHECK_API :"+API);
				break;
			}
			if(API.equals("/identify")) {
				API = "/"+version+"/identify";
				logger.info("CHECK_API :"+API);
				break;
			}
			if(API.equals("/face")&&method.equals("POST")) {
				API = "/"+version+"/face";
				logger.info("CHECK_API :"+API);
				request.setAttribute("galleries", galleries);
				request.setAttribute("api", "face/post");
				request.setAttribute("userName", userName);
				break;
			}
			if(API.startsWith("/face/meta")){
					API = "/"+version+"/face/meta"+API.split("meta")[1];
				logger.info("CHECK_API :"+API);
			break;
			}
			if(API.startsWith("/face/gallery")&&API.indexOf("meta")!=-1) {
				String galleryName = API.split("gallery/")[1].split("/meta")[0];
				if(galleries.contains(galleryName)) {
					API = "/"+version+"/face/gallery/"+galleryName+"/meta"+API.split("meta")[1];
				}else {
					throw new IllegalGalleryException("bad_gallery");
				}
				StringBuilder api = new StringBuilder(API);
				String nextPage = request.getParameter("max_id");
				String prePage = request.getParameter("min_id");
				
				if(nextPage!=null&&!nextPage.equals(""))
					api.append("?max_id=").append(nextPage);
				if(prePage!=null&&!prePage.equals(""))
					api.append("?min_id=").append(prePage);
				API = api.toString();
				
				logger.info("CHECK_API:"+API);
				break;
			}	
			if(API.startsWith("/meta/gallery")){
				String galleryName = API.split("gallery/")[1];
				if(galleries.contains(galleryName)) {
					API = "/"+version+"/meta/gallery/"+galleryName;
				}else {
					throw new IllegalGalleryException("bad-gallery");
				}
				logger.info("CHECK_API :"+API);
			break;
			}
			if(API.equals("/meta")){
				API = "/"+version+"/meta/gallery/"+userName;
				logger.info("CHECK_API :"+API);
			break;
			}
			if(API.startsWith("/face/id")){
				String id = API.split("id")[1];
				boolean isMaster = false;
				List<String> list = new Check().checkId(id);
				if(list==null)
					throw new IllegalIDException("bad-id");
				Iterator<String> iterator = list.iterator();
				while(iterator.hasNext()) {
					if(galleries.contains((String)iterator.next())) {
						isMaster = true;
						break;
					}
				}
				if (isMaster) {
					API = "/"+version+"/face/id"+id;
					logger.info("CHECK_API :"+API);
					logger.info(API);
				}else {
						throw new IllegalIDException("bad_id");
				}
			break;
			}
			if(API.startsWith("/faces/gallery")) {
				String inputGallery = API.split("gallery/")[1]; 
				boolean existGallery = galleries.contains(inputGallery);
				if(existGallery) {
					API = "/"+version+"/faces/gallery/"+inputGallery;
				}else {
					throw new IllegalGalleryException("bad_gallery");
				}
				StringBuffer api = new StringBuffer(API);
				String nextPage = request.getParameter("max_id");
				String prePage = request.getParameter("min_id");
				
				if(nextPage!=null&&!nextPage.equals(""))
					api.append("?max_id=").append(nextPage);
				if(prePage!=null&&!prePage.equals(""))
					api.append("?min_id=").append(prePage);
				API = api.toString();
				logger.info("CHECK_API :"+API);
				break;
			}	
			if(API.equals("/faces")) {
				StringBuilder api = new StringBuilder("/"+version+"/faces/gallery/"+userName);
				String nextPage = request.getParameter("max_id");
				String prePage = request.getParameter("min_id");
				if(nextPage!=null&&!nextPage.equals(""))
					api.append("?max_id=").append(nextPage);
				if(prePage!=null&&!prePage.equals(""))
					api.append("?min_id=").append(prePage);
				API = api.toString();
				logger.info("CHECK_API :"+API);
				break;
			}
			
			throw new IllegalAPIException("bad_api");
		}
		request.setAttribute("API", API);
	}
}
