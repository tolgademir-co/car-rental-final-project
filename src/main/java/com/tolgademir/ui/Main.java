package com.tolgademir.ui;

import com.tolgademir.model.User;
import com.tolgademir.service.UserService;
import com.tolgademir.util.HashUtil;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main entry point of the application
 * // Uygulamanın giriş noktası
 */
public class Main {
    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("=== RENT-A-CAR SYSTEM ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Select: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // buffer temizle

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 0 -> {
                    System.out.println("👋 Goodbye!");
                    return;
                }
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }

    /**
     * Register flow
     * // Yeni kullanıcı kaydı akışı
     */
    private static void register() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            System.out.print("Role (ADMIN/CUSTOMER): ");
            String role = scanner.nextLine();
            System.out.print("Account Type (INDIVIDUAL/CORPORATE): ");
            String accountType = scanner.nextLine();
            System.out.print("Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();

            String passwordHash = HashUtil.sha256(password);
            User user = new User(0, name, email, passwordHash, role, accountType, age);

            userService.registerUser(user);

        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input type. Please enter numbers where required.");
            scanner.nextLine(); // buffer temizle
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Login flow
     * // Kullanıcı girişi akışı
     */
    private static void login() {
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            String passwordHash = HashUtil.sha256(password);
            User user = userService.login(email, passwordHash);

            if (user == null) {
                System.out.println("❌ Invalid credentials.");
                return;
            }

            System.out.println("✅ Login successful: " + user.getEmail());

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                new AdminMenu(user).show();
            } else {
                new CustomerMenu(user).show();
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}