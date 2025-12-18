package dao;

import model.CheeseProduct;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheeseProductDAO {
    
    public boolean addProduct(CheeseProduct product) throws SQLException {
        if (!DatabaseConnection.isNotEmpty(product.getProductName()) ||
            !DatabaseConnection.isNotEmpty(product.getDescription()) ||
            !DatabaseConnection.isValidPrice(product.getPrice()) ||
            !DatabaseConnection.isValidStock(product.getStockQuantity())) {
            return false;
        }
        
        String sql = "INSERT INTO cheeseProduct (productName, cheeseType, description, price, stockQuantity, supplierId) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getCheeseType());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getStockQuantity());
            stmt.setInt(6, product.getSupplierId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<CheeseProduct> getAllProducts() throws SQLException {
        List<CheeseProduct> products = new ArrayList<>();
        String sql = "SELECT * FROM cheeseProduct ORDER BY productName";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                CheeseProduct product = new CheeseProduct();
                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setCheeseType(rs.getString("cheeseType"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStockQuantity(rs.getInt("stockQuantity"));
                product.setSupplierId(rs.getInt("supplierId"));
                products.add(product);
            }
        }
        return products;
    }
    
    public CheeseProduct getProductById(int productId) throws SQLException {
        String sql = "SELECT * FROM cheeseProduct WHERE productId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                CheeseProduct product = new CheeseProduct();
                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStockQuantity(rs.getInt("stockQuantity"));
                product.setSupplierId(rs.getInt("supplierId"));
                return product;
            }
        }
        return null;
    }
    
    public boolean updateProduct(CheeseProduct product) throws SQLException {
        if (!DatabaseConnection.isNotEmpty(product.getProductName()) ||
            !DatabaseConnection.isNotEmpty(product.getDescription()) ||
            !DatabaseConnection.isValidPrice(product.getPrice()) ||
            !DatabaseConnection.isValidStock(product.getStockQuantity())) {
            return false;
        }
        
        String sql = "UPDATE cheeseProduct SET productName=?, cheeseType=?, description=?, price=?, stockQuantity=?, supplierId=? WHERE productId=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getCheeseType());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getStockQuantity());
            stmt.setInt(6, product.getSupplierId());
            stmt.setInt(7, product.getProductId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM cheeseProduct WHERE productId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateStock(int productId, int newQuantity) throws SQLException {
        String sql = "UPDATE cheeseProduct SET stockQuantity = ? WHERE productId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, productId);
            
            return stmt.executeUpdate() > 0;
        }
    }
}