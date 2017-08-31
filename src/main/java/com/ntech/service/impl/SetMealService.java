package com.ntech.service.impl;

import com.ntech.dao.SetMealMapper;
import com.ntech.model.SetMeal;
import com.ntech.model.SetMealExample;
import com.ntech.service.inf.ISetMealService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class SetMealService implements ISetMealService {

    Logger logger = Logger.getLogger(SetMealService.class);

    @Autowired
    SetMealMapper setMealMapper;


    public boolean add(SetMeal setMeal) {
        logger.info("add new set meal for user:" + setMeal.getUserName());
        SetMeal meal = findByName(setMeal.getUserName());
        //查不到结果 直接插入
        if(meal==null){
            setMeal.setEnable(1);
            if (1 == setMealMapper.insertSelective(setMeal)) {
                logger.info("add success");
                return true;
            }
        }
        //查询出结果
        else{
            if (meal.getEnable() == 0) {
                //如果enable=0，修改
                setMeal.setEnable(1);
                //更新套餐
                return  modify(setMeal);
            }else{
                //如果是包天
                if(setMeal.getContype().equals("date")){
                    //获取套餐天数
                    int leftDay=(int)((setMeal.getEndTime().getTime()
                            - setMeal.getBeginTime().getTime())/(1000*3600*24));
                    //获取用户原先剩余天数
                    int userLeftDay=(int)((meal.getEndTime().getTime()
                            - meal.getBeginTime().getTime())/(1000*3600*24));
                    GregorianCalendar gc=new GregorianCalendar();
                    gc.setTime(meal.getBeginTime());
                    //得到结束时间
                    gc.add(Calendar.DAY_OF_YEAR,leftDay+userLeftDay);
                    meal.setEndTime(gc.getTime());
                    //更新套餐
                    return modify(meal);
                }//包次数
                else{
                    //设置剩余次数
                    meal.setLeftTimes(meal.getLeftTimes()+setMeal.getLeftTimes());
                    //设置总次数
                    meal.setTotalTimes(meal.getTotalTimes()+setMeal.getTotalTimes());
                    return  modify(meal);
                }
            }

        }

        logger.error("add set meal error");
        return false;
    }


    public boolean delete(String userName) {
        logger.info("delete set meal for user:"+userName);
        SetMealExample example = new SetMealExample();
        example.createCriteria().andUserNameEqualTo(userName).andEnableEqualTo(1);
        if(1==setMealMapper.deleteByExample(example)){
            logger.info("delete success");
            return true;
        }
        logger.error("delete error");
        return false;
    }

    public List<SetMeal> findAll() {
        logger.info("get all set meal info");
        SetMealExample example = new SetMealExample();
        example.createCriteria().andUserNameIsNotNull();
        List<SetMeal> setMeals = setMealMapper.selectByExample(example);
        return setMeals;
    }

    public SetMeal findByName(String name) {
        logger.info("get set meal for user " +name );
        SetMealExample example = new SetMealExample();
        example.createCriteria().andUserNameEqualTo(name).andEnableEqualTo(1);
        List<SetMeal> setMeals = setMealMapper.selectByExample(example);
        if(setMeals.size()>0){
            return setMeals.get(0);
        }
        logger.error("no records of set meal for user "+ name);
        return null;
    }

    public boolean modify(SetMeal setMeal) {
        logger.info("modify set meal for user "+setMeal.getUserName());
        SetMealExample example = new SetMealExample();
        if(1==setMealMapper.updateByExampleSelective(setMeal,example)){
            logger.info("modify success");
            return true;
        }
        logger.error("modify fail");
        return false;
    }
}
