package com.ntech.controller;

import com.ntech.forward.Constant;
import com.ntech.forward.HttpUploadFile;
import com.ntech.forward.MethodUtil;
import com.ntech.model.Customer;
import com.ntech.model.LibraryKey;
import com.ntech.model.SetMeal;
import com.ntech.service.inf.ICustomerService;
import com.ntech.service.inf.ILibraryService;
import com.ntech.service.inf.ISetMealService;
import com.ntech.util.SHAencrypt;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;


@Controller
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger logger = Logger.getLogger(CustomerController.class);

//    @Autowired
//    private HttpServletResponse response;

    @Autowired
    ICustomerService customerService;

    @Autowired
    ISetMealService setMealService;

    @Autowired
    ILibraryService libraryService;

    @RequestMapping("/register")
    public String jumpToRegister() {
        logger.info("jump to register page");
        return "register";
    }


    @RequestMapping("/check")
    @ResponseBody
    public Boolean checkUserName(@RequestParam String userName) {
        logger.info("checkLogin");
        return customerService.checkUserName(userName);
    }

    @RequestMapping("/commitReg")
    public ModelAndView register(@RequestParam("name") String name, String password, String email) {
        ModelAndView mav = new ModelAndView("msg");
        if (null == name || "".equals(name) || null == password || "".equals(password) || null == email || "".equals(email)) {
            mav.addObject("msg", "注册信息有无，请重启填写");
            return mav;
        }

        logger.info("commit register");
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPassword(SHAencrypt.encryptSHA(password));
        customer.setEmail(email);
        try {
            customerService.add(customer);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            mav.addObject("msg", "注册失败");
            mav.setViewName("error");
        }
        mav.addObject("msg", "注册成功，请到邮箱激活账号");
        return mav;

    }

    //登录
    @RequestMapping("loginCheck")
    @ResponseBody
    public boolean login(String name, String password, HttpSession session) {
        logger.info("login check user:" + name);
        boolean result = false;
        result = customerService.loginCheck(name, password);
        if (result) {
            session.setAttribute("name", name);
        }
        return result;
    }


    @RequestMapping("active")
    public ModelAndView activeEmail(String name, String validateCode, String email) {
        ModelAndView mav = new ModelAndView();
        if (null == name || "".equals(name) || null == validateCode || "".equals(validateCode)) {
            mav.addObject("msg", "验证信息有误，请重新注册");
            mav.setViewName("error");
            return mav;
        }
        Customer customer = customerService.findByName(name);
        if (validateCode.equals(SHAencrypt.encryptSHA(customer.getEmail()))) {
            customer.setActive(1);
            if (customerService.modify(customer) == 1) {
                logger.info(customer.getName() + "激活成功");
                mav.setViewName("msg");
                mav.addObject("msg", "激活成功<a href=\"login\">点击登录</a>");
                return mav;
            }
            ;
        }
        mav.addObject("msg", "出现未知错误");
        mav.setViewName("error");
        return mav;
    }

    @RequestMapping("verifyCode")
    public void VerifyCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        int width = 63;
        int height = 37;
        Random random = new Random();
        //设置response头信息
        //禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height, 1);
        //产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        //Graphics类的样式
        g.setColor(this.getRandColor(200, 250));
        g.setFont(new Font("Times New Roman", 0, 28));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for (int i = 0; i < 40; i++) {
            g.setColor(this.getRandColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        //绘制字符
        String strCode = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            strCode = strCode + rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 28);
        }
        //将字符保存到session中用于前端的验证

        session.setAttribute("strCode", strCode);
        logger.info(strCode);
        g.dispose();

        ImageIO.write(image, "JPEG", response.getOutputStream());
        response.getOutputStream().flush();
    }

    @RequestMapping("checkCode")
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


    /**
     * 页面跳转控制
     * 1.个人中心
     * 2.套餐购买
     * 3.人脸探测
     * 4.人脸对比
     * 5.图库管理
     * 6.使用记录
     * 8.登录跳转
     */
    //登录成功后进入主页面
    @RequestMapping("personInfo")
    public ModelAndView customerInfo(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String name = (String) session.getAttribute("name");
        if (name == null || name.equals("") || !customerService.checkUserName(name)) {
            mav.addObject("msg", "用户存在异常");
            mav.setViewName("error");
            return mav;
        }
        SetMeal meal = setMealService.findByName(name);
        Customer customer = customerService.findByName(name);
        if (meal != null) {

            if (meal.getContype().equals("date")) {
                //计算剩余天数
                int leftDay = (int) ((meal.getEndTime().getTime() -
                        meal.getBeginTime().getTime()) / (1000 * 3600 * 24));
                session.setAttribute("leftDay", leftDay);
            }
        }
        session.setAttribute("meal", meal);
        session.setAttribute("customer", customer);

        mav.setViewName("info");
        return mav;
    }

    @RequestMapping("setMeal")
    public String setMealJump() {
        return "set-meal";
    }

    @RequestMapping("detect")
    public String faceDetectJump() {
        return "show-detect";
    }

    @RequestMapping("verify")
    public String faceVerifyJump() {
        return "show-verify";
    }

    //个人相册,判断session中是否有用户名,根据session中的用户名来获取该用户的相册
    @RequestMapping("gallery")
    public ModelAndView galleryManageJump(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            String name = (String) session.getAttribute("name");
            if (null != name) {
                List<LibraryKey> galleries = libraryService.findLKByUserName(name);
                modelAndView.setViewName("show-gallery");
                logger.info(galleries);
                modelAndView.addObject("galleries", galleries);
                modelAndView.addObject("test1", "test1Content");
                modelAndView.addObject("test2", "test2Content");
            } else {
//                logger.info("");
                modelAndView.setViewName("msg");
                modelAndView.addObject("msg", "用户会话过期,请重新登录 <a href='login'>点击登录</a>");

            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            modelAndView.setViewName("msg");
            modelAndView.addObject("msg", "操作异常");
            return modelAndView;
        }
        return modelAndView;
    }

    @RequestMapping("gallery-demo")
    public ModelAndView getDemoGallery(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("show-gallery-demo");
        String name = (String) session.getAttribute("name");

        String result = null;
        try {
            logger.info(request.getRequestURI());
            if (request.getRequestURI().equals("/customer/gallery-demo")) {
                request.setAttribute("localAPI", "/v0/faces/gallery/demo_default");
            }
            if (null != name && !"".equals(name)) {
                modelAndView.setViewName("show-gallery");
                request.setAttribute("localAPI", "/v0/faces/gallery/" + name);
            }
            result = MethodUtil.getInstance().requestForword(request, response);
            logger.info(result);
            modelAndView.addObject("galleryDemo", wrapResponse(result));
        } catch (Exception e) {
            logger.error("request error");
            modelAndView.setViewName("msg");
            modelAndView.addObject("msg", "request error");
        }
//        modelAndView.addObject("demoGallery",result);
        return modelAndView;
    }

    @RequestMapping("getDemoFace")
    @ResponseBody
    public String getDemoSearchFace(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
        String name = (String) session.getAttribute("name");
        String result = null;
        if (null != name && !"".equals(name)) {
            request.setAttribute("localAPI", "/v0/faces/gallery/"+name+"/identify");
        } else {
            request.setAttribute("localAPI", "/v0/faces/gallery/demo_default/identify");
        }
        result = MethodUtil.getInstance().requestForword(request, response);
        if (null != result && !"".equals(result)) {
            return result.replaceAll("http://127.0.0.1:3333/uploads", "http://192.168.10.208:3333/uploads");
        }
        return result;

    }

    @RequestMapping("getMyGallery")
    @ResponseBody
    public JSONArray getMyGallery(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        String name = (String) session.getAttribute("name");
        String result = null;
        if (null != name && !"".equals(name)) {
            if (request.getRequestURI().equals("/customer/getMyGallery")) {
                request.setAttribute("localAPI", "/v0/faces/gallery/" + name);
            }
//            request.setAttribute("personFaceInsert",name);

            result = MethodUtil.getInstance().requestForword(request, response);

            logger.info(result);

            return wrapResponse(result);
        }
        return null;
    }


    //添加人脸到个人库

    @RequestMapping("addToGallery")
    @ResponseBody
    public JSONArray addToGallery(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String name = (String) session.getAttribute("name");
        String result = null;
        if (null != name && !"".equals(name)) {
            if (request.getRequestURI().equals("/customer/addToGallery")) {
                request.setAttribute("localAPI", "/v0/face/");
            }
            request.setAttribute("personFaceInsert", name);
            result = MethodUtil.getInstance().requestForword(request, response);
            if (result != null) {
                //添加后获取最新的图片列表
             result = getMyGalleryLocal(name);
            }
            return wrapResponse(result) ;
        }
        return null;
    }


    //通过id删除个人人脸库中的图片
    @RequestMapping("deleteToGallery")
    @ResponseBody
    public JSONArray deleteToGallery(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String name = (String) session.getAttribute("name");
        String result = null;
        if (null != name && !"".equals(name)) {
            if (request.getRequestURI().equals("/customer/deleteToGallery")) {
                request.setAttribute("localAPI", "/v0/face/id/");
            }
            result=MethodUtil.getInstance().requestForword(request, response);
            result=getMyGalleryLocal(name);

            return wrapResponse(result);
        }
        return null;
    }

    @RequestMapping("addGallery")
    @ResponseBody
    public boolean addGallery(String libraryName, HttpSession session) {
        if (null == libraryName || "".equals(libraryName)) {
            return false;
        }
        String name = (String) session.getAttribute("name");
        if (null != name) {
            if (!libraryService.checkLibrary(name, libraryName)) {
                LibraryKey libraryKey = new LibraryKey();
                libraryKey.setUserName(name);
                libraryKey.setLibraryName(libraryName);
                libraryService.insert(libraryKey);
            }
        }
        return false;
    }

//    //删除图库
//    @RequestMapping("deleteGallery")
//    @ResponseBody
//    public boolean deleteGallery(String libraryName) {
//
//        return false;
//    }

    @RequestMapping("record")
    public String recordLogJump() {
        return "record-log";
    }

    @RequestMapping("login")
    public String loginJump() {
        return "login";
    }

    @RequestMapping("exit")
    public String loginJump(HttpSession session) {
        session.removeAttribute("name");
        return "login";
    }


    @RequestMapping("setMealBuy")
    @ResponseBody
    public boolean setMeal(HttpSession session, @RequestParam("type") String type, @RequestParam("value") String value) {
        String name = (String) session.getAttribute("name");
        if (null != name) {
            if ("".equals(name) || null == type || "".equals(type) || null == value || "".equals(value)) {
                return false;
            }
            if (!(type.equals("date") || type.equals("times"))) {
                return false;
            }
            if (!customerService.checkUserName(name)) {
                logger.error("username is not exist");
                return false;
            }

            int intValue = Integer.parseInt(value);
            SetMeal setMeal = new SetMeal();
            setMeal.setUserName(name);
            setMeal.setContype(type);

            //找出数据库中的订单
            SetMeal meal = setMealService.findByName(setMeal.getUserName());
            if (meal != null) {
                //判断订单类型  一个用户只能购买一种类型的订单
                if (!meal.getContype().equals(setMeal.getContype())) {
                    logger.info("chose two diffence contype");
                    return false;

                }
            }

            if (type.equals("date")) {
                if (intValue == 1 || intValue == 3 || intValue == 6 || intValue == 12) {
                    setMeal.setBeginTime(new Date());
                    setMeal.setEndTime(count(intValue));
                } else {
                    return false;
                }
            } else {
                if (intValue == 100 || intValue == 300 || intValue == 500 || intValue == 1000) {
                    setMeal.setTotalTimes(intValue);
                    setMeal.setLeftTimes(intValue);
                } else {
                    return false;
                }
            }
            if (setMealService.add(setMeal)) {
                return true;
            }
        }
        return false;
    }


    //转发接口
    @RequestMapping(value = {"detect-face", "verify-face",})
    @ResponseBody
    public String forwardDetect(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
//        request.getRequestDispatcher("/n-tech/v0/detect").forward(request,response);
        try {
            logger.info(request.getRequestURI());
            if (request.getRequestURI().equals("/customer/detect-face")) {
                request.setAttribute("localAPI", "/v0/detect");
            }
            if (request.getRequestURI().equals("/customer/verify-face")) {
                request.setAttribute("localAPI", "/v0/verify");
            }
            result = MethodUtil.getInstance().requestForword(request, response);
        } catch (Exception e) {
            logger.error("request error");
            return "request error";
        }
        return result;
    }

    @RequestMapping("")
    @ResponseBody
    public String forwardVerify(HttpServletRequest request, HttpServletResponse response) {
        String result = null;
        return result;
    }


    //获取用户图库信息
    private String getMyGalleryLocal(String name){
        String result = null;
        HashMap<String ,String> header =new HashMap<String,String>();
        header.put("Method","GET");
        header.put("Authorization", Constant.TOKEN);
        header.put("API", "/v0/faces/gallery/" + name);
        try {
            result = HttpUploadFile.getInstance().httpURLConnectionSDK(header, null, null, "no");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    //包装返回的请求
    private JSONArray wrapResponse(String result) {
        JSONObject jsonResult = null;
        JSONArray jsonArray = null;
        try {
            jsonResult = (JSONObject) new JSONParser().parse(result);
            jsonArray = (JSONArray) jsonResult.get("results");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject tmpJson = (JSONObject) jsonArray.get(i);
                String tmpStrng = (String) tmpJson.get("photo");
                tmpStrng = "http://192.168.10.208" + tmpStrng.substring(16);
                ((JSONObject) jsonArray.get(i)).put("photo", tmpStrng);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }


    private Date count(int value) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        gc.add(2, value);
        return gc.getTime();
    }

    //创建颜色
    Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
