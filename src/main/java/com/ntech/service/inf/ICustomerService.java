package com.ntech.service.inf;

import com.ntech.model.Customer;

import java.util.List;

public interface ICustomerService {
    int add(Customer customer);
    int delete();
    int modify();
    List<Customer> findAll();
    Customer findByName();
    boolean checkUserName(String userName);
    boolean loginCheck(String name,String password);
}
