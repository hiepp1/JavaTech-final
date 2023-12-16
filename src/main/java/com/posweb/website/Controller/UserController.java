package com.posweb.website.Controller;

import com.posweb.website.Model.ConfirmationToken;
import com.posweb.website.Model.PasswordChangeRequest;
import com.posweb.website.Model.User;
import com.posweb.website.Repository.ConfirmationTokenRepo;
import com.posweb.website.Repository.UserRepo;
import com.posweb.website.Service.EmailService;
import com.posweb.website.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Value("${base_url}")
    private String baseUrl;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ConfirmationTokenRepo confirmationTokenRepo;


    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    //-----------------------------------LOG IN-----------------------------------Done
    @GetMapping("/login")
    public String getLoginPage(Model model)
    {
        model.addAttribute("loginRequest", new User());
        return "login_page";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, RedirectAttributes redirectAttributes)
    {
        User authenticate = userService.authenticate(user.getUsername(), user.getPassword());
        if (authenticate != null) {
            if (authenticate.isEnable()) {
                if (authenticate.getRole().equals("ADMIN")) {
                    return "redirect:/admin";
                } else if (authenticate.getRole().equals("SALE")) {
                    return "redirect:/salesperson";
                } else {
                    redirectAttributes.addFlashAttribute("loginError", "Invalid role");
                    return "redirect:/login";
                }
            } else {
                redirectAttributes.addFlashAttribute("loginError", "Account has not been verified");
                return "redirect:/login";
            }
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

    //----------------------------CREATE SALEPERSON ACCOUNT------------------------NotDone
    @RequestMapping(value = "/createAccount", method = RequestMethod.GET)
    public ModelAndView getCreateAccount(ModelAndView modelAndView, User user)
    {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("createAccount_page");
        return modelAndView;
    }
    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    public ModelAndView createAccount(ModelAndView modelAndView, User user)
    {
        User existingUser = userRepo.findByEmailIgnoreCase(user.getEmail());
        if (existingUser != null) {
            modelAndView.addObject("message", "This email already exists!");
            modelAndView.setViewName("error_page");
        } else {

            //Split and set user's account
            String mail = user.getEmail();
            String split_mail[] = mail.split("@");
            String user_name = split_mail[0];
            String user_password_temp = split_mail[0];

            user.setUsername(user_name);
            user.setPassword(user_password_temp);
            user.setEnable(false);
            user.setRole("SALE");

            userRepo.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            confirmationTokenRepo.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(mail);
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("hiepgale0817@gmail.com");
            mailMessage.setText("Your account has been created, this is the link to log in to the system : "
                    + baseUrl + "/confirmAccount?token=" + confirmationToken.getConfirmationToken());

            emailService.sendEmail(mailMessage);

            modelAndView.addObject("email", "Email has been sent");
            modelAndView.setViewName("createAccount_page");
        }
        return modelAndView;
    }
    @RequestMapping(value = "/confirmAccount", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepo.findByConfirmationToken(confirmationToken);
        if (token != null) {
            User user = userRepo.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setEnable(true);
            userRepo.save(user);
            modelAndView.setViewName("confirmAccount_page");
        } else {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("error_page");
        }

        return modelAndView;
    }
    //###########################------------------------################################

    //----------------------------  ADMIN PAGE ------------------------------------NotDone
    @GetMapping("/admin")
    public String getAdmin(Model model)
    {
        return "admin_page";
    }
    @PostMapping("/admin")
    public String admin(@ModelAttribute User user, RedirectAttributes redirectAttributes)
    {
       return "admin_page";
    }
    //###########################------------------------################################

    //----------------------------  SALE PAGE -------------------------------------NotDone
    @GetMapping("/salesperson")
    public String getSalesperson(Model model)
    {
        return "salesperson_page";
    }
    @PostMapping("/salesperson")
    public String Salesperson(@ModelAttribute User user, RedirectAttributes redirectAttributes)
    {
        return "salesperson_page";
    }
    //###########################------------------------################################

}






