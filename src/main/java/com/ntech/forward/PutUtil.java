package com.ntech.forward;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class PutUtil {
	
	private static Logger logger = Logger.getLogger(PutUtil.class);
	
	public static String requestForword(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		logger.info("METHOD:"+request.getMethod());
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		BufferedReader bufferedReader = null;
		InputStream inputStream = request.getInputStream();
		StringBuffer stringBuffer = new StringBuffer();
		Map<String,String> param = new HashMap<String,String>();
		Map<String,Object> body = new HashMap<String,Object>();
		String key = null;
		String value = null;
		//读取报文参数
		String meta = "no";
		if(inputStream.available()!=0) {
			logger.info("HAS PARAM");
			meta = "yes";
			int flag = -1;
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
			stringBuffer.append(bufferedReader.readLine());
			String putString = stringBuffer.toString();
			logger.info(putString);
			if((flag = putString.indexOf("&"))!=-1) {
				String[] paramString = putString.split("&");
				for(int i=0;i<paramString.length;i++) {
					key = paramString[i].split("=")[0];
					value = paramString[i].split("=")[1];
					if(key!=""&&key!=null&&value!=""&&value!=null)
					body.put(key, value);
					logger.info(key+":"+value);
				}
			}else {
				key = putString.split("=")[0];
				value = putString.split("=")[1];
				if(key!=""&&key!=null&&value!=""&&value!=null)
				body.put(key, value);
				logger.info(key+":"+value);
			}
		}	
			/*JSONParser jsonParser = new JSONParser();
			try {
				jsonObject = (JSONObject) jsonParser.parse(stringBuffer.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("ERROR:ParseException");
			}*/
			//转发报文参数
		/*	@SuppressWarnings("unchecked")
			Iterator<Entry<String,Object>> iterator = jsonObject.entrySet().iterator();
			while(iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				logger.info("PARAM---"+entry.getKey()+":"+entry.getValue());
				body.put(entry.getKey(), entry.getValue());
			}
		}*/
		//转发和设定报头信息
		param.put("Method",request.getMethod());
		param.put("Authorization",Constant.TOKEN);
		param.put("Content-Type", "application/json");
		param.put("API",request.getRequestURI().split("n-tech")[1]);
		
		String SDKreply =  ConnectionSDK.httpURLConnectionPOST(param, body,meta);
		
		return SDKreply;
	}
}
