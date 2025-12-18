package dao;

import model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    
    public boolean addCustomer(Customer customer) throws SQLException {
        if (!DatabaseConnection.isValidEmail(customer.getEmail()) || 
            !DatabaseConnection.isNotEmpty(customer.getFirstName()) ||
            !DatabaseConnection.isNotEmpty(customer.getLastName()) ||
            !DatabaseConnection.isNotEmpty(customer.getPassword()) ||
            (customer.getPhone() != null && !customer.getPhone().isEmpty() && !DatabaseConnection.isValidPhone(customer.getPhone()))) {
            return false;
        }
        
        String sql = "INSERT INTO customer (firstName, lastName, email, password, phone, address, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPassword());
            stmt.setString(5, customer.getPhone());
            stmt.setString(6, customer.getAddress());
            stmt.setString(7, customer.getRole() != null ? customer.getRole() : "CUSTOMER");
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public Customer authenticateCustomer(String email, String password) throws SQLException {
        String sql = "SELECT * FROM customer WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customerId"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setLastName(rs.getString("lastName"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setRole(rs.getString("role"));
                return customer;
            }
        }
        return null;
    }
    
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY firstName, lastName";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customerId"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setLastName(rs.getString("lastName"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setRole(rs.getString("role"));
                customers.add(customer);
            }
        }
        return customers;
    }
    
    public boolean updateCustomer(Customer customer) throws SQLException {
        if (!DatabaseConnection.isValidEmail(customer.getEmail()) || 
            !DatabaseConnection.isNotEmpty(customer.getFirstName()) ||
            !DatabaseConnection.isNotEmpty(customer.getLastName()) ||
            (customer.getPhone() != null && !customer.getPhone().isEmpty() && !DatabaseConnection.isValidPhone(customer.getPhone()))) {
            return false;
        }
        
        String sql = "UPDATE customer SET firstName=?, lastName=?, email=?, phone=?, address=? WHERE customerId=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getAddress());
            stmt.setInt(6, customer.getCustomerId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customer WHERE customerId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;
        }
    }
}