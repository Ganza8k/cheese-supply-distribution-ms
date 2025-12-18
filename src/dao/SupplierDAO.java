package dao;

import model.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {
    
    public boolean addSupplier(Supplier supplier) throws SQLException {
        if (!DatabaseConnection.isNotEmpty(supplier.getSupplierName()) ||
            !DatabaseConnection.isNotEmpty(supplier.getContactPerson()) ||
            !DatabaseConnection.isValidEmail(supplier.getEmail()) ||
            (supplier.getPhone() != null && !supplier.getPhone().isEmpty() && !DatabaseConnection.isValidPhone(supplier.getPhone()))) {
            return false;
        }
        
        String sql = "INSERT INTO supplier (supplierName, contactPerson, email, phone, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getContactPerson());
            stmt.setString(3, supplier.getEmail());
            stmt.setString(4, supplier.getPhone());
            stmt.setString(5, supplier.getAddress());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM supplier ORDER BY supplierName";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getInt("supplierId"));
                supplier.setSupplierName(rs.getString("supplierName"));
                supplier.setContactPerson(rs.getString("contactPerson"));
                supplier.setEmail(rs.getString("email"));
                supplier.setPhone(rs.getString("phone"));
                supplier.setAddress(rs.getString("address"));
                suppliers.add(supplier);
            }
        }
        return suppliers;
    }
    
    public Supplier getSupplierById(int supplierId) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE supplierId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, supplierId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getInt("supplierId"));
                supplier.setSupplierName(rs.getString("supplierName"));
                supplier.setContactPerson(rs.getString("contactPerson"));
                supplier.setEmail(rs.getString("email"));
                supplier.setPhone(rs.getString("phone"));
                supplier.setAddress(rs.getString("address"));
                return supplier;
            }
        }
        return null;
    }
    
    public boolean updateSupplier(Supplier supplier) throws SQLException {
        if (!DatabaseConnection.isNotEmpty(supplier.getSupplierName()) ||
            !DatabaseConnection.isNotEmpty(supplier.getContactPerson()) ||
            !DatabaseConnection.isValidEmail(supplier.getEmail()) ||
            (supplier.getPhone() != null && !supplier.getPhone().isEmpty() && !DatabaseConnection.isValidPhone(supplier.getPhone()))) {
            return false;
        }
        
        String sql = "UPDATE supplier SET supplierName=?, contactPerson=?, email=?, phone=?, address=? WHERE supplierId=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getContactPerson());
            stmt.setString(3, supplier.getEmail());
            stmt.setString(4, supplier.getPhone());
            stmt.setString(5, supplier.getAddress());
            stmt.setInt(6, supplier.getSupplierId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteSupplier(int supplierId) throws SQLException {
        String sql = "DELETE FROM supplier WHERE supplierId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, supplierId);
            return stmt.executeUpdate() > 0;
        }
    }
}