package com.ntech.controller;

import com.ntech.forward.MethodUtil;
import com.ntech.forward.PictureForward;
import com.ntech.util.ErrorPrompt;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class ForwardController {

    private final static Logger logger = Logger.getLogger(ForwardController.class);

    @RequestMapping("/n-tech/**")
    @ResponseBody
    public String methodHandler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("enter Controller");
        String reply;
        if(ErrorPrompt.size() != 0)
            return ErrorPrompt.getJSONInfo();
        reply = MethodUtil.getInstance().requestForword(request, response);
        if (ErrorPrompt.size() != 0)
            reply = ErrorPrompt.getJSONInfo();
        return reply;
    }

    @RequestMapping("/n-tech/picture/**")
    @ResponseBody
    public void pictureHandler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("enter Controller");
        PictureForward.getInstance().requestForward(request,response);

    }
}