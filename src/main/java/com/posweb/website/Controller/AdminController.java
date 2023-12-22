package com.posweb.website.Controller;

import com.posweb.website.Model.User;
import com.posweb.website.Repository.UserRepo;
import com.posweb.website.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
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
        return "staff_list";
    }

    @GetMapping("/add-product")
    public String addNewProduct(Model model) {
        // Add any necessary logic or data to the model
        return "product/add_new_product";
    }


    @GetMapping("/view-product")
    public String viewProducts(Model model) {
        // Add any necessary logic or data to the model
        return "view_product";
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
        return "staff_details_page";
    }
}
