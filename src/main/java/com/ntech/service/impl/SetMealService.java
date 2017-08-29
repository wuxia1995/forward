package com.ntech.service.impl;

import com.ntech.dao.SetMealMapper;
import com.ntech.model.SetMeal;
import com.ntech.model.SetMealExample;
import com.ntech.service.inf.ISetMealService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetMealService implements ISetMealService {

    Logger logger = Logger.getLogger(SetMealService.class);

    @Qualifier
    SetMealMapper setMealMapper;


    public boolean add(SetMeal setMeal) {
        logger.info("add new set meal for user:"+setMeal.getUserName());
        if(1==setMealMapper.insert(setMeal)){
            logger.info("add success");
            return true;
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
