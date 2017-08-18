package com.ntech.service.inf;

import com.ntech.model.Customer;

import java.util.List;

public interface ICustomerService {
    void add(Customer customer);
    void delete();
    void modify();
    List<Customer> findAll();
    Customer findByName();
}
