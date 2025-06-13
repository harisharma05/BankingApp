package com.hari.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hari.bank.model.User;
import com.hari.bank.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        double balance = userService.getBalance(user.getUsername());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("balance", balance);
        return "dashboard"; // dashboard.html
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String recipient,
                           @RequestParam double amount,
                           HttpSession session,
                           Model model) {
        User sender = (User) session.getAttribute("user");
        if (sender == null) {
            return "redirect:/login";
        }
        boolean success = userService.transfer(sender.getUsername(), recipient, amount);
        if (!success) {
            model.addAttribute("error", "Transfer failed. Check recipient or balance.");
        }
        return "redirect:/dashboard";
    }
}
