package com.posweb.website.Repository;

import com.posweb.website.Model.Product;
import com.posweb.website.Model.ProductForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findAll();
    Product findById(int id);

    Product deleteById(int id);
}
