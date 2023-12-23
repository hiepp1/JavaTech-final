package com.posweb.website.Controller;

import com.posweb.website.Model.Product;
import com.posweb.website.Model.User;
import com.posweb.website.Repository.UserRepo;
import com.posweb.website.Service.ImageUtils;
import com.posweb.website.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/salesperson")
public class SaleController {

    @Autowired
    private UserRepo repo;

    @Autowired
    private ProductService productService;

    @PostMapping("/logout")
    public String logout(HttpServletRequest request)
    {
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @GetMapping("")
    public String getSalesperson(Model model)
    {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("imageUtils", new ImageUtils());
        model.addAttribute("products", productList);
        return "viewProductSale_page";
    }

    @PostMapping("")
    public String Salesperson(Model model)
    {
        return "viewProductSale_page";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, HttpSession session) throws IOException {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("imageUtils", new ImageUtils());
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("user", loggedInUser);
            return "salesperson_page";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/profile")
    public String Profile(Model model)
    {
        return "salesperson_page";
    }


    @GetMapping("/update-avatar")
    public String updateAvatar(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("user", loggedInUser);
            return "updateAvatar_page";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/update-avatar")
    public String updateAvatar(@RequestParam("avatar") MultipartFile avatar,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) throws IOException
    {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            if (!avatar.isEmpty()) {
                byte[] imageBytes = avatar.getBytes();
                loggedInUser.setPicture(imageBytes);
                repo.save(loggedInUser);
                redirectAttributes.addFlashAttribute("successMessage", "Avatar updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Please select an image to upload.");
            }
            return "redirect:/profile"; // Redirect back to the salesperson page
        } else {
            return "redirect:/login";
        }
    }

    //------------------GET DEFAULT AVATAR AND ENCODE BYTE--------------------------
    @ModelAttribute("base64Image")
    public String base64Image(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getPicture() != null) {
            return Base64.getEncoder().encodeToString(loggedInUser.getPicture());
        }
        return "";
    }

    //================================================================================
}
