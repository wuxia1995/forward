package com.ntech.controller;

import com.ntech.exception.PermissionDeniedException;
import com.ntech.forward.MethodUtil;
import com.ntech.forward.PutUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ForwardController {

    @Autowired
    private HttpServletRequest request;

    private final static Logger logger = Logger.getLogger(ForwardController.class);

//    @RequestMapping("/index")
//    public String indexControl(){
//        return "index.html";
//    }
//    @RequestMapping("face")
//    @ResponseBody
//    public Map testRequest(){
//        logger.info("start testRequest method");
//        logger.info(request.toString());
//        logger.info(request.getRequestURI());
//        logger.info(request.getServletPath());
//        logger.info("end testRequest method");
//        HashMap test = new HashMap();
//        test.put("aa","bb");
//        test.put("bb","bb");
//        test.put("cc","bb");
//        test.put("ccc","bb");
//        return test;
//    }
//    @RequestMapping("/user/register")
//    public String register(){
//        logger.info("register");
//        return "register";
//    }

//    //人脸探测和对比体验
//    @RequestMapping(value = {"detect"})
//    public String detectFace(){
//        return "show-detect";
//    }
//    @RequestMapping(value = {"verify"})
//    public String verifyFace(){
//        return "show-verify";
//    }

    @RequestMapping("/n-tech/**")
    @ResponseBody
    public String methodHandler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reply="";
//        PrintWriter out = response.getWriter();
        String method = request.getMethod();
        if(method.equals("PUT")||method.equals("DELETE")){
            reply = PutUtil.requestForword(request,response);
        }else{
            try {
                reply = MethodUtil.requestForword(request, response);
            } catch (PermissionDeniedException e) {
                e.printStackTrace();
                logger.info("BAD_GALLERY");
            }
        }
        if(reply.equals(""))
            reply="BAD_INPUT";
        return reply;
    }
}
