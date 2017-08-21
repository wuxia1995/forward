package com.ntech.controller;

import com.ntech.model.Customer;
import com.ntech.service.inf.ICustomerService;
import com.ntech.util.SHAencrypt;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


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
    @RequestMapping("/check")
    @ResponseBody
    public Boolean checkUserName(@RequestParam String userName){
        logger.info("checkLogin");
        return customerService.checkUserName(userName);
    }
    @RequestMapping("/commitReg")
    public String register(@RequestParam("name") String name, String password, String email){
        logger.info("commit register");
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPassword(SHAencrypt.encryptSHA(password));
        customer.setEmail(email);
        customerService.add(customer);
        return "msg";

    }

    //登录
    @RequestMapping("loginCheck")
    @ResponseBody
    public boolean login(String name,String password){
        logger.info("login check");
        return customerService.loginCheck(name,password);
    }
    @RequestMapping("login")
    public String jumpLogin(){
        return "login";
    }

    @RequestMapping("index")
    public String customerInfo(String name,String password){
        if(customerService.loginCheck(name,password)){
            return "info";
        }
        return "msg";
    }

    @RequestMapping("/all")
    public void addCustomer(){
        System.out.println(customerService.findAll());
    }
}
