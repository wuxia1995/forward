package com.ntech.forward;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.ntech.exception.IllegalGalleryException;
import com.ntech.util.ConfigManager;
import com.ntech.util.Encrypt;
import com.ntech.util.ErrorPrompt;

public class MethodUtil {
	private static MethodUtil instance;
	private static Logger logger = Logger.getLogger(MethodUtil.class);
	private static FileItemFactory factory = new DiskFileItemFactory();
	private static String BOUNDARY = "---------------------------"+System.currentTimeMillis();
	private Map<String ,String> header = new HashMap<String,String>();
	private Map<String,String> param = new HashMap<String,String>();
	private Map<String,Object> file = new HashMap<String,Object>();
	private List<String> galleries = null;
	private MethodUtil() {
	}
	public static MethodUtil getInstance(){    //对获取实例的方法进行同步
	      if (instance == null){
	          synchronized(MethodUtil.class){
	             if (instance == null)
	                 instance = new MethodUtil(); 
	          }
	      }
     return instance;
	}
	 
	public String requestForword(HttpServletRequest request,
			HttpServletResponse response)  {
		logger.info("*************START***********");
		String SDKreply="";
		String meta = "no";
		String API = (String) request.getAttribute("API");
		String api = (String) request.getAttribute("api");
		header.clear();
		param.clear();
		file.clear();
		if(api!=null&&api.equals("face/post")) {
			galleries = (List<String>) request.getAttribute("galleries");
			String inputGalleries = request.getParameter("galleries");
			if(galleries.size()!=0&&(inputGalleries==null||inputGalleries.equals("")))
				param.put("galleries",(String) request.getAttribute("userName"));
		}
		//判断是否有文件输入
		boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
		if(isMultiPart) {
			logger.info("********* HAS FILE********");
			meta = "isFile";
			header.put("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iterator = items.iterator();
				while(iterator.hasNext()) {
					FileItem item = iterator.next();
					if(item.isFormField()) {
						String filedName = item.getFieldName();
						String value = new String(item.getString().getBytes("iso-8859-1"));
						if(filedName.equals("galleries")||filedName.equals("gallery")) {
							logger.info("inputGaleries :"+value);
							if(value.indexOf((String)request.getAttribute("userName"))==-1) {
								value = value.concat(","+request.getAttribute("userName"));
							}
							logger.info("inputGaleries :"+value);
							filedName = "galleries";
							boolean existGallery = false;
							if(galleries!=null&&galleries.size()!=0) {
								logger.info("*******USER_HOLD_GALLERY*******");
								String[] inputGalleries = null;
								if (value.indexOf(",")!=-1) {
									logger.info("inputMoreGalleries");
									inputGalleries = value.split(","); 
									for(int i=0;i<inputGalleries.length;i++) {
										logger.info(inputGalleries[i]);
										existGallery = galleries.contains(inputGalleries[i]);
										if(!existGallery)
											throw new IllegalGalleryException("BAD_GALLERIES");
										logger.info("FACE_GALLERIES_CHECK_RESULT :"+existGallery);
									}
								}else {
									logger.info("inputOneGallery");
									existGallery = galleries.contains(value);
									logger.info("FACE_GALLERIES_CHECK_RESULT :"+existGallery);
								}
							}
							if(existGallery)
								param.put("galleries",value);
						}
						param.put(filedName, value);
						logger.info("文本域："+filedName+"——"+value);
					}else {
						file.put("contentType",item.getContentType());
						logger.info("contentType:"+item.getContentType());
						String filedName = item.getFieldName();
						String fileName = item.getName();
						logger.info("文件控件: "+filedName+"--"+fileName);
						if(item.getSize()>1024*1024*10) {
							logger.info("*******上传图片过大*******");
						}
						byte[] pic = item.get();
						logger.info("PICTURE.LENGTH:"+pic.length);
						
						file.put(filedName+":"+fileName, pic);
					}	
				}
			} catch (FileUploadException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			
				return null;
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				return null;
			} catch (IllegalGalleryException e) {
				logger.error("*****BAD_GALLERY*****@"+request.getAttribute("userName"));
				ErrorPrompt.addInfo("error"+(ErrorPrompt.size()+1), "bad_gallery");
				e.printStackTrace();
				return null;
			} 
		}else {
			header.put("Content-Type", "application/json");
		}
		//获取表单文本数据
		@SuppressWarnings("unchecked")
		Enumeration<String> enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements()) {
			logger.info("*********HAS INPUT*********");
			meta = "yes";
			String name = enumeration.nextElement();
			if((name.equals("max_id")||name.equals("min_id"))&&request.getMethod().equals("GET")) {
				meta = "no";
			}else {
				param.put(name,request.getParameter(name));
			}
			logger.info("PARAM:"+name+"--"+""+param.get(name));
		}
		//转发和设定报头信息
		header.put("Method",request.getMethod());
		header.put("Authorization",Constant.TOKEN);
		
		String localAPI = (String) request.getAttribute("localAPI");
		if(localAPI==null||localAPI.equals("")) {
			header.put("API",API);
		}else {
			header.put("API",localAPI);
		}
		logger.info((String)request.getAttribute("API"));
		logger.info("Token :"+request.getHeader("Token"));
		try {
			SDKreply = HttpUploadFile.getInstance().httpURLConnectionSDK(header, param, file, meta);
		} catch (IOException e) {
			ErrorPrompt.addInfo("error"+(ErrorPrompt.size()+1),"bad_input");
        	logger.error(e.getMessage()+"@"+request.getAttribute("userName"));
            e.printStackTrace();
            return null;
        }
		String string = ConfigManager.getInstance().getParameter("PICTURE")+"/"+Encrypt.encryptUserName((String)request.getAttribute("userName"));
		if(SDKreply!=null||!SDKreply.equals("")) {
			return SDKreply.replaceAll("http://127.0.0.1:3333/uploads",string);
		}
		return null;
	}
}
