package com.ntech.service.impl;

import com.ntech.dao.CustomerMapper;
import com.ntech.model.Customer;
import com.ntech.model.CustomerExample;
import com.ntech.service.inf.ICustomerService;
import com.ntech.util.SHAencrypt;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private static final Logger logger = Logger.getLogger(CustomerService.class);

    @Autowired
    CustomerMapper customerMapper;

    @Transactional
    public int add(Customer customer) {
        customer.setRegtime(new Date());
        return customerMapper.insert(customer);
    }

    public int delete() {
        return 1;
    }

    public int modify() {
        return 1;
    }

    public List<Customer> findAll() {
        logger.info("find all customer");
        CustomerExample example = new CustomerExample();
        example.createCriteria().andNameIsNotNull();
        return customerMapper.selectByExample(example);
    }

    public Customer findByName() {
        return null;
    }

    public boolean checkUserName(String userName) {
        logger.info("check user name");
        CustomerExample example = new CustomerExample();
        example.createCriteria().andNameEqualTo(userName);
        List<Customer> result = customerMapper.selectByExample(example);
        return result.size()>0?true:false;
    }

    public boolean loginCheck(String name, String password) {
        logger.info("check login info");
        try {
            password= SHAencrypt.encryptSHA(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        CustomerExample example = new CustomerExample();
        example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
        List<Customer> result =customerMapper.selectByExample(example);
        return result.size()>0?true:false;
    }
}
