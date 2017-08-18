package com.ntech.service.impl;

import com.ntech.dao.CustomerMapper;
import com.ntech.model.Customer;
import com.ntech.model.CustomerExample;
import com.ntech.service.inf.ICustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private static final Logger logger = Logger.getLogger(CustomerService.class);

    @Autowired
    CustomerMapper customerMapper;

    @Transactional
    public void add(Customer customer) {
        customerMapper.insert(customer);
    }

    public void delete() {

    }

    public void modify() {

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
}
