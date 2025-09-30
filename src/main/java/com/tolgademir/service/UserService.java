package com.tolgademir.service;

import com.tolgademir.dao.UserDao;
import com.tolgademir.model.User;

/**
 * Service layer for user operations
 * // Kullanıcı işlemleri için iş kurallarını yöneten katman
 */
public class UserService {
    private final UserDao userDao = new UserDao();

    /**
     * Register a new user
     * // Yeni kullanıcı kaydı oluşturur
     */
    public boolean registerUser(User user) {

        // --- Validation rules ---
        if (user.getName() == null || user.getName().isBlank()) {
            throw new RuntimeException("❌ Name cannot be empty.");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new RuntimeException("❌ Invalid email format.");
        }
        if (!user.getRole().equalsIgnoreCase("ADMIN") &&
                !user.getRole().equalsIgnoreCase("CUSTOMER")) {
            throw new RuntimeException("❌ Role must be ADMIN or CUSTOMER.");
        }
        if (!user.getAccountType().equalsIgnoreCase("INDIVIDUAL") &&
                !user.getAccountType().equalsIgnoreCase("CORPORATE")) {
            throw new RuntimeException("❌ Account type must be INDIVIDUAL or CORPORATE.");
        }
        if (user.getAge() <= 0) {
            throw new RuntimeException("❌ Age must be greater than 0.");
        }

        return userDao.insert(user);
    }

    /**
     * Login with email and passwordHash
     * // Email ve şifre ile giriş yapar
     */
    public User login(String email, String passwordHash) {
        return userDao.findByEmailAndPassword(email, passwordHash);
    }

    /**
     * Find user by ID
     * // ID ile kullanıcı bulur
     */
    public User getUserById(int id) {
        return userDao.findById(id);
    }
}