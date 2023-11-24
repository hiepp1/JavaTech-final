package com.posweb.website.Controller;

import com.posweb.website.Model.PasswordChangeRequest;
import com.posweb.website.Model.User;
import com.posweb.website.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserControll {

    private final UserService userService;

    public UserControll(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new User());
        return "login_page";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) {

        User authenticate = userService.authenticate(user.getUsername(), user.getPassword());
        if (authenticate != null) {
            model.addAttribute("userLogin", authenticate.getUsername());
            return "personal_page";
        } else {
            return "redirect:/login";
        }
    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute User user, Model model) {
//
//        User authenticate = userService.authenticate(user.getUsername(), user.getPassword());
//        if (authenticate != null) {
//            model.addAttribute("userLogin", authenticate.getUsername());
//            if ("ADMIN".equals(authenticate.getRole())) {
//                return "admin_page";
//            } else {
//                return "saleperson_page";
//            }
//        } else {
//            return "redirect:/login";
//        }
//    }

    @GetMapping("/changePassword")
    public String getChangePasswordPage(Model model) {
        model.addAttribute("changepasswordRequest", new User());
        return "forgetPassword_page";
    }

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute PasswordChangeRequest request, Model model) {
        boolean isChanged = userService.changePassword(request);
        if (isChanged) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Failed to change password. Please try again.");
            return "forgetPassword_page";
        }
    }
}



