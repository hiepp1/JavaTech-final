package com.posweb.website.Service;

import com.posweb.website.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RoleService implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        User user = (User) request.getSession().getAttribute("loggedInUser");
        if (user != null && user.getRole().equals("ADMIN")) {
            return true;
        } else {
            response.sendRedirect("/error");
            return false;
        }
    }
}