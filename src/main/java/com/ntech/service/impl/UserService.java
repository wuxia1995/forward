package com.ntech.service.impl;

import com.ntech.dao.UserMapper;
import com.ntech.model.User;
import com.ntech.model.UserExample;
import com.ntech.service.inf.IUserService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

/**
 * Created by niu on 17-3-26.
 */
@Repository
public class UserService implements IUserService{
    @Resource
    UserMapper userMapper;

    @Transactional
    public List<User> getAllUser() {
        UserExample example= new UserExample();
        example.createCriteria().andIdIsNotNull();
        List<User> users =userMapper.selectByExample(example);
        return users;
    }

    public User findByName(String name) {
        return null;
    }

    public void addUser(User user) {

    }

    public void deleteUser(int id) {

    }

    public void updateUser(User user) {

    }
}
