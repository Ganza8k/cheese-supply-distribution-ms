package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cheese_supply_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }
    
  
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }
    
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10,15}");
    }
    
    public static boolean isValidPrice(double price) {
        return price > 0;
    }
    
    public static boolean isValidQuantity(int quantity) {
        return quantity > 0;
    }
    
    public static boolean isValidStock(int stock) {
        return stock >= 0;
    }
    
    public static boolean isMinimumOrderQuantity(int quantity) {
        return quantity >= 1;
    }
    
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}