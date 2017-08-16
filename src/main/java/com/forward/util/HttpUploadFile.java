package com.forward.util;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * java通过模拟post方式提交表单实现图片上传功能
 */
public class HttpUploadFile {
	private static final String POST_URL = "http://192.168.10.208:8000";
	private static Logger logger = Logger.getLogger(HttpUploadFile.class);
    public static void main(String[] args) {
    
        testUploadImage();
    }
    public static void testUploadImage(){
        String url = "http://192.168.10.208:8000/v0/detect";
        String fileName = "/home/testPic/timg.jpeg";
        Map<String, String> header = new HashMap<String, String>();
        header.put("API", "/v0/detect");
        header.put("Method", "POST");
        header.put("Authorization", "Hhhzp023j1nckca9OsauXr-T4Onmf7Bp");
        Map<String, String> param = new HashMap<String,String>();
        
        /*param.put("photo", "http://static.findface.pro/sample.jpg");*/
        //设置file的name，路径
        Map<String, Object> file = new HashMap<String, Object>();
        file.put("photo", fileName);
        String contentType = "";//image/png
        String ret = new HttpUploadFile().forwardRequest(header, param, file,"isFile");
        System.out.println(ret);
    }

    /**
     * 上传图片
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @param contentType 没有传入文件类型默认采用application/octet-stream
     * contentType非空采用filename匹配默认的图片类型
     * @return 返回response数据
     */
    @SuppressWarnings("rawtypes")
    public synchronized String forwardRequest( Map<String,String> header,Map<String, String> param,
            Map<String, Object> file,String meta) {
        String reply = "";
        String BOUNDARY = "---------------------------"+System.currentTimeMillis(); 
        HttpURLConnection connection = null;
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(POST_URL+header.get("API"));
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(3000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(header.get("Method"));
            connection.setRequestProperty("Authorization", "Token "+header.get("Authorization"));
            if(meta.equals("yes")) {
            	connection.setRequestProperty("Content-Type","application/json");
	        	bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
		        JSONObject.writeJSONString(param, bufferedWriter);
		        bufferedWriter.close();
	        }else if(meta.equals("isFile")) {
	            connection.setRequestProperty("Connection", "Keep-Alive");
	            connection.setRequestProperty("Content-Type",
	                    "multipart/form-data; boundary=" + BOUNDARY);
	            OutputStream out = new DataOutputStream(connection.getOutputStream());
	            // text
	            if (param.size()!=0) {
	                StringBuffer stringBuffer = new StringBuffer();
	                Iterator<Entry<String, String>> iterator = param.entrySet().iterator();
	                while (iterator.hasNext()) {
	                    Entry<String, String> entry = iterator.next();
	                    String inputName = entry.getKey();
	                    String inputValue = entry.getValue();
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
	                   /* File path = new File(inputValue);
	                    String filename = path.getName();*/
	                    
	                    //没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
	                    String contentType = (String) file.get("contentType");
	                 
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
	                    /*DataInputStream in = new DataInputStream(
	                            new FileInputStream(path));
	                    int bytes = 0;
	                    byte[] bufferOut = new byte[1024*1024*10];
	                    while ((bytes = in.read(bufferOut)) != -1) {
	                        out.write(bufferOut, 0, bytes);
	                    }
	                    in.close();*/
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
            bufferedReader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            reply = strBuf.toString();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return reply;
    }
}