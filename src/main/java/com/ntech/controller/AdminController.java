package com.ntech.controller;

import com.ntech.service.inf.ICustomerService;
import com.ntech.service.inf.ILibraryService;
import com.ntech.service.inf.ISetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    ICustomerService customerService;

    @Autowired
    ILibraryService libraryService;

    @Autowired
    ISetMealService setMealService;

    @RequestMapping("loginCheck")
    @ResponseBody
    public String loginCheck(String name,String password){
        if(null==name||"".equals(password)||null==password||"".equals(password)){
            return "登录信息错误";
        }
        return null;
    }
}
