package com.ntech.controller;

import com.ntech.service.inf.ICustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger logger = Logger.getLogger(CustomerController.class);

    @Autowired
    ICustomerService customerService;

    @RequestMapping("/register")
    public String jumpToRegister(){
        logger.info("jump to register page");
        return "register";
    }
    @RequestMapping("/commitReg")
    public String register(){
        return null;
    }

    @RequestMapping("/all")
    public void addCustomer(){
        System.out.println(customerService.findAll());
    }
}
