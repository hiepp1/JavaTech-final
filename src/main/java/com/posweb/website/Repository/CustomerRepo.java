package com.posweb.website.Repository;

import com.posweb.website.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Customer findCustomerByPhoneNumber(String phoneNumber);

    Customer findCustomerById(int id);
    List<Customer> findAll();
}
