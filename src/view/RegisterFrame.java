package view;

import dao.CustomerDAO;
import model.Customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {
    private JTextField firstNameField, lastNameField, emailField, phoneField, addressField;
    private JPasswordField passwordField, confirmPasswordField;
    private JTextField roleField;
    private JButton registerButton, backButton;
    private CustomerDAO customerDAO;

    public RegisterFrame() {
        customerDAO = new CustomerDAO();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLayout(new GridLayout(10, 2, 10, 10));

        // Role
        add(new JLabel("Role (ADMIN/CUSTOMER):"));
        roleField = new JTextField("CUSTOMER");
        roleField.setFont(new Font("Arial", Font.BOLD, 14));
        add(roleField);

        // First Name
        add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        add(firstNameField);

        // Last Name
        add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        add(lastNameField);

        // Email
        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        // Password
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Confirm Password
        add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField();
        add(confirmPasswordField);

        // Phone
        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        // Address
        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        // Buttons
        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");
        add(registerButton);
        add(backButton);

        // Action Listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void registerUser() {
        String role = roleField.getText().trim().toUpperCase();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
            return;
        }

        try {
            Customer customer = new Customer(firstName, lastName, email, password, phone, address, role);
            if (customerDAO.addCustomer(customer)) {
                JOptionPane.showMessageDialog(this, "Registration successful! Role: " + role);
                new LoginFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
}