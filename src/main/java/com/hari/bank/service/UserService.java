package com.hari.bank.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.hari.bank.model.User;

import jakarta.annotation.PostConstruct;

@Service
public class UserService implements InitializingBean {

    private final Map<String, User> users = new HashMap<>();
    private final Path usersFile = Paths.get("data/users.txt");

    @PostConstruct
    public void init() {
        loadUsers();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadUsers();
    }

    private void loadUsers() {
        users.clear();
        if (!Files.exists(usersFile)) return;

        try (BufferedReader br = Files.newBufferedReader(usersFile)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    users.put(username, new User(username, password, balance));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        try {
            Files.createDirectories(usersFile.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(usersFile)) {
                for (User user : users.values()) {
                    bw.write(user.getUsername() + ";" + user.getPassword() + ";" + user.getBalance());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User register(String username, String password) {
        username = username.toLowerCase();
        if (users.containsKey(username)) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User(username, password, 100);
        users.put(username, user);
        saveUsers();
        return user;
    }

    public User login(String username, String password) {
        username = username.toLowerCase();
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public double getBalance(String username) {
        User user = users.get(username.toLowerCase());
        return user != null ? user.getBalance() : 0.0;
    }

    public boolean transfer(String senderUsername, String recipientUsername, double amount) {
        senderUsername = senderUsername.toLowerCase();
        recipientUsername = recipientUsername.toLowerCase();

        User sender = users.get(senderUsername);
        User recipient = users.get(recipientUsername);

        if (sender == null || recipient == null) return false;
        if (sender.getBalance() < amount) return false;

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        saveUsers();
        return true;
    }
}
