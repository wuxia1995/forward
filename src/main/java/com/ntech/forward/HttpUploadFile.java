package com.ntech.forward;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.ntech.exception.BadInputException;
import com.ntech.util.ErrorPrompt;

/**
 * java通过模拟post方式提交表单实现图片上传功能
 */
public class HttpUploadFile {
	
	private static HttpUploadFile instance;
	private static final String POST_URL = Constant.SDK_IP;
	private static Logger logger = Logger.getLogger(HttpUploadFile.class);
	private HttpURLConnection connection = null;
    private BufferedWriter bufferedWriter = null;
    private BufferedReader bufferedReader = null;
    private URL url = null;
    private OutputStream out = null;
	
	private HttpUploadFile() {}
	  public static HttpUploadFile getInstance(){    //对获取实例的方法进行同步
	         if (instance == null){
	             synchronized(HttpUploadFile.class){
	                if (instance == null)
	                    instance = new HttpUploadFile(); 
	             }
	         }
	       return instance;
	       }
	
    public synchronized String httpURLConnectionSDK( Map<String,String> header,Map<String, String> param,
            Map<String, Object> file,String meta) throws IOException {
    	//测试×××××××××××××××××××××××××××××××
    	logger.info("META: "+meta);
    		Iterator<Entry<String,String>>iterator1 = header.entrySet().iterator();
    		while(iterator1.hasNext()) {
    			Entry<String, String> entry = iterator1.next();
    			logger.info(entry.getKey()+":"+entry.getValue());
    		}
    	if(param!=null) {
    		Iterator<Entry<String,String>>iterator2 = param.entrySet().iterator();
    		while(iterator2.hasNext()) {
    			Entry<String, String> entry = iterator2.next();
    			logger.info(entry.getKey()+":"+entry.getValue());
    		}
    	}
     	if(file!=null) {
    		Iterator<Entry<String,Object>>iterator3 = file.entrySet().iterator();
    		while(iterator3.hasNext()) {
    			Entry<String, Object> entry = iterator3.next();
    			logger.info(entry.getKey()+":"+entry.getValue());
    		}
    	}
		//×××××××××××××××××××××××××××××××××××		
        String reply = "";
        String BOUNDARY = "-------------------------"+System.currentTimeMillis(); 
      
            url = new URL(POST_URL+header.get("API"));
            logger.info("URL :"+url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(6000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Connection","keep-alive");
            connection.setRequestMethod(header.get("Method"));
            connection.setRequestProperty("Authorization", "Token "+header.get("Authorization"));
            if(meta.equals("yes")) {
            	logger.info("META=YES");
            	connection.setRequestProperty("Content-Type","application/json");
	        	bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
		        JSONObject.writeJSONString(param, bufferedWriter);
		        bufferedWriter.close();
	        }else if(meta.equals("isFile")) {
	        	logger.info("META=ISFILE");
	            connection.setRequestProperty("Connection", "Keep-Alive");
	            connection.setRequestProperty("Content-Type",
	                    "multipart/form-data; boundary=" + BOUNDARY);
	            out = new DataOutputStream(connection.getOutputStream());
	            // text
	            if (param.size()!=0) {
	            	logger.info("******** FILE-TEXT ********");
	                StringBuffer stringBuffer = new StringBuffer();
	                Iterator<Entry<String, String>> iterator = param.entrySet().iterator();
	                while (iterator.hasNext()) {
	                    Entry<String, String> entry = iterator.next();
	                    String inputName = entry.getKey();
	                    String inputValue = entry.getValue();
	                    logger.info(inputName+":"+inputValue);
	                    if (inputValue == null) {
	                        continue;
	                    }
	                    stringBuffer.append("\r\n").append("--").append(BOUNDARY)
	                            .append("\r\n");
	                    stringBuffer.append("Content-Disposition: form-data; name=\""
	                            + inputName + "\"\r\n\r\n");
	                    stringBuffer.append(inputValue);
	                }
	                out.write(stringBuffer.toString().getBytes());
	            }
	            // file
	            if (file.size()!=0) {
	            	logger.info("******** FILE-PICTURE *******");
	                Iterator<Entry<String,Object>> iter = file.entrySet().iterator();
	                while (iter.hasNext()) {
	                    Entry<String, Object> entry = iter.next();
	                    String filed =  entry.getKey();
	                    if(filed.equals("contentType"))
	                    	   continue;
	                    String filedName = filed.split(":")[0];
	                    String fileName = filed.split(":")[1];
	                    if (entry.getValue() == null) {
	                        continue;
	                    }
	                    //没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
	                    String contentType = (String) file.get("contentType");
	                    logger.info(fileName+"--CONTENTTYPE:"+contentType);
	                    if (contentType == null || "".equals(contentType)) {
	                        contentType = "application/octet-stream";
	                    }
	                    StringBuffer strBuf = new StringBuffer();
	                    strBuf.append("\r\n").append("--").append(BOUNDARY)
	                            .append("\r\n");
	                    strBuf.append("Content-Disposition: form-data; name=\""
	                            + filedName + "\"; filename=\"" + filedName
	                            + "\"\r\n");
	                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
	                    out.write(strBuf.toString().getBytes());
	                    byte[] pic = (byte[]) entry.getValue();
	                    out.write(pic);
	                }
	            }
	            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
	            out.write(endData);
	            out.flush();
	            out.close();
	        } 
            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            int code = connection.getResponseCode();
            if(code!=200)
            	logger.error("ERROR_CODE: "+code);
            logger.info("HTTP_CODE: "+code);
            	bufferedReader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            reply = strBuf.toString();
            bufferedReader.close();
            if (connection != null) {
                connection.disconnect();
            }
        
        return reply;
    }
}