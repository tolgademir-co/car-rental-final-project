package com.tolgademir.model;

/**
 * User entity -> users tablosunun karşılığıdır
 * // Kullanıcı bilgilerini ve rollerini tutar
 */
public class User {
    private int id;
    private String name;
    private String email;
    private String passwordHash;
    private String role;        // ADMIN | CUSTOMER
    private String accountType; // INDIVIDUAL | CORPORATE
    private int age;

    // --- Constructor ---
    public User(int id, String name, String email, String passwordHash, String role, String accountType, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.accountType = accountType;
        this.age = age;
    }

    // --- Getters & Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", accountType='" + accountType + '\'' +
                ", age=" + age +
                '}';
    }
}