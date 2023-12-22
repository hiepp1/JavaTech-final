//package com.posweb.website.Service;
//import com.posweb.website.Model.Product;
//import com.posweb.website.Repository.ProductRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//@Service
//public class ProductService {
//
//    @Autowired
//    private ProductRepo productRepo;
//
//    public List<Product> getAllProducts() {
//        return productRepo.findAll();
//    }
//
//    public void saveProduct(Product product) {
//        // Handle file upload
//        if (product.getImgFile() != null && !product.getImgFile().isEmpty()) {
//            try {
//                product.setImg(product.getImgFile().getBytes());
//            } catch (IOException e) {
//                // Handle the exception as needed
//                e.printStackTrace();
//            }
//        }
//
//        // Save the product to the database
//        productRepo.save(product);
//    }
//}
//
