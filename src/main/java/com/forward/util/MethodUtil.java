package com.forward.util;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


public class MethodUtil {
	
	static String BOUNDARY = "---------------------------"+System.currentTimeMillis(); 
	private static Logger logger = Logger.getLogger(MethodUtil.class);
	private static FileItemFactory factory = new DiskFileItemFactory();
	public static String requestForword(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		logger.info("*************START***********");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Map<String ,String> header = new HashMap<String,String>();
		Map<String,String> param = new HashMap<String,String>();
		Map<String,Object> file = new HashMap<String,Object>();
		String meta = "no";
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
						param.put(filedName, item.getString());
						logger.info("文本域："+filedName+"——"+item.getString());
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
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			param.put(name,request.getParameter(name));
			logger.info("PARAM:"+name+"--"+""+request.getParameter(name));
		}
		//转发和设定报头信息
		header.put("Method",request.getMethod());
		header.put("Authorization","Hhhzp023j1nckca9OsauXr-T4Onmf7Bp");
		/*param.put("Authorization","iqwpYL6jTEnnebA2WIYeluFZCtBV4kx3");*/
		header.put("API",request.getRequestURI().split(Constant.CONTEXT)[1]);
		
		/*String SDKreply = new ConnectionSDK().httpURLConnectionPOST(header, param, file, meta);*/
		String SDKreply = HttpUploadFile.forwardRequest(header, param, file, meta);
		
		return SDKreply;
	}
}
