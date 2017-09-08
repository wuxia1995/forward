package com.ntech.controller;

import com.ntech.model.Customer;
import com.ntech.model.Log;
import com.ntech.model.SetMeal;
import com.ntech.service.inf.ICustomerService;
import com.ntech.service.inf.ILogService;
import com.ntech.service.inf.ISetMealService;
import com.ntech.util.SHAencrypt;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final static Logger logger = Logger.getLogger(AdminController.class);
    @Autowired
    private ISetMealService setMealService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ILogService logService;


    //跳转登陆
    @RequestMapping("/login")
    public String loginJump() {
        return "admin-login";
    }


    //跳转日志页面
    @RequestMapping("log")
    public ModelAndView logJump(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String name = (String) session.getAttribute("admin");
        if (name.equals("ntech")) {
            logger.info("log succeed");
            session.setAttribute("logList", logService.findAll());
            mav.setViewName("log");
            return mav;
        }
        logger.info("log failed");
        mav.setViewName("error");
        return mav;
    }

    //跳转到首页
    @RequestMapping("index")
    public String IndexJump() {

        return "admin-index";
    }


    //验证码校验
    @RequestMapping("/checkCode")
    @ResponseBody
    public boolean checkCode(HttpSession session, String authCode) {

        String preCode = (String) session.getAttribute("strCode");
        logger.info("precode" + preCode);
        logger.info("commitcode" + authCode);
        if (preCode.equalsIgnoreCase(authCode)) {
            logger.info("validate success");
            return true;
        }

        return false;

    }

    //登录
    @RequestMapping("/loginCheck")
    @ResponseBody
    public boolean login(String name, String password, HttpSession session) {
        logger.info("login check user:" + name);
        boolean result = false;
        result = customerService.loginCheck(name, password);
        if (result) {
            session.setAttribute("admin", name);
        }
        return result;
    }

    //用户名验证
    @RequestMapping("checkNameAndMeal")
    @ResponseBody
    public boolean checkName(String name) {
        if (name != null) {
            Customer customer = customerService.findByName(name);
            if (customer != null) {
                SetMeal meal = setMealService.findByName(name);
                if (meal == null) {
                    return true;
                }
                return false;
            }
            return false;
        }

        return false;
    }

    //-------------------------------------用户----------------------------------------------//
    //跳转更新页面
    @RequestMapping("update")
    public ModelAndView update(HttpSession session, String name) {
        String adminName = (String) session.getAttribute("admin");
        ModelAndView mav = new ModelAndView();
        if (adminName.equals("ntech")) {
            session.setAttribute("customer", customerService.findByName(name));
            mav.setViewName("update");
            return mav;
        }
        logger.info("update failed");
        mav.setViewName("error");
        return mav;
    }

    //修改密码
    @RequestMapping("updatePassword")
    public ModelAndView updatePassword(HttpSession session, String name, String password) {
        String adminName = (String) session.getAttribute("admin");
        ModelAndView modelAndView = new ModelAndView();
        if (adminName.equals("ntech")) {
            Customer customer = customerService.findByName(name);
            if (customer != null) {
                //密码加密
                customer.setPassword(SHAencrypt.encryptSHA(password));
                if (customerService.modify(customer) == 1) {
                    logger.info("updatePassword succeed");
                    modelAndView.addObject("msg", "更改密码成功");
                }
            } else {
                logger.info("updatePassword failed");
                modelAndView.addObject("msg", "查无此人");
            }
            modelAndView.setViewName("information");
            return modelAndView;
        }
        modelAndView.setViewName("error");
        return modelAndView;
    }

    //更新用户
    @RequestMapping("updateCustomer")
    public ModelAndView updateCustomer(HttpSession session, @RequestBody Customer customer) {
        String admin = (String) session.getAttribute("admin");
        ModelAndView mav = new ModelAndView();
        //根据session判断可否操作
        if (admin.equals("ntech")) {
            if (customerService.modify(customer) == 1) {
                logger.info("customer update success");
                mav.setViewName("customer");
                session.setAttribute("customerList", customerService.findAll());
                return mav;
            }
        }
        mav.setViewName("error");
        logger.info("customer update fail");
        return mav;
    }

    //查询所有用户
    @RequestMapping("findCustomers")
    public ModelAndView findCustomers(HttpSession session) {
        String admin = (String) session.getAttribute("admin");
        ModelAndView mav = new ModelAndView();
        //根据session判断可否操作
        if (admin.equals("ntech")) {
            logger.info("customer findCustomers success");
            session.setAttribute("customerList", customerService.findAll());
            mav.setViewName("customer");
            return mav;
        }
        logger.info("customer findCustomers fail");
        mav.setViewName("error");
        return mav;
    }

    //根据用户名删除用户
    @RequestMapping("deleteCustomer")
    public ModelAndView deleteCustomerByName(HttpSession session, String name) {
        String admin = (String) session.getAttribute("admin");
        ModelAndView mav = new ModelAndView();
        if (admin.equals("ntech")) {
            if (customerService.findByName(name) != null) {
                logger.info("delete customer succeed");
                customerService.deleteByName(name);
            }
            logger.info("delete customer succeed");
            session.setAttribute("customerList", customerService.findAll());
            mav.setViewName("customer");
            return mav;
        }
        logger.info("delete customer failed");
        mav.setViewName("error");
        return mav;
    }

    // 根据用户名删除用户和用户订单信息
    public Boolean deleteCustomerAndMealByName(HttpSession session, String name) {
        String admin = (String) session.getAttribute("admin");
        if (admin.equals("ntech")) {
            if (customerService.findByName(name) != null) {

                customerService.deleteByName(name);
            }
            if (setMealService.findByName(name) != null) {
                setMealService.delete(name);
            }
            logger.info("deleteCustomerAll succeed ");
            return true;
        }

        return false;
    }
//----------------------------日志-------------------------------------------------//

    //查询日志
    @RequestMapping("findLogByName")
    public ModelAndView findLogByName(HttpSession session, String name) {
        String admin = (String) session.getAttribute("admin");
        ModelAndView mav = new ModelAndView();
        if (admin.equals("ntech")) {

            List<Log> list = logService.findByName(name);
            if (list != null) {
                logger.info("findLogByName succeed");
                session.setAttribute("logList", logService.findByName(name));
                mav.setViewName("log");
                return mav;
            }

            logger.info("findLogByName failed");
            mav.setViewName("error");
            return mav;
        }
        logger.info("findLogByName failed");
        mav.setViewName("error");
        return mav;
    }
    //根据姓名模糊查询  todo

    //------------------------------订单------------------------------------------------//
    //查询所有订单
    @RequestMapping("findSetMeals")
    public ModelAndView findSetMeals(HttpSession session) {
        String admin = (String) session.getAttribute("admin");
        ModelAndView mav = new ModelAndView();
        if (admin.equals("ntech")) {
            logger.info("findSetMeals succeed");
            session.setAttribute("mealList", setMealService.findAll());
            mav.setViewName("meal");
            return mav;
        }
        logger.info("findSetMeal failed");
        mav.setViewName("error");
        return mav;
    }

    @RequestMapping("testSetMeals")
    @ResponseBody
    public JSONObject testMeals(HttpSession session) {
        //   String admin = (String) session.getAttribute("admin");
        JSONObject jsonObject = new JSONObject();
//        if (admin.equals("ntech")) {
//
//        }
//        logger.info("findSetMeal failed");
//
//        return null;
        logger.info("findSetMeals succeed");
        jsonObject.put("data", setMealService.findAll());

        jsonObject.put("total", setMealService.totalCount());

        return jsonObject;
    }

    //根据姓名查询订单
    @RequestMapping("findSetMealByName")
    public ModelAndView findSetMealByName(HttpSession session, String name) {
        String admin = (String) session.getAttribute("admin");
        ModelAndView mav = new ModelAndView();
        if (admin.equals("ntech")) {
            logger.info("findSetMeals succeed");
            ArrayList<SetMeal> mealList = new ArrayList<SetMeal>();
            mealList.add(setMealService.findByName(name));
            session.setAttribute("mealList", mealList);
            mav.setViewName("meal");
            return mav;
        }
        logger.info("findSetMeal failed");
        mav.setViewName("error");
        return mav;
    }

    //修改订单
    @RequestMapping("/updateSetMeal")
    public ModelAndView updateSetMeal(String id, String userName, String contype,
                                      String beginTime, String endTime, String totalTimes,
                                      String leftTimes, String enable) {
        ModelAndView mav = new ModelAndView();
        try {
            SetMeal setMeal = new SetMeal();
            //给对象赋值
            setMeal.setId(Integer.parseInt(id));
            setMeal.setUserName(userName);
            setMeal.setContype(contype);

            //字符串向时间类型进行转换
            if (contype.equals("date")) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                setMeal.setBeginTime(format.parse(beginTime));
                setMeal.setEndTime(format.parse(endTime));
            } else if (contype.equals("times")) {
                setMeal.setTotalTimes(Integer.parseInt(totalTimes));
                setMeal.setLeftTimes(Integer.parseInt(leftTimes));
            } else {
                logger.info("update meal error");
                mav.setViewName("error");
            }
            if (enable.equals("able")) {
                setMeal.setEnable(1);
            } else if (enable.equals("enable")) {
                setMeal.setEnable(0);
            } else {
                logger.info("update meal error");
                mav.setViewName("error");
            }

            if (setMealService.modify(setMeal)) {
                logger.info("update meal succeed");
                mav.setViewName("dataTable");
            } else {
                logger.info("update meal error");
                mav.setViewName("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            return mav;
        }
    }
    @RequestMapping("addMeal")
    @ResponseBody
    public boolean addMeal(String userName, String contype,
                                String beginTime, String endTime, String totalTimes,
                                String leftTimes, String enable){
        SetMeal setMeal = new SetMeal();
        try {
            //给对象赋值
            setMeal.setUserName(userName);
            setMeal.setContype(contype);
            //字符串向时间类型进行转换
            if (contype.equals("date")) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                setMeal.setBeginTime(format.parse(beginTime));
                setMeal.setEndTime(format.parse(endTime));
            } else if (contype.equals("times")) {
                setMeal.setTotalTimes(Integer.parseInt(totalTimes));
                setMeal.setLeftTimes(Integer.parseInt(leftTimes));
            }
            if (enable.equals("able")) {
                setMeal.setEnable(1);
            } else if (enable.equals("enable")) {
                setMeal.setEnable(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

           if(setMealService.add(setMeal)){
               return  true;
           }
           return  false;
        }

    }
    //批量删除选择的订单
    @RequestMapping("deleteMealByName")
    @ResponseBody
    public boolean deleteMealByName(String[] selectedName) {
        // System.out.println("-------------------");
        if (selectedName != null) {
            for (int i = 0; i < selectedName.length; i++) {
                setMealService.delete(selectedName[i]);
            }
            logger.info("deleteMeal by selectedName[] succeed");
            return true;
        }

        return true;
    }
}
