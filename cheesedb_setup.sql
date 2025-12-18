CREATE DATABASE IF NOT EXISTS cheesedb;
USE cheesedb;

-- Customer table based on Customer.java model
CREATE TABLE customer (
    customerId INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    address TEXT,
    role VARCHAR(20) DEFAULT 'CUSTOMER'
);

-- Admin table for system administrators
CREATE TABLE admin (
    adminId INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    address TEXT
);

-- Supplier table based on Supplier.java model
CREATE TABLE supplier (
    supplierId INT AUTO_INCREMENT PRIMARY KEY,
    supplierName VARCHAR(100) NOT NULL,
    contactPerson VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    address TEXT
);

-- CheeseProduct table based on CheeseProduct.java model
CREATE TABLE cheeseProduct (
    productId INT AUTO_INCREMENT PRIMARY KEY,
    productName VARCHAR(100) NOT NULL,
    cheeseType VARCHAR(50),
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stockQuantity INT NOT NULL DEFAULT 0,
    supplierId INT,
    FOREIGN KEY (supplierId) REFERENCES supplier(supplierId)
);

-- Order table based on Order.java model
CREATE TABLE orders (
    orderId INT AUTO_INCREMENT PRIMARY KEY,
    customerId INT NOT NULL,
    orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    totalAmount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (customerId) REFERENCES customer(customerId)
);

-- OrderItem table based on OrderItem.java model
CREATE TABLE orderItem (
    orderItemId INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    unitPrice DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (orderId) REFERENCES orders(orderId),
    FOREIGN KEY (productId) REFERENCES cheeseProduct(productId)
);

-- Payment table based on Payment.java model
CREATE TABLE payment (
    paymentId INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    paymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paymentMethod VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (orderId) REFERENCES orders(orderId)
);

-- Sample suppliers
INSERT INTO supplier (supplierName, contactPerson, email, phone, address) VALUES
('Dairy Fresh Co.', 'John Smith', 'john@dairyfresh.com', '1234567890', '123 Dairy Lane'),
('Cheese Masters Ltd.', 'Mary Johnson', 'mary@cheesemasters.com', '0987654321', '456 Cheese Street');

-- Sample cheese products
INSERT INTO cheeseProduct (productName, cheeseType, description, price, stockQuantity, supplierId) VALUES
('Premium Cheddar', 'Cheddar', 'Aged cheddar cheese with rich flavor', 12.99, 50, 1),
('Fresh Mozzarella', 'Mozzarella', 'Soft and creamy mozzarella', 8.99, 30, 1),
('Aged Gouda', 'Gouda', 'Dutch aged gouda with caramel notes', 15.99, 25, 2),
('Creamy Brie', 'Brie', 'French soft cheese with white rind', 18.99, 20, 2);

-- Sample customers
INSERT INTO customer (firstName, lastName, email, password, phone, address, role) VALUES
('John', 'Doe', 'john@example.com', 'password123', '1234567890', '123 Main St', 'CUSTOMER');

-- System Administrator
INSERT INTO admin (firstName, lastName, email, password, phone, address) VALUES
('Admin', 'Ganza', 'admin@cheese.com', '123456', '1234567890', 'Admin Office');