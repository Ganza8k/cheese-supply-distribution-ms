package dao;

import model.Admin;
import java.sql.*;

public class AdminDAO {
    
    public Admin authenticateAdmin(String email, String password) throws SQLException {
        String sql = "SELECT * FROM admin WHERE TRIM(email) = TRIM(?) AND TRIM(password) = TRIM(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email.trim());
            stmt.setString(2, password.trim());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getInt("adminId"));
                admin.setFirstName(rs.getString("firstName"));
                admin.setLastName(rs.getString("lastName"));
                admin.setEmail(rs.getString("email"));
                admin.setPassword(rs.getString("password"));
                admin.setPhone(rs.getString("phone"));
                admin.setAddress(rs.getString("address"));
                return admin;
            }
        }
        return null;
    }
}