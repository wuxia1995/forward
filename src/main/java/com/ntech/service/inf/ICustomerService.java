package com.ntech.service.inf;

import com.ntech.model.Customer;

import javax.mail.MessagingException;
import java.util.List;

public interface ICustomerService {
    int add(Customer customer) throws MessagingException;
    int delete();
    int modify(Customer customer);
    List<Customer> findAll();
    Customer findByName(String name);
    boolean checkUserName(String userName);
    boolean loginCheck(String name,String password);
}
