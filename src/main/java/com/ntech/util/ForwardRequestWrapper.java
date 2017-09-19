package com.ntech.util;


import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

import com.ntech.exception.ErrorTokenException;
import com.ntech.exception.IllegalAPIException;
import com.ntech.exception.IllegalIDException;
import com.ntech.forward.Constant;
import com.ntech.exception.IllegalGalleryException;

public class ForwardRequestWrapper extends HttpServletRequestWrapper {

	private static Check check = (Check) Constant.GSB.getBean("check");
	
	private static Logger logger = Logger.getLogger(ForwardRequestWrapper.class);

	public ForwardRequestWrapper(HttpServletRequest request) throws ErrorTokenException, IllegalAPIException {
		
		super(request);
		String userName ;
		if(request.getRequestURI().length()<9){
			throw new IllegalAPIException("bad_api");
		}
		String API = request.getRequestURI().split(Constant.PATH)[1];
		String inputToken = request.getHeader("Token");
		if(inputToken==null||inputToken.equals(""))
			throw new ErrorTokenException("bad_token");
		/*String requestIP = request.getRemoteHost();
		logger.info("requestIP :"+requestIP);*/
		boolean isToken = check.checkToken(inputToken);
		if(!isToken)
			throw new ErrorTokenException("bad_token");
		request.setAttribute("inputToken",inputToken);
		logger.info("InputToken :"+inputToken);
		userName = check.getUserName(inputToken);
		logger.info("UserName: "+userName);
		request.setAttribute("userName",userName);
		if(API.startsWith("/picture")) {
			return;
		}

		/*galleries = check.getGalleries(inputToken);*/


		//Retrieve userName and galleriesList from database

	/*	if(galleries==null||galleries.size()==0)
			throw new IllegalGalleryException("isToken_ButNoGalleries!!!");*/
		/*String method = request.getMethod();*/
		/*String version = API.split("/")[1];
		logger.info("version: "+version);
		if(!version.equals("v0")&&!version.equals("v1"))
			throw new IllegalAPIException("bad_version");
		request.setAttribute("version",version);*/
	/*	API = API.substring(3);
		while(true) {
		try {
			if (API.equals("/detect")) {
				request.setAttribute("chargeAPI", "detect");
				request.setAttribute("charge", Constant.Detect);
				API = new StringBuilder("/").append(version).append("/detect").toString();
				logger.info("CHECK_API :" + API);
				break;
			}
			if (API.equals("/verify")) {
				request.setAttribute("chargeAPI", "verify");
				request.setAttribute("charge", Constant.Verify);
				API = new StringBuilder("/").append(version).append("/verify").toString();
				logger.info("CHECK_API :" + API);
				break;
			}
			if (API.equals("/identify")) {
				request.setAttribute("chargeAPI", "identify");
				request.setAttribute("charge", Constant.Identify);
				API = new StringBuilder("/").append(version).append("/faces/gallery/").append(userName).append("/identify").toString();
				logger.info("CHECK_API :" + API);
				break;
			}
			if (API.startsWith("/identify/gallery")) {
				request.setAttribute("chargeAPI", "identify");
				request.setAttribute("charge", Constant.Identify);
				String gallery = API.split("gallery/")[1];
				if(!gallery.equals(userName))
					gallery = new StringBuilder(gallery).append("_anytec_" + userName).toString();
				if (!galleries.contains(gallery))
					throw new IllegalGalleryException("bad_gallery");
				API = new StringBuilder("/").append(version).append("/faces/gallery/").append(gallery).append("/identify").toString();
				logger.info("CHECK_API :" + API);
				break;
			}
			if (API.equals("/face") && method.equals("POST")) {
				request.setAttribute("chargeAPI", "face");
				request.setAttribute("charge", Constant.Face);
				API = new StringBuilder("/").append(version).append("/face").toString();
				logger.info("CHECK_API :" + API);
				request.setAttribute("galleries", galleries);
				request.setAttribute("api", "face/post");
				break;
			}
			if (API.startsWith("/face/meta")) {
				String meta = API.split("meta")[1];
				StringBuilder api = new StringBuilder("/").append(version).append("/face/gallery/").append(userName).append("/meta").append(meta);
				String nextPage = request.getParameter("max_id");
				String prePage = request.getParameter("min_id");
				if (nextPage != null && !nextPage.equals(""))
					api.append("?max_id=").append(nextPage);
				if (prePage != null && !prePage.equals(""))
					api.append("?min_id=").append(prePage);
				API = api.toString();
				logger.info("CHECK_API :" + API);
				break;
			}
			if (API.startsWith("/face/gallery") && API.contains("/meta")) {
				String galleryName = API.split("gallery/")[1].split("/meta")[0];
				if(!galleryName.equals(userName))
					galleryName = new StringBuilder(galleryName).append("_anytec_" + userName).toString();
				StringBuilder api;
				if (galleries.contains(galleryName)) {
					api = new StringBuilder("/").append(version).append("/face/gallery/").append(galleryName).append("/meta").append(API.split("meta")[1]);
				} else {
					throw new IllegalGalleryException("bad_gallery");
				}
				String nextPage = request.getParameter("max_id");
				String prePage = request.getParameter("min_id");

				if (nextPage != null && !nextPage.equals(""))
					api.append("?max_id=").append(nextPage);
				if (prePage != null && !prePage.equals(""))
					api.append("?min_id=").append(prePage);
				API = api.toString();

				logger.info("CHECK_API:" + API);
				break;
			}
			if (API.startsWith("/meta/gallery")) {
				String galleryName = API.split("gallery/")[1];
				if(!galleryName.equals(userName))
					galleryName = new StringBuilder(galleryName).append("_anytec_" + userName).toString();
				if (galleries.contains(galleryName)) {
					API = new StringBuilder("/").append(version).append("/meta/gallery/").append(galleryName).toString();
				} else {
					throw new IllegalGalleryException("bad-gallery");
				}
				logger.info("CHECK_API :" + API);
				break;
			}
			if (API.equals("/meta")) {
				API = new StringBuilder("/").append(version).append("/meta/gallery/").append(userName).toString();
				logger.info("CHECK_API :" + API);
				break;
			}
			if (API.startsWith("/face/id")) {
				String id = API.split("id/")[1];
				boolean isMaster = false;
				List<String> list = check.checkId(id);
				if (list == null)
					break;
				Iterator<String> iterator = list.iterator();
				while (iterator.hasNext()) {
					if (galleries.contains((String) iterator.next())) {
						isMaster = true;
						break;
					}
				}
				if (isMaster) {
					API = new StringBuilder("/").append(version).append("/face/id/").append(id).toString();
					logger.info("CHECK_API :" + API);
					logger.info(API);
				} else {
					throw new IllegalIDException("bad_id");
				}
				break;
			}
			if (API.startsWith("/faces/gallery")) {
				String inputGallery = API.split("gallery/")[1];
				if(!inputGallery.equals(userName))
					inputGallery = new StringBuilder(inputGallery).append("_anytec_" + userName).toString();
				boolean existGallery = galleries.contains(inputGallery);
				StringBuilder api;
				if (existGallery) {
					api = new StringBuilder("/").append(version).append("/faces/gallery/").append(inputGallery);
				} else {
					throw new IllegalGalleryException("bad_gallery");
				}
				String nextPage = request.getParameter("max_id");
				String prePage = request.getParameter("min_id");

				if (nextPage != null && !nextPage.equals(""))
					api.append("?max_id=").append(nextPage);
				if (prePage != null && !prePage.equals(""))
					api.append("?min_id=").append(prePage);
				API = api.toString();
				logger.info("CHECK_API :" + API);
				break;
			}
			if (API.equals("/faces")) {
				StringBuilder api = new StringBuilder("/").append(version).append("/faces/gallery/").append(userName);
				String nextPage = request.getParameter("max_id");
				String prePage = request.getParameter("min_id");
				if (nextPage != null && !nextPage.equals(""))
					api.append("?max_id=").append(nextPage);
				if (prePage != null && !prePage.equals(""))
					api.append("?min_id=").append(prePage);
				API = api.toString();
				logger.info("CHECK_API :" + API);
				break;
			}
			if ("GET".equals(method) && API.equals("/galleries")) {
				API = "allGalleries";
				request.setAttribute("allGalleries", galleries);
				logger.info("CHECK_API :" + API);
				break;
			}
			if ("POST".equals(method) && API.startsWith("/galleries")) {
				if (galleries.size() > 4)
					throw new IllegalAPIException("too many galleries");
				String createGallery = API.split("/")[2];
				createGallery = new StringBuilder(createGallery).append("_anytec_" + userName).toString();
				if (galleries.contains(createGallery))
					throw new IllegalAPIException("already exist");
				boolean result = check.createGallery(userName, createGallery);
				logger.info("createResult: " + result);
				request.setAttribute("charge", Constant.Gallery);
				API = "/v0/galleries/" + createGallery;
				logger.info("CHECK_API :" + API);
				break;
			}
			if ("DELETE".equals(method) && API.startsWith("/galleries")) {
				String deleteGallery = API.split("/")[2];
				deleteGallery = new StringBuilder(deleteGallery).append("_anytec_" + userName).toString();
				if (!galleries.contains(deleteGallery))
					throw new IllegalGalleryException("bad_gallery");
				boolean result = check.deleteGallery(userName, deleteGallery);
				logger.info("deleteResult: " + result);
				API = "/v0/galleries/" + deleteGallery;
				logger.info("CHECK_API :" + API);
				break;
			}
		}catch (ArrayIndexOutOfBoundsException e){
			throw new IllegalAPIException("bad_api");
		}
			throw new IllegalAPIException("bad_api");
		}
		request.setAttribute("API", API);*/
	}
}
