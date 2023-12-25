package com.posweb.website.Service;

import com.posweb.website.Model.Customer;
import com.posweb.website.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepository; // Assuming you have a repository for customer operations

    public Customer findCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findCustomerByPhoneNumber(phoneNumber);
    }

    public Customer findCustomerById(int id) {
        return customerRepository.findCustomerById(id);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}
