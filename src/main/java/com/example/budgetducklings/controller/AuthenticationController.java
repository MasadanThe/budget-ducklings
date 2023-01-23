package com.example.budgetducklings.controller;

import com.example.budgetducklings.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class AuthenticationController {
    private AuthenticationService authenticationService;

    public AuthenticationController(){
        authenticationService = new AuthenticationService();
    }

    @GetMapping("login")
    public String showLoginPage(HttpSession session)
    {
        if (session.getAttribute("username") != null){
            return "redirect:invoice";
        }
        return "redirect:homePage.html";
    }
    @PostMapping("login")
    public String login(HttpSession session, @RequestParam String username, @RequestParam String password){
        //If you logged in correctly
        if(authenticationService.login(username,password))
        {
            session.setAttribute("username", username);
            session.setMaxInactiveInterval(60 * 30);
        }

        return "redirect:/invoice";
    }

    @PostMapping("logout")
    public String logout(HttpSession session)
    {
        session.invalidate();

        return "redirect:login";
    }
}
