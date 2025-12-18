CREATE DATABASE IF NOT EXISTS cheese_supply_db;
USE cheese_supply_db;

CREATE TABLE IF NOT EXISTS customer (
    customerId INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    address TEXT
);

CREATE TABLE IF NOT EXISTS suppliers (
    supplierId INT AUTO_INCREMENT PRIMARY KEY,
    supplierName VARCHAR(100) NOT NULL,
    contactPerson VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    address TEXT
);

CREATE TABLE IF NOT EXISTS cheeseProduct (
    productId INT AUTO_INCREMENT PRIMARY KEY,
    productName VARCHAR(100) NOT NULL,
    cheeseType VARCHAR(50),
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stockQuantity INT NOT NULL DEFAULT 0,
    supplierId INT,
    FOREIGN KEY (supplierId) REFERENCES suppliers(supplierId)
);

CREATE TABLE IF NOT EXISTS orders (
    orderId INT AUTO_INCREMENT PRIMARY KEY,
    customerId INT NOT NULL,
    orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    totalAmount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (customerId) REFERENCES customer(customerId)
);

CREATE TABLE IF NOT EXISTS orderItem (
    orderItemId INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL,
    unitPrice DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (orderId) REFERENCES orders(orderId),
    FOREIGN KEY (productId) REFERENCES cheeseProduct(productId)
);

CREATE TABLE IF NOT EXISTS payments (
    paymentId INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    paymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    paymentMethod VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending',
    FOREIGN KEY (orderId) REFERENCES orders(orderId)
);

INSERT INTO suppliers (supplierName, contactPerson, email, phone, address) VALUES
('Dairy Fresh Co.', 'John Smith', 'john@dairyfresh.com', '1234567890', '123 Dairy Lane'),
('Cheese Masters Ltd.', 'Mary Johnson', 'mary@cheesemasters.com', '0987654321', '456 Cheese Street');

INSERT INTO cheeseProduct (productName, cheeseType, description, price, stockQuantity, supplierId) VALUES
('Premium Cheddar', 'Cheddar', 'Aged cheddar cheese with rich flavor', 12.99, 50, 1),
('Fresh Mozzarella', 'Mozzarella', 'Soft and creamy mozzarella', 8.99, 30, 1),
('Aged Gouda', 'Gouda', 'Dutch aged gouda with caramel notes', 15.99, 25, 2),
('Creamy Brie', 'Brie', 'French soft cheese with white rind', 18.99, 20, 2);