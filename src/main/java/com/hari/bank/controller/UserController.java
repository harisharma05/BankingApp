package com.hari.bank.controller;

import com.hari.bank.model.User;
import com.hari.bank.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password) {
        try {
            User user = userService.register(username, password);
            return ResponseEntity.ok("User registered: " + user.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user == null) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        return ResponseEntity.ok("Login successful for: " + user.getUsername());
    }

    @GetMapping("/balance")
    public ResponseEntity<?> balance(@RequestParam String username) {
        double balance = userService.getBalance(username);
        return ResponseEntity.ok("Balance for " + username + ": " + balance);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestParam String sender,
                                      @RequestParam String recipient,
                                      @RequestParam double amount) {
        boolean success = userService.transfer(sender, recipient, amount);
        if (success) {
            return ResponseEntity.ok("Transfer successful");
        } else {
            return ResponseEntity.badRequest().body("Transfer failed");
        }
    }
}
