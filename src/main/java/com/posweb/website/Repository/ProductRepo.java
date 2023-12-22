package com.posweb.website.Repository;

import com.posweb.website.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findAll();
    Product findById(int id);
}
