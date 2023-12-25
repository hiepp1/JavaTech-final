package com.posweb.website.Service;

import com.posweb.website.Model.Customer;
import com.posweb.website.Model.Order;
import com.posweb.website.Repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<Order> findOrdersByCustomer(Customer customer) {
        return orderRepo.findOrderByCustomer(customer);
    }
}
