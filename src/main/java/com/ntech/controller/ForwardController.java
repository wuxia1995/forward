package com.ntech.controller;

import com.ntech.exception.ErrorTokenException;
import com.ntech.forward.MethodUtil;
import com.ntech.forward.PictureForward;
import com.ntech.util.ErrorPrompt;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ForwardController {



    private final static Logger logger = Logger.getLogger(ForwardController.class);

    @RequestMapping("/n-tech/**")
    @ResponseBody
    public String methodHandler(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes)
            throws ServletException, IOException {
//
        String reply = "";
        String method = request.getMethod();
        reply = MethodUtil.getInstance().requestForword(request, response);
        if (ErrorPrompt.size() != 0)
            reply = ErrorPrompt.getJSONInfo();

        return reply;
    }

    @RequestMapping("/n-tech/picture/**")
    @ResponseBody
    public void pictureHandler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PictureForward.getInstance().requestForward(request,response);

    }
}