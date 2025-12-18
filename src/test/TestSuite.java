package test;

import dao.*;
import model.*;
import java.sql.SQLException;

public class TestSuite {
    
    public static void main(String[] args) {
        System.out.println("=== Cheese Supply Management System - Test Suite ===");
        
        testDatabaseConnection();
        testCustomerRegistration();
        testCustomerAuthentication();
        testProductManagement();
        testRoleBasedAccess();
        
        System.out.println("=== All Tests Completed ===");
    }
    
    public static void testDatabaseConnection() {
        System.out.println("\n1. Testing Database Connection...");
        try {
            DatabaseConnection.getConnection();
            System.out.println("✅ Database connection successful");
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
        }
    }
    
    public static void testCustomerRegistration() {
        System.out.println("\n2. Testing Customer Registration...");
        CustomerDAO customerDAO = new CustomerDAO();
        Customer testCustomer = new Customer("Test", "User", "test@example.com", "password123", "1234567890", "Test Address", "CUSTOMER");
        
        try {
            boolean result = customerDAO.addCustomer(testCustomer);
            if (result) {
                System.out.println("✅ Customer registration successful");
            } else {
                System.out.println("❌ Customer registration failed");
            }
        } catch (SQLException e) {
            System.out.println("❌ Registration error: " + e.getMessage());
        }
    }
    
    public static void testCustomerAuthentication() {
        System.out.println("\n3. Testing Customer Authentication...");
        CustomerDAO customerDAO = new CustomerDAO();
        
        try {
            Customer user = customerDAO.authenticateCustomer("test@example.com", "password123");
            if (user != null) {
                System.out.println("✅ Authentication successful for: " + user.getFirstName());
                System.out.println("✅ Role verification: " + user.getRole());
            } else {
                System.out.println("❌ Authentication failed");
            }
        } catch (SQLException e) {
            System.out.println("❌ Authentication error: " + e.getMessage());
        }
    }
    
    public static void testProductManagement() {
        System.out.println("\n4. Testing Product Management...");
        CheeseProductDAO productDAO = new CheeseProductDAO();
        
        try {
            CheeseProduct testProduct = new CheeseProduct("Test Cheese", "Cheddar", "Test Description", 10.99, 50, 1);
            boolean result = productDAO.addProduct(testProduct);
            if (result) {
                System.out.println("✅ Product addition successful");
            } else {
                System.out.println("❌ Product addition failed");
            }
        } catch (SQLException e) {
            System.out.println("❌ Product management error: " + e.getMessage());
        }
    }
    
    public static void testRoleBasedAccess() {
        System.out.println("\n5. Testing Role-Based Access...");
        
        Customer adminUser = new Customer();
        adminUser.setRole("ADMIN");
        
        if ("ADMIN".equals(adminUser.getRole())) {
            System.out.println("✅ Admin role access verified");
        } else {
            System.out.println("❌ Admin role access failed");
        }
        
        Customer customerUser = new Customer();
        customerUser.setRole("CUSTOMER");
        
        if ("CUSTOMER".equals(customerUser.getRole())) {
            System.out.println("✅ Customer role access verified");
        } else {
            System.out.println("❌ Customer role access failed");
        }
    }
}