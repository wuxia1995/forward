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
    boolean setContype(String name,String contype);
    String getNameByToken(String name);
    boolean deleteByName(String name);
    long totalCount();
    List<Customer> findPage(int limit,int offset);
    String findByToken(String token);
    boolean checkToken(String inputToken);
    boolean enableToken(String name);
    boolean disableToken(String name);
}
