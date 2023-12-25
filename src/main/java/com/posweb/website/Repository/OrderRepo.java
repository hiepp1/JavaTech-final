package com.posweb.website.Repository;

import com.posweb.website.Model.Customer;
import com.posweb.website.Model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findOrderByCustomer(Customer customer);

}
