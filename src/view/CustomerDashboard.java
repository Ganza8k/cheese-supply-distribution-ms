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
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CustomerDashboard extends JFrame {
    private Customer currentCustomer;
    private JTable productsTable, ordersTable, cartTable;
    private DefaultTableModel productsModel, ordersModel, cartModel;
    private JButton addToCartButton, placeOrderButton, cancelOrderButton, logoutButton;
    private CheeseProductDAO productDAO;
    private OrderDAO orderDAO;
    private List<OrderItem> cart;

    public CustomerDashboard(Customer customer) {
        this.currentCustomer = customer;
        this.productDAO = new CheeseProductDAO();
        this.orderDAO = new OrderDAO();
        this.cart = new ArrayList<>();
        initComponents();
        loadProducts();
        loadOrders();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Customer Dashboard - " + currentCustomer.getFirstName() + " " + currentCustomer.getLastName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0xFAF9F6));

        // Header Panel with gradient
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
        JLabel titleLabel = new JLabel("Welcome, " + currentCustomer.getFirstName() + "!", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(25, 0, 25, 0));
        headerPanel.add(titleLabel);

        // Main Panel with Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(0xFAF9F6));
        tabbedPane.setForeground(new Color(0x2D3748));

        // Products Tab
        JPanel productsPanel = createProductsPanel();
        tabbedPane.addTab("Browse Products", productsPanel);

        // Cart Tab
        JPanel cartPanel = createCartPanel();
        tabbedPane.addTab("Shopping Cart", cartPanel);

        // Orders Tab
        JPanel ordersPanel = createOrdersPanel();
        tabbedPane.addTab("My Orders", ordersPanel);

        // Logout Button
        logoutButton = createStyledButton("Logout", new Color(0x22543D), new Color(0x2F855A));
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

        // Products Table
        String[] columns = {"ID", "Product Name", "Description", "Price", "Stock"};
        productsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productsTable = new JTable(productsModel);
        styleTable(productsTable);

        JScrollPane scrollPane = new JScrollPane(productsTable);

        // Add to Cart Panel
        JPanel addPanel = new JPanel(new FlowLayout());
        addPanel.setBackground(new Color(0xFAF9F6));
        addPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        qtyLabel.setForeground(new Color(0x2D3748));
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantitySpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addToCartButton = createStyledButton("Add to Cart", new Color(0x2F855A), new Color(0x22543D));

        addPanel.add(qtyLabel);
        addPanel.add(quantitySpinner);
        addPanel.add(addToCartButton);

        addToCartButton.addActionListener(e -> {
            int selectedRow = productsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int productId = (Integer) productsModel.getValueAt(selectedRow, 0);
                String productName = (String) productsModel.getValueAt(selectedRow, 1);
                double price = (Double) productsModel.getValueAt(selectedRow, 3);
                int stock = (Integer) productsModel.getValueAt(selectedRow, 4);
                int quantity = (Integer) quantitySpinner.getValue();

                // Business validation: Cannot order out-of-stock products
                if (stock <= 0) {
                    JOptionPane.showMessageDialog(this, "This product is out of stock and cannot be ordered.", "Out of Stock", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Business validation: Prevent ordering more than available stock
                if (quantity > stock) {
                    JOptionPane.showMessageDialog(this, "Cannot order more than available stock. Available: " + stock, "Insufficient Stock", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Business validation: Minimum order quantity
                if (quantity < 1) {
                    JOptionPane.showMessageDialog(this, "Minimum order quantity is 1", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                OrderItem item = new OrderItem(0, productId, quantity, price);
                cart.add(item);
                updateCartTable();
                JOptionPane.showMessageDialog(this, productName + " (" + quantity + " units) added to cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a product to add to cart", "Selection Required", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(addPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xFAF9F6));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Cart Table
        String[] columns = {"Product ID", "Quantity", "Unit Price", "Subtotal"};
        cartModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartTable = new JTable(cartModel);
        styleTable(cartTable);

        JScrollPane scrollPane = new JScrollPane(cartTable);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(0xFAF9F6));
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        placeOrderButton = createStyledButton("Place Order", new Color(0x2F855A), new Color(0x22543D));
        JButton clearCartButton = createStyledButton("Clear Cart", new Color(0x22543D), new Color(0x2F855A));

        buttonPanel.add(placeOrderButton);
        buttonPanel.add(clearCartButton);

        placeOrderButton.addActionListener(e -> placeOrder());
        clearCartButton.addActionListener(e -> {
            cart.clear();
            updateCartTable();
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0xFAF9F6));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Orders Table
        String[] columns = {"Order ID", "Date", "Total Amount", "Status"};
        ordersModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ordersTable = new JTable(ordersModel);
        styleTable(ordersTable);

        JScrollPane scrollPane = new JScrollPane(ordersTable);

        // Cancel Order Button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(0xFAF9F6));
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        cancelOrderButton = createStyledButton("Cancel Order", new Color(0x22543D), new Color(0x2F855A));

        buttonPanel.add(cancelOrderButton);

        cancelOrderButton.addActionListener(e -> cancelOrder());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadProducts() {
        try {
            List<CheeseProduct> products = productDAO.getAllProducts();
            productsModel.setRowCount(0);
            for (CheeseProduct product : products) {
                Object[] row = {
                    product.getProductId(),
                    product.getProductName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStockQuantity()
                };
                productsModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadOrders() {
        try {
            List<Order> orders = orderDAO.getOrdersByCustomer(currentCustomer.getCustomerId());
            ordersModel.setRowCount(0);
            for (Order order : orders) {
                Object[] row = {
                    order.getOrderId(),
                    order.getOrderDate(),
                    order.getTotalAmount(),
                    order.getStatus()
                };
                ordersModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading orders: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCartTable() {
        cartModel.setRowCount(0);
        for (OrderItem item : cart) {
            Object[] row = {
                item.getProductId(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()
            };
            cartModel.addRow(row);
        }
    }

    private void placeOrder() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty. Please add items before placing an order.", "Empty Cart", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Validate stock availability before placing order
            for (OrderItem item : cart) {
                CheeseProduct product = productDAO.getProductById(item.getProductId());
                if (product == null) {
                    JOptionPane.showMessageDialog(this, "Product no longer available", "Product Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Business validation: Cannot place orders for out-of-stock products
                if (product.getStockQuantity() <= 0) {
                    JOptionPane.showMessageDialog(this, "Product '" + product.getProductName() + "' is out of stock", "Out of Stock", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Business validation: Prevent ordering more than available stock
                if (item.getQuantity() > product.getStockQuantity()) {
                    JOptionPane.showMessageDialog(this, "Insufficient stock for '" + product.getProductName() + "'. Available: " + product.getStockQuantity(), "Insufficient Stock", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            double total = cart.stream().mapToDouble(OrderItem::getSubtotal).sum();
            if (total <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid order total", "Order Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Payment selection
            String[] paymentMethods = {"Cash on Delivery", "Mobile Payment"};
            String selectedPayment = (String) JOptionPane.showInputDialog(this, 
                "Select payment method for order total: $" + String.format("%.2f", total), 
                "Payment Method", JOptionPane.QUESTION_MESSAGE, null, paymentMethods, paymentMethods[0]);
            
            if (selectedPayment == null) {
                return; // User cancelled
            }
            
            Order order = new Order(currentCustomer.getCustomerId(), new Date(), total, "Pending");
            
            int orderId = orderDAO.createOrder(order);
            if (orderId > 0) {
                for (OrderItem item : cart) {
                    item.setOrderId(orderId);
                    if (!orderDAO.addOrderItem(item)) {
                        JOptionPane.showMessageDialog(this, "Failed to add order item", "Order Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Update stock
                    CheeseProduct product = productDAO.getProductById(item.getProductId());
                    if (product != null) {
                        int newStock = product.getStockQuantity() - item.getQuantity();
                        productDAO.updateStock(item.getProductId(), newStock);
                    }
                }
                
                // Create payment record
                PaymentDAO paymentDAO = new PaymentDAO();
                Payment payment = new Payment(orderId, total, new Date(), selectedPayment, "Pending");
                paymentDAO.addPayment(payment);
                
                cart.clear();
                updateCartTable();
                loadProducts();
                loadOrders();
                JOptionPane.showMessageDialog(this, "Order placed successfully!\nOrder ID: " + orderId + "\nPayment Method: " + selectedPayment, "Order Confirmed", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create order. Please try again.", "Order Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error while placing order. Please try again later.\nError: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelOrder() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow >= 0) {
            int orderId = (Integer) ordersModel.getValueAt(selectedRow, 0);
            String status = (String) ordersModel.getValueAt(selectedRow, 3);
            
            // Business validation: Only pending orders can be cancelled by customers
            if (!"Pending".equals(status)) {
                JOptionPane.showMessageDialog(this, "Only pending orders can be cancelled. Current status: " + status, "Cannot Cancel Order", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel Order #" + orderId + "?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (orderDAO.updateOrderStatus(orderId, "Cancelled")) {
                        loadOrders();
                        JOptionPane.showMessageDialog(this, "Order #" + orderId + " cancelled successfully!", "Order Cancelled", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to cancel order. Please try again.", "Cancellation Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Database error while cancelling order. Please try again later.\nError: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to cancel", "Selection Required", JOptionPane.ERROR_MESSAGE);
        }
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
}