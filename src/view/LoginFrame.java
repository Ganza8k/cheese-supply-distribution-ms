package view;

import dao.CustomerDAO;
import model.Customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField emailField, roleField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private CustomerDAO customerDAO;

    public LoginFrame() {
        customerDAO = new CustomerDAO();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Email
        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        // Password
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Role
        add(new JLabel("Role (ADMIN/CUSTOMER):"));
        roleField = new JTextField("CUSTOMER");
        roleField.setFont(new Font("Arial", Font.BOLD, 14));
        add(roleField);

        // Buttons
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        add(loginButton);
        add(registerButton);

        // Action Listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String expectedRole = roleField.getText().trim().toUpperCase();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields");
            return;
        }

        try {
            Customer user = customerDAO.authenticateCustomer(email, password);
            if (user != null && expectedRole.equals(user.getRole())) {
                JOptionPane.showMessageDialog(this, "Login successful! Role: " + user.getRole());
                
                if ("ADMIN".equals(user.getRole())) {
                    JOptionPane.showMessageDialog(this, "Admin Dashboard would open here");
                } else {
                    JOptionPane.showMessageDialog(this, "Customer Dashboard would open here");
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials or wrong role");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}