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