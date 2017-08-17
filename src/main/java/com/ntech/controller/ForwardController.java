package com.ntech.controller;

import com.ntech.util.MethodUtil;
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

    @RequestMapping("face")
    @ResponseBody
    public Map testRequest(){
        logger.info("start testRequest method");
        logger.info(request.toString());
        logger.info(request.getRequestURI());
        logger.info(request.getServletPath());
        logger.info("end testRequest method");
        HashMap test = new HashMap();
        test.put("aa","bb");
        test.put("bb","bb");
        test.put("cc","bb");
        test.put("ccc","bb");
        return test;
    }
    @RequestMapping("/n-tech/**")
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(request.getMethod());
        PrintWriter out = response.getWriter();
        String reply = MethodUtil.requestForword(request, response);
        out.println(reply);
    }
}
