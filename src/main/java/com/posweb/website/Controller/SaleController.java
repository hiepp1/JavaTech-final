package com.posweb.website.Controller;
import org.springframework.security.core.Authentication;

import com.posweb.website.Model.User;
import com.posweb.website.Repository.UserRepo;
import com.posweb.website.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/salesperson")
public class SaleController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepo repo;
    @GetMapping("")
    public String getSalesperson(Model model, Authentication authentication) {
        // Assuming UserService has a method to fetch user by username
        User currentUser = (User) authentication.getPrincipal();
        model.addAttribute("user", currentUser);
        return "salesperson_page";
    }

    @PostMapping("")
    public String Salesperson(@ModelAttribute User user, RedirectAttributes redirectAttributes)
    {
        return "salesperson_page";
    }

}
