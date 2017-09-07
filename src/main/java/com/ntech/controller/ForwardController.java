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

    @Autowired
    private HttpServletRequest request;

    private final static Logger logger = Logger.getLogger(ForwardController.class);

    @RequestMapping("/n-tech/**")
    @ResponseBody
    public String methodHandler(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes)
            throws ServletException, IOException {
//        HashMap map = (HashMap) redirectAttributes.getFlashAttributes();
        if (redirectAttributes.containsAttribute("request") && redirectAttributes.containsAttribute("response")) {
            request = (HttpServletRequest) redirectAttributes.getFlashAttributes().get("request");
            response = (HttpServletResponse) redirectAttributes.getFlashAttributes().get("response");

        }
//        HttpServletRequest request1= redirectAttributes.getFlashAttribute("request");
        String reply = "";
//        PrintWriter out = response.getWriter();
        String method = request.getMethod();
        if (method.equals("PUT") || method.equals("DELETE")) {
            reply = MethodUtil.getInstance().requestForword(request, response);
            if (ErrorPrompt.size() != 0)
                reply = ErrorPrompt.getJSONInfo();
        } else {
            reply = MethodUtil.getInstance().requestForword(request, response);
            if (ErrorPrompt.size() != 0)
                reply = ErrorPrompt.getJSONInfo();
        }

        return reply;
    }

    @RequestMapping("/n-tech/picture/**")
    @ResponseBody
    public void pictureHandler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream outputStream = response.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        List<Byte> reply = null;
        try {
            reply = PictureForward.getInstance().requestForward(request, response);
        } catch (ErrorTokenException e) {
            ErrorPrompt.addInfo("error"+(ErrorPrompt.size()+1),"bad_master");
            e.printStackTrace();
        }
        if(reply==null||ErrorPrompt.size()!=0) {
            response.setContentType("application/json");
            outputStream.write(ErrorPrompt.getJSONInfo().getBytes());
            return;
        }
        response.setContentType("image");
        response.setHeader("Accept-Ranges", "bytes");
        for(int b:reply)
            dataOutputStream.write(b);
        dataOutputStream.close();
    }
}