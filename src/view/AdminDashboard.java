package view;

import dao.*;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private JTable productsTable, suppliersTable, customersTable, ordersTable;
    private DefaultTableModel productsModel, suppliersModel, customersModel, ordersModel;
    private CheeseProductDAO productDAO;
    private SupplierDAO supplierDAO;
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;

    public AdminDashboard() {
        this.productDAO = new CheeseProductDAO();
        this.supplierDAO = new SupplierDAO();
        this.customerDAO = new CustomerDAO();
        this.orderDAO = new OrderDAO();
        initComponents();
        loadAllData();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Admin Dashboard - Cheese Supply Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0xFAF9F6));

        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(0x22543D), 0, getHeight(), new Color(0x2F855A));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 80));
        JLabel titleLabel = new JLabel("Admin Dashboard - Cheese Supply Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(25, 0, 25, 0));
        headerPanel.add(titleLabel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(0xFAF9F6));
        tabbedPane.setForeground(new Color(0x2D3748));
        tabbedPane.addTab("Products", createProductsPanel());
        tabbedPane.addTab("Suppliers", createSuppliersPanel());
        tabbedPane.addTab("Customers", createCustomersPanel());
        tabbedPane.addTab("Orders", createOrdersPanel());
        tabbedPane.addTab("Inventory", createInventoryPanel());
        tabbedPane.addTab("Reports", createReportsPanel());

        JButton logoutButton = createStyledButton("Logout", new Color(0x22543D), new Color(0x2F855A));
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(0xFAF9F6));
        bottomPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        bottomPanel.add(logoutButton);

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xFAF9F6));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        String[] columns = {"ID", "Name", "Type", "Description", "Price", "Stock", "Supplier ID"};
        productsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        productsTable = new JTable(productsModel);
        styleTable(productsTable);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(0xFAF9F6));
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        JButton addBtn = createStyledButton("Add Product", new Color(0x2F855A), new Color(0x22543D));
        JButton updateBtn = createStyledButton("Update Product", new Color(0xD69E2E), new Color(0x2F855A));
        JButton deleteBtn = createStyledButton("Delete Product", new Color(0x22543D), new Color(0x2F855A));
        JButton refreshBtn = createStyledButton("Refresh", new Color(0x718096), new Color(0x2D3748));

        addBtn.addActionListener(e -> addProduct());
        updateBtn.addActionListener(e -> updateProduct());
        deleteBtn.addActionListener(e -> deleteProduct());
        refreshBtn.addActionListener(e -> loadProducts());

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        panel.add(new JScrollPane(productsTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createSuppliersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xFAF9F6));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        String[] columns = {"ID", "Name", "Contact Person", "Email", "Phone", "Address"};
        suppliersModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        suppliersTable = new JTable(suppliersModel);
        styleTable(suppliersTable);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(248, 249, 250));
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        JButton addBtn = createStyledButton("Add Supplier", new Color(0x2F855A), new Color(0x22543D));
        JButton updateBtn = createStyledButton("Update Supplier", new Color(0xD69E2E), new Color(0x2F855A));
        JButton deleteBtn = createStyledButton("Delete Supplier", new Color(0x22543D), new Color(0x2F855A));
        JButton refreshBtn = createStyledButton("Refresh", new Color(0x718096), new Color(0x2D3748));

        addBtn.addActionListener(e -> addSupplier());
        updateBtn.addActionListener(e -> updateSupplier());
        deleteBtn.addActionListener(e -> deleteSupplier());
        refreshBtn.addActionListener(e -> loadSuppliers());

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        panel.add(new JScrollPane(suppliersTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCustomersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xFAF9F6));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        String[] columns = {"ID", "First Name", "Last Name", "Email", "Phone", "Address"};
        customersModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        customersTable = new JTable(customersModel);
        styleTable(customersTable);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(248, 249, 250));
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        JButton deleteBtn = createStyledButton("Delete Customer", new Color(0x22543D), new Color(0x2F855A));
        JButton refreshBtn = createStyledButton("Refresh", new Color(0x718096), new Color(0x2D3748));

        deleteBtn.addActionListener(e -> deleteCustomer());
        refreshBtn.addActionListener(e -> loadCustomers());

        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        panel.add(new JScrollPane(customersTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xFAF9F6));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        String[] columns = {"Order ID", "Customer ID", "Date", "Total", "Status"};
        ordersModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        ordersTable = new JTable(ordersModel);
        styleTable(ordersTable);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(248, 249, 250));
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        JButton updateStatusBtn = createStyledButton("Update Status", new Color(0xD69E2E), new Color(0x2F855A));
        JButton refreshBtn = createStyledButton("Refresh", new Color(0x718096), new Color(0x2D3748));

        updateStatusBtn.addActionListener(e -> updateOrderStatus());
        refreshBtn.addActionListener(e -> loadOrders());

        buttonPanel.add(updateStatusBtn);
        buttonPanel.add(refreshBtn);

        panel.add(new JScrollPane(ordersTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMinimumSize(new Dimension(120, 38));
        button.setPreferredSize(new Dimension(150, 38));
        button.setMaximumSize(new Dimension(200, 38));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setBackground(Color.WHITE);
        table.setForeground(new Color(0x2D3748));
        table.setSelectionBackground(new Color(0x2F855A));
        table.setSelectionForeground(new Color(0xFFFFFF));
        table.setGridColor(new Color(0x718096));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(0x22543D));
        table.getTableHeader().setForeground(new Color(0xFFFFFF));
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void loadAllData() {
        loadProducts();
        loadSuppliers();
        loadCustomers();
        loadOrders();
    }

    private void loadProducts() {
        try {
            List<CheeseProduct> products = productDAO.getAllProducts();
            productsModel.setRowCount(0);
            for (CheeseProduct product : products) {
                Object[] row = {product.getProductId(), product.getProductName(), 
                               product.getCheeseType(), product.getDescription(), product.getPrice(), 
                               product.getStockQuantity(), product.getSupplierId()};
                productsModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + ex.getMessage());
        }
    }

    private void loadSuppliers() {
        try {
            List<Supplier> suppliers = supplierDAO.getAllSuppliers();
            suppliersModel.setRowCount(0);
            for (Supplier supplier : suppliers) {
                Object[] row = {supplier.getSupplierId(), supplier.getSupplierName(),
                               supplier.getContactPerson(), supplier.getEmail(),
                               supplier.getPhone(), supplier.getAddress()};
                suppliersModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading suppliers: " + ex.getMessage());
        }
    }

    private void loadCustomers() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            customersModel.setRowCount(0);
            for (Customer customer : customers) {
                Object[] row = {customer.getCustomerId(), customer.getFirstName(),
                               customer.getLastName(), customer.getEmail(),
                               customer.getPhone(), customer.getAddress()};
                customersModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading customers: " + ex.getMessage());
        }
    }

    private void loadOrders() {
        try {
            List<Order> orders = orderDAO.getAllOrders();
            ordersModel.setRowCount(0);
            for (Order order : orders) {
                Object[] row = {order.getOrderId(), order.getCustomerId(),
                               order.getOrderDate(), order.getTotalAmount(), order.getStatus()};
                ordersModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading orders: " + ex.getMessage());
        }
    }

    private void addProduct() {
        JTextField nameField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField descField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField supplierField = new JTextField();

        Object[] message = {
            "Product Name:", nameField,
            "Cheese Type:", typeField,
            "Description:", descField,
            "Price:", priceField,
            "Stock Quantity:", stockField,
            "Supplier ID:", supplierField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Product", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String type = typeField.getText().trim();
                String description = descField.getText().trim();
                String priceText = priceField.getText().trim();
                String stockText = stockField.getText().trim();
                String supplierText = supplierField.getText().trim();
                
                // Validation: Prevent empty fields
                if (name.isEmpty() || type.isEmpty() || description.isEmpty() || priceText.isEmpty() || stockText.isEmpty() || supplierText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);
                int supplierId = Integer.parseInt(supplierText);
                
                // Business validation: Prices cannot be negative or zero
                if (price <= 0) {
                    JOptionPane.showMessageDialog(this, "Price must be greater than zero", "Invalid Price", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Business validation: Stock cannot be negative
                if (stock < 0) {
                    JOptionPane.showMessageDialog(this, "Stock quantity cannot be negative", "Invalid Stock", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                CheeseProduct product = new CheeseProduct(name, type, description, price, stock, supplierId);
                
                if (productDAO.addProduct(product)) {
                    JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadProducts();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add product. Please check your input.", "Add Product Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for price, stock, and supplier ID", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateProduct() {
        int selectedRow = productsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product to update", "Selection Required", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int productId = (Integer) productsModel.getValueAt(selectedRow, 0);
        String currentName = (String) productsModel.getValueAt(selectedRow, 1);
        String currentType = (String) productsModel.getValueAt(selectedRow, 2);
        String currentDesc = (String) productsModel.getValueAt(selectedRow, 3);
        Double currentPrice = (Double) productsModel.getValueAt(selectedRow, 4);
        Integer currentStock = (Integer) productsModel.getValueAt(selectedRow, 5);
        Integer currentSupplier = (Integer) productsModel.getValueAt(selectedRow, 6);

        JTextField nameField = new JTextField(currentName);
        JTextField typeField = new JTextField(currentType);
        JTextField descField = new JTextField(currentDesc);
        JTextField priceField = new JTextField(currentPrice.toString());
        JTextField stockField = new JTextField(currentStock.toString());
        JTextField supplierField = new JTextField(currentSupplier.toString());

        Object[] message = {
            "Product Name:", nameField,
            "Cheese Type:", typeField,
            "Description:", descField,
            "Price:", priceField,
            "Stock Quantity:", stockField,
            "Supplier ID:", supplierField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Update Product", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String type = typeField.getText().trim();
                String description = descField.getText().trim();
                String priceText = priceField.getText().trim();
                String stockText = stockField.getText().trim();
                String supplierText = supplierField.getText().trim();
                
                // Validation: Prevent empty fields
                if (name.isEmpty() || type.isEmpty() || description.isEmpty() || priceText.isEmpty() || stockText.isEmpty() || supplierText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);
                int supplierId = Integer.parseInt(supplierText);
                
                // Business validation: Prices cannot be negative or zero
                if (price <= 0) {
                    JOptionPane.showMessageDialog(this, "Price must be greater than zero", "Invalid Price", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Business validation: Stock cannot be negative
                if (stock < 0) {
                    JOptionPane.showMessageDialog(this, "Stock quantity cannot be negative", "Invalid Stock", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                CheeseProduct product = new CheeseProduct(name, type, description, price, stock, supplierId);
                product.setProductId(productId);
                
                if (productDAO.updateProduct(product)) {
                    JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadProducts();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update product. Please check your input.", "Update Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for price, stock, and supplier ID", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteProduct() {
        int selectedRow = productsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete");
            return;
        }

        int productId = (Integer) productsModel.getValueAt(selectedRow, 0);
        String productName = (String) productsModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete product: " + productName + "?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (productDAO.deleteProduct(productId)) {
                    JOptionPane.showMessageDialog(this, "Product deleted successfully!");
                    loadProducts();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete product");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void addSupplier() {
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();

        Object[] message = {
            "Supplier Name:", nameField,
            "Contact Person:", contactField,
            "Email:", emailField,
            "Phone:", phoneField,
            "Address:", addressField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Supplier", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String contact = contactField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String address = addressField.getText().trim();
                
                // Validation: Prevent empty required fields
                if (name.isEmpty() || contact.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all required fields (Name, Contact Person, Email)", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate email format
                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid email address", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate phone format if provided
                if (!phone.isEmpty() && !isValidPhone(phone)) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid phone number (10-15 digits)", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Supplier supplier = new Supplier(name, contact, email, phone, address);
                
                if (supplierDAO.addSupplier(supplier)) {
                    JOptionPane.showMessageDialog(this, "Supplier added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadSuppliers();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add supplier. Please check your input.", "Add Supplier Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(emailRegex);
    }
    
    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10,15}");
    }

    private void updateSupplier() {
        int selectedRow = suppliersTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a supplier to update", "Selection Required", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int supplierId = (Integer) suppliersModel.getValueAt(selectedRow, 0);
        String currentName = (String) suppliersModel.getValueAt(selectedRow, 1);
        String currentContact = (String) suppliersModel.getValueAt(selectedRow, 2);
        String currentEmail = (String) suppliersModel.getValueAt(selectedRow, 3);
        String currentPhone = (String) suppliersModel.getValueAt(selectedRow, 4);
        String currentAddress = (String) suppliersModel.getValueAt(selectedRow, 5);

        JTextField nameField = new JTextField(currentName);
        JTextField contactField = new JTextField(currentContact);
        JTextField emailField = new JTextField(currentEmail);
        JTextField phoneField = new JTextField(currentPhone);
        JTextField addressField = new JTextField(currentAddress);

        Object[] message = {
            "Supplier Name:", nameField,
            "Contact Person:", contactField,
            "Email:", emailField,
            "Phone:", phoneField,
            "Address:", addressField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Update Supplier", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String contact = contactField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String address = addressField.getText().trim();
                
                // Validation: Prevent empty required fields
                if (name.isEmpty() || contact.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all required fields (Name, Contact Person, Email)", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate email format
                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid email address", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate phone format if provided
                if (!phone.isEmpty() && !isValidPhone(phone)) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid phone number (10-15 digits)", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Supplier supplier = new Supplier(name, contact, email, phone, address);
                supplier.setSupplierId(supplierId);
                
                if (supplierDAO.updateSupplier(supplier)) {
                    JOptionPane.showMessageDialog(this, "Supplier updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadSuppliers();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update supplier. Please check your input.", "Update Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteSupplier() {
        int selectedRow = suppliersTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a supplier to delete");
            return;
        }

        int supplierId = (Integer) suppliersModel.getValueAt(selectedRow, 0);
        String supplierName = (String) suppliersModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete supplier: " + supplierName + "?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (supplierDAO.deleteSupplier(supplierId)) {
                    JOptionPane.showMessageDialog(this, "Supplier deleted successfully!");
                    loadSuppliers();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete supplier");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void deleteCustomer() {
        int selectedRow = customersTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete");
            return;
        }

        int customerId = (Integer) customersModel.getValueAt(selectedRow, 0);
        String customerName = customersModel.getValueAt(selectedRow, 1) + " " + customersModel.getValueAt(selectedRow, 2);

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete customer: " + customerName + "?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (customerDAO.deleteCustomer(customerId)) {
                    JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
                    loadCustomers();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete customer");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void updateOrderStatus() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an order to update", "Selection Required", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int orderId = (Integer) ordersModel.getValueAt(selectedRow, 0);
        String currentStatus = (String) ordersModel.getValueAt(selectedRow, 4);

        // Business validation: Order status can only be updated by admin
        // This validation is implicit since only admin can access this function
        
        if ("Cancelled".equals(currentStatus)) {
            JOptionPane.showMessageDialog(this, "Cannot update status of cancelled orders", "Status Update Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ("Delivered".equals(currentStatus)) {
            JOptionPane.showMessageDialog(this, "Cannot update status of delivered orders", "Status Update Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] statuses = {"Pending", "Processing", "Shipped", "Delivered", "Cancelled"};
        String newStatus = (String) JOptionPane.showInputDialog(this, 
            "Select new status for Order #" + orderId + ":\nCurrent Status: " + currentStatus, "Update Order Status", 
            JOptionPane.QUESTION_MESSAGE, null, statuses, currentStatus);

        if (newStatus != null && !newStatus.equals(currentStatus)) {
            try {
                if (orderDAO.updateOrderStatus(orderId, newStatus)) {
                    JOptionPane.showMessageDialog(this, "Order #" + orderId + " status updated to: " + newStatus, "Status Updated", JOptionPane.INFORMATION_MESSAGE);
                    loadOrders();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update order status. Please try again.", "Update Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error while updating order status: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xFAF9F6));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextArea inventoryArea = new JTextArea();
        inventoryArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        inventoryArea.setEditable(false);
        inventoryArea.setBackground(Color.WHITE);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(0xFAF9F6));
        JButton checkStockBtn = createStyledButton("Check Low Stock", new Color(0xD69E2E), new Color(0x2F855A));
        JButton replenishBtn = createStyledButton("Auto Replenish", new Color(0x2F855A), new Color(0x22543D));
        
        checkStockBtn.addActionListener(e -> checkLowStock(inventoryArea));
        replenishBtn.addActionListener(e -> autoReplenishStock(inventoryArea));
        
        buttonPanel.add(checkStockBtn);
        buttonPanel.add(replenishBtn);
        
        panel.add(new JScrollPane(inventoryArea), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xFAF9F6));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextArea reportsArea = new JTextArea();
        reportsArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        reportsArea.setEditable(false);
        reportsArea.setBackground(Color.WHITE);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(0xFAF9F6));
        JButton salesReportBtn = createStyledButton("Sales Report", new Color(0x2F855A), new Color(0x22543D));
        JButton popularProductsBtn = createStyledButton("Popular Products", new Color(0xD69E2E), new Color(0x2F855A));
        
        salesReportBtn.addActionListener(e -> generateSalesReport(reportsArea));
        popularProductsBtn.addActionListener(e -> generatePopularProductsReport(reportsArea));
        
        buttonPanel.add(salesReportBtn);
        buttonPanel.add(popularProductsBtn);
        
        panel.add(new JScrollPane(reportsArea), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void checkLowStock(JTextArea area) {
        try {
            List<CheeseProduct> products = productDAO.getAllProducts();
            StringBuilder report = new StringBuilder("LOW STOCK ALERT REPORT\n");
            report.append("=========================\n\n");
            
            boolean hasLowStock = false;
            for (CheeseProduct product : products) {
                if (product.getStockQuantity() <= 10) { // Minimum threshold
                    hasLowStock = true;
                    report.append(String.format("⚠️ %s - Stock: %d (ID: %d)\n", 
                        product.getProductName(), product.getStockQuantity(), product.getProductId()));
                }
            }
            
            if (!hasLowStock) {
                report.append("✅ All products have sufficient stock levels.\n");
            }
            
            area.setText(report.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error checking stock: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void autoReplenishStock(JTextArea area) {
        try {
            List<CheeseProduct> products = productDAO.getAllProducts();
            StringBuilder report = new StringBuilder("AUTO REPLENISHMENT REPORT\n");
            report.append("===========================\n\n");
            
            int replenishedCount = 0;
            for (CheeseProduct product : products) {
                if (product.getStockQuantity() <= 10) {
                    int newStock = product.getStockQuantity() + 50; // Add 50 units
                    if (productDAO.updateStock(product.getProductId(), newStock)) {
                        replenishedCount++;
                        report.append(String.format("✅ %s - Replenished from %d to %d\n", 
                            product.getProductName(), product.getStockQuantity(), newStock));
                    }
                }
            }
            
            if (replenishedCount == 0) {
                report.append("ℹ️ No products required replenishment.\n");
            } else {
                report.append(String.format("\n📦 Total products replenished: %d\n", replenishedCount));
            }
            
            area.setText(report.toString());
            loadProducts(); // Refresh products table
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error replenishing stock: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generateSalesReport(JTextArea area) {
        try {
            List<Order> orders = orderDAO.getAllOrders();
            StringBuilder report = new StringBuilder("SALES REPORT\n");
            report.append("=============\n\n");
            
            double totalSales = 0;
            int totalOrders = 0;
            int deliveredOrders = 0;
            
            for (Order order : orders) {
                totalOrders++;
                if ("Delivered".equals(order.getStatus())) {
                    totalSales += order.getTotalAmount();
                    deliveredOrders++;
                }
            }
            
            report.append(String.format("📊 Total Orders: %d\n", totalOrders));
            report.append(String.format("✅ Delivered Orders: %d\n", deliveredOrders));
            report.append(String.format("💰 Total Sales: $%.2f\n", totalSales));
            report.append(String.format("📈 Average Order Value: $%.2f\n", 
                deliveredOrders > 0 ? totalSales / deliveredOrders : 0));
            
            area.setText(report.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error generating sales report: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generatePopularProductsReport(JTextArea area) {
        try {
            String sql = "SELECT p.productName, SUM(oi.quantity) as totalSold " +
                        "FROM cheeseProduct p " +
                        "JOIN orderItem oi ON p.productId = oi.productId " +
                        "JOIN orders o ON oi.orderId = o.orderId " +
                        "WHERE o.status = 'Delivered' " +
                        "GROUP BY p.productId, p.productName " +
                        "ORDER BY totalSold DESC LIMIT 10";
            
            StringBuilder report = new StringBuilder("POPULAR PRODUCTS REPORT\n");
            report.append("========================\n\n");
            
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                int rank = 1;
                while (rs.next()) {
                    report.append(String.format("%d. %s - %d units sold\n", 
                        rank++, rs.getString("productName"), rs.getInt("totalSold")));
                }
                
                if (rank == 1) {
                    report.append("ℹ️ No sales data available yet.\n");
                }
            }
            
            area.setText(report.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error generating popular products report: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}