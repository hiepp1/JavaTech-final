package com.posweb.website.Controller;

import com.posweb.website.Model.Product;
import com.posweb.website.Model.ProductForm;
import com.posweb.website.Model.User;
import com.posweb.website.Repository.ProductRepo;
import com.posweb.website.Repository.UserRepo;
import com.posweb.website.Service.ImageUtils;
import com.posweb.website.Service.ProductService;
import com.posweb.website.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ProductService productService;
    @GetMapping("")
    public String getAdmin(Model model)
    {
        return "admin_page";
    }
    @PostMapping("")
    public String admin(@ModelAttribute User user, RedirectAttributes redirectAttributes)
    {
        return "admin_page";
    }

    @GetMapping("/staff-list")
    public String getStaffList(Model model)
    {
        List<User> staffList = userRepo.findByRole("SALE");
        model.addAttribute("staffList", staffList);
        model.addAttribute("imageUtils", new ImageUtils());
        return "staff_list";
    }

    @GetMapping("/add-product")
    public String getAddProduct(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "product/add_new_product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute ProductForm productForm,
                             @RequestParam("image") MultipartFile image,
                             RedirectAttributes redirectAttributes) {
        try {
            Product newProduct = new Product();
            newProduct.setName(productForm.getName());
            newProduct.setCategory(productForm.getCategory());
            newProduct.setImportPrice(productForm.getImportPrice());
            newProduct.setDate(new Date());

            if (!image.isEmpty()) {
                newProduct.setPicture(image.getBytes());
            }
            productRepo.save(newProduct);
            redirectAttributes.addFlashAttribute("message", "Product added successfully");
        } catch (IOException e) {
            // Handle the exception (e.g., log it)
            redirectAttributes.addFlashAttribute("error", "Error adding product. Please try again.");
        }

        return "redirect:/admin/view-product";
    }
    @PostMapping("/delete-product")
    public String deleteProduct(@RequestParam("productId") int productId, RedirectAttributes redirectAttributes) {
        productRepo.deleteById(productId);
        redirectAttributes.addFlashAttribute("message", "Product deleted successfully");
        return "redirect:/admin/view-product";
    }

    @GetMapping("/view-product")
    public String viewProducts(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("imageUtils", new ImageUtils());
        model.addAttribute("products", productList);
        return "viewProductAdmin_page";
    }



    //---------------------------Lock/Unlock Account===---------------------------
    @RequestMapping(value = "/staff-lock/{userId}", method = RequestMethod.GET)
    public String lockStaffAccount(@PathVariable int userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        user.setEnable(false);
        userRepo.save(user);
        return "redirect:/admin/staff-list";
    }

    @RequestMapping(value = "/staff-unlock/{userId}", method = RequestMethod.GET)
    public String unlockStaffAccount(@PathVariable int userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        user.setEnable(true);
        userRepo.save(user);
        return "redirect:/admin/staff-list";
    }

    //------------------------------------------------------------------

    //Salesperson Details
    @GetMapping("/staff-details/{userId}")
    public String viewStaffDetails(@PathVariable int userId, Model model) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("imageUtils", new ImageUtils());
        return "staff_details_page";
    }
}
