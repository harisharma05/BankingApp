# Sharma Banking

A simple banking web application built with Spring Boot. Users can register, log in, view their balance, and transfer money.

## Features

- User registration and login
- Dashboard with balance display
- Transfer money between users
- Session-based authentication
- Logout functionality
- Clean HTML views with Thymeleaf

## Tech Stack

- Java
- Spring Boot
- Thymeleaf
- Maven
- HTML/CSS

## Getting Started

1. **Clone the repository**

```bash
git clone https://github.com/yourusername/sharma-bank.git
cd sharma-bank
```

2. **Build and run**

Make sure you have Java 17+ and Maven installed.

```bash
./mvnw spring-boot:run
```

Visit `http://localhost:8080` in your browser.

## Folder Structure

```
src/
├── main/
│   ├── java/com/hari/bank/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── service/
│   │   └── SharmaBankApplication.java
│   └── resources/
│       ├── templates/
│       │   ├── home.html
│       │   ├── login.html
│       │   ├── register.html
│       │   └── dashboard.html
│       └── application.properties
```

## Screens

- **Home**: Welcome screen with Login/Register options
- **Login/Register**: Simple forms
- **Dashboard**: Shows balance, transfer form, and logout button

## Enjoy!
