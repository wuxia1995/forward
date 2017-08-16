package com.forward.controller;

import com.forward.service.inf.IUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by niu on 17-3-26.
 */
@Controller
@RequestMapping("user")
public class UserController {

    private final static Logger logger = Logger.getLogger(UserController.class);

    @Resource
    IUserService userService;



    @RequestMapping("test")
    public void test(){
        System.out.println(userService.getAllUser());

    }
}
