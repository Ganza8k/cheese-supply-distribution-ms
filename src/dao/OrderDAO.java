package dao;

import model.Order;
import model.OrderItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    
    public int createOrder(Order order) throws SQLException {
        if (order.getTotalAmount() <= 0 || !DatabaseConnection.isNotEmpty(order.getStatus())) {
            return -1;
        }
        
        String sql = "INSERT INTO orders (customerId, orderDate, totalAmount, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, order.getCustomerId());
            stmt.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setString(4, order.getStatus());
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return -1;
    }
    
    public boolean addOrderItem(OrderItem item) throws SQLException {
        if (!DatabaseConnection.isMinimumOrderQuantity(item.getQuantity()) || 
            !DatabaseConnection.isValidPrice(item.getUnitPrice())) {
            return false;
        }
        
        String sql = "INSERT INTO orderItem (orderId, productId, quantity, unitPrice) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, item.getOrderId());
            stmt.setInt(2, item.getProductId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getUnitPrice());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Order> getOrdersByCustomer(int customerId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customerId = ? ORDER BY orderDate DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderId"));
                order.setCustomerId(rs.getInt("customerId"));
                order.setOrderDate(rs.getTimestamp("orderDate"));
                order.setTotalAmount(rs.getDouble("totalAmount"));
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }
        }
        return orders;
    }
    
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY orderDate DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderId"));
                order.setCustomerId(rs.getInt("customerId"));
                order.setOrderDate(rs.getTimestamp("orderDate"));
                order.setTotalAmount(rs.getDouble("totalAmount"));
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }
        }
        return orders;
    }
    
    public boolean updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE orderId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<OrderItem> getOrderItems(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM orderItem WHERE orderId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderItemId(rs.getInt("orderItemId"));
                item.setOrderId(rs.getInt("orderId"));
                item.setProductId(rs.getInt("productId"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unitPrice"));
                items.add(item);
            }
        }
        return items;
    }
}