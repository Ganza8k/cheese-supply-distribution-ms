package dao;

import model.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    
    public boolean addPayment(Payment payment) throws SQLException {
        if (!DatabaseConnection.isValidPrice(payment.getAmount()) ||
            !DatabaseConnection.isNotEmpty(payment.getPaymentMethod()) ||
            !DatabaseConnection.isNotEmpty(payment.getStatus())) {
            return false;
        }
        
        String sql = "INSERT INTO payments (orderId, amount, paymentDate, paymentMethod, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, payment.getOrderId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setTimestamp(3, new Timestamp(payment.getPaymentDate().getTime()));
            stmt.setString(4, payment.getPaymentMethod());
            stmt.setString(5, payment.getStatus());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Payment> getAllPayments() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments ORDER BY paymentDate DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("paymentId"));
                payment.setOrderId(rs.getInt("orderId"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentDate(rs.getTimestamp("paymentDate"));
                payment.setPaymentMethod(rs.getString("paymentMethod"));
                payment.setStatus(rs.getString("status"));
                payments.add(payment);
            }
        }
        return payments;
    }
    
    public Payment getPaymentByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM payments WHERE orderId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("paymentId"));
                payment.setOrderId(rs.getInt("orderId"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentDate(rs.getTimestamp("paymentDate"));
                payment.setPaymentMethod(rs.getString("paymentMethod"));
                payment.setStatus(rs.getString("status"));
                return payment;
            }
        }
        return null;
    }
}