package com.forward.service.inf;

import com.forward.model.User;

import java.util.List;

/**
 * Created by niu on 17-3-26.
 */
public interface IUserService {
    List<User> getAllUser();
    User findByName(String name);
    void addUser(User user);
    void deleteUser(int id);
    void updateUser(User user);
}
