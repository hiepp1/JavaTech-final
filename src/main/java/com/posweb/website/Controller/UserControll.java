package com.posweb.website.Controller;

import com.posweb.website.Model.ConfirmationToken;
import com.posweb.website.Model.PasswordChangeRequest;
import com.posweb.website.Model.User;
import com.posweb.website.Repository.ConfirmationTokenRepo;
import com.posweb.website.Repository.UserRepo;
import com.posweb.website.Service.DatabaseService;
import com.posweb.website.Service.EmailService;
import com.posweb.website.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserControll {

    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ConfirmationTokenRepo confirmationTokenRepo;


    public UserControll(UserService userService, DatabaseService databaseService, EmailService emailService)
    {
        this.userService = userService;
        this.databaseService = databaseService;
        this.emailService = emailService;
    }

    //-----------------------------------LOG IN-----------------------------------Done
    @GetMapping("/login")
    public String getLoginPage(Model model)
    {
        databaseService.creatUserIfNotExists();
        model.addAttribute("loginRequest", new User());
        return "login_page";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute User user, RedirectAttributes redirectAttributes)
    {

        User authenticate = userService.authenticate(user.getUsername(), user.getPassword());
        if (authenticate != null) {
            return "admin_page";
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Login failed, please check your username and password");
            return "redirect:/login";
        }
    }
    //###########################------------------------###############################

    //-----------------------------------LOG OUT-----------------------------------Done
    @PostMapping("/logout")
    public String logout(HttpServletRequest request)
    {
        request.getSession().invalidate();
        return "redirect:/login";
    }
    //###########################------------------------###############################

    //-----------------------------------CHANGE PASSWORD---------------------------Done
    @GetMapping("/changePassword")
    public String getChangePasswordPage(Model model)
    {
        model.addAttribute("changepasswordRequest", new User());
        return "changePassword_page";
    }
    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute PasswordChangeRequest request, RedirectAttributes redirectAttributes)
    {
        UserService.ChangePasswordResult result = userService.changePassword(request);
        switch (result) {
            case SUCCESS:
                redirectAttributes.addFlashAttribute("message", "Change Password successfully");
                return "redirect:/login";
            case SAME_PASSWORD:
                redirectAttributes.addFlashAttribute("error", "The new password must be different from the old password");
                return "redirect:/changePassword";
            case USER_NOT_FOUND:
                redirectAttributes.addFlashAttribute("error", "Username is not exist");
                return "redirect:/changePassword";
            default:
                redirectAttributes.addFlashAttribute("error", "Failed to change password. Please try again.");
                return "redirect:/changePassword";
        }
    }
    //###########################------------------------###############################

    //----------------------------CREATE SALEPERSON ACCOUNT-----------------------NotDone
    @RequestMapping(value="/createAccount", method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, User user)
    {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("createAccount_page");
        return modelAndView;
    }

    @RequestMapping(value="/createAccount", method = RequestMethod.POST)
    public ModelAndView createAccount(ModelAndView modelAndView, User user)
    {

        User existingUser = userRepo.findByEmailIgnoreCase(user.getEmail());
        if (existingUser != null) {
            modelAndView.addObject("message", "This email already exists!");
            modelAndView.setViewName("error_page");
        } else {
            userRepo.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            confirmationTokenRepo.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            System.out.println(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("Email");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

            emailService.sendEmail(mailMessage);

            modelAndView.addObject("emailId", user.getEmail());
            modelAndView.setViewName("admin_page");
        }
        return modelAndView;
    }
    //###########################------------------------################################

}



