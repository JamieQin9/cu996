package org.dadeco.cu996.api.controller;


import org.dadeco.cu996.api.model.RuntimeUserInfo;
import org.dadeco.cu996.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;


@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RuntimeUserInfo currentUser = (RuntimeUserInfo) auth.getPrincipal();

        session.setAttribute("currentUser", currentUser);
        return "overall_capacity_overview";
    }

}

