package com.forward.util;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

public class ConnectionSDK {

    private static Logger logger = Logger.getLogger(ConnectionSDK.class);
    String BOUNDARY = "---------------------------" + System.currentTimeMillis();

    private static final String POST_URL = "http://192.168.10.208:8000";

//    public static void main(String[] args) {
//        Map<String, String> header = new HashMap<String, String>();
//        Map<String, String> param = new HashMap<String, String>();
//        Map<String, Object> file = new HashMap<String, Object>();
//        List<String> list = new ArrayList<String>();
//        header.put("Method", "POST");
//        header.put("Authorization", "Hhhzp023j1nckca9OsauXr-T4Onmf7Bp");
//        header.put("Content-Type", "application/json");
//        header.put("API", "/v0/verify");
//        param.put("photo1", "http://static.findface.pro/sample.jpg");
//        param.put("photo2", "http://static.findface.pro/sample.jpg");
//        String string = new ConnectionSDK().httpURLConnectionPOST(header, param, file, "yes");
//        System.out.println("测试结果" + string);
//    }

    public String httpURLConnectionPOST(Map<String, String> header, Map<String, String> param
            , Map<String, Object> file, String meta) {
        //测试×××××××××××××××××××××××××××××××
        Iterator<Entry<String, String>> iterator1 = header.entrySet().iterator();
        Iterator<Entry<String, String>> iterator2 = param.entrySet().iterator();
        Iterator<Entry<String, Object>> iterator3 = file.entrySet().iterator();
        while (iterator1.hasNext()) {
            Entry<String, String> entry = iterator1.next();
            logger.info(entry.getKey() + ":" + entry.getValue());
        }
        while (iterator2.hasNext()) {
            Entry<String, String> entry = iterator2.next();
            logger.info(entry.getKey() + ":" + entry.getValue());
        }
        while (iterator2.hasNext()) {
            Entry<String, Object> entry = iterator3.next();
            logger.info(entry.getKey() + ":" + entry.getValue());
        }
        //×××××××××××××××××××××××××××××××××××
        URL url;
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            url = new URL(POST_URL + header.get("API"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestMethod(header.get("Method"));
            connection.setRequestProperty("Content-Type", header.get("Content-Type"));
            connection.setRequestProperty("Authorization", "Token " + header.get("Authorization"));
            if (meta.equals("yes")) {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                JSONObject.writeJSONString(param, bufferedWriter);
                bufferedWriter.close();
            } else if (meta.equals("isFile")) {
                OutputStream out = new DataOutputStream(connection.getOutputStream());
                if (param.size() != 0) {
                    StringBuffer strBuf = new StringBuffer();
                    Iterator<Entry<String, String>> iterator = param.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, String> entry = iterator.next();
                        String inputName = entry.getKey();
                        logger.info(inputName);
                        String inputValue = (String) entry.getValue();
                        logger.info(inputValue);
                        if (inputValue == null) {
                            continue;
                        }
                        strBuf.append("\r\n").append("--").append(BOUNDARY)
                                .append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\""
                                + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    }
                    out.write(strBuf.toString().getBytes());
                }
                // file
                if (file.size() != 0) {
                    logger.info("fileMapSize:" + file.size());
                    Iterator<Entry<String, Object>> iter = file.entrySet().iterator();
                    while (iter.hasNext()) {
                        Entry<String, Object> entry = iter.next();
                        String filed = entry.getKey();
                        if (filed.equals("contentType"))
                            continue;
                        String filedName = filed.split(":")[0];
                        String fileName = filed.split(":")[1];
                        byte[] fileBody = (byte[]) entry.getValue();
                        if (fileBody.length == 0) {
                            continue;
                        }
                        //没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
                        String contentType = (String) file.get("contentType");

                        if (contentType == null || "".equals(contentType)) {
                            contentType = "application/octet-stream";
                        }
                        StringBuffer strBuf = new StringBuffer();
                        strBuf.append("\r\n").append("--").append(BOUNDARY)
                                .append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\""
                                + filedName + "\"; filename=\"" + fileName
                                + "\"\r\n");
                        strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                        out.write(strBuf.toString().getBytes());
                        out.write(fileBody);
                    }
                }
                byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
                out.write(endData);
                out.flush();
                out.close();
            }
            logger.info("#########--" + connection.getResponseCode());
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
                bufferedReader = new BufferedReader(inputStream);
                String lines;
                stringBuffer = new StringBuffer("");
                while ((lines = bufferedReader.readLine()) != null) {
                    lines = new String(lines.getBytes(), "utf-8");
                    stringBuffer.append(lines);
                }
            }
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }
}
