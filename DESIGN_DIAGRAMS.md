# Cheese Supply Management System - Design Diagrams

## 1. Activity Diagram
```
[Start] → [User Registration] → [Role Selection (Admin/Customer)]
    ↓
[Login Authentication] → [Role Verification]
    ↓
[Admin Role] → [Admin Dashboard] → [Manage Products/Suppliers/Orders]
    ↓
[Customer Role] → [Customer Dashboard] → [Browse Products/Place Orders]
    ↓
[Database Operations] → [End]
```

## 2. Data Flow Diagram
```
User Input → Authentication Layer → Business Logic Layer → Data Access Layer → Database
    ↑                                        ↓
UI Layer ← Presentation Layer ← Response Processing ← Database Results
```

## 3. Sequence Diagram - User Login
```
User → LoginFrame → CustomerDAO → Database
User: Enter credentials
LoginFrame: Validate input
CustomerDAO: Authenticate user
Database: Return user data
CustomerDAO: Return user object
LoginFrame: Navigate to dashboard
```

## 4. Class Diagram Structure
```
Model Classes: Customer, Admin, CheeseProduct, Order, OrderItem, Payment, Supplier
DAO Classes: CustomerDAO, CheeseProductDAO, OrderDAO, PaymentDAO, SupplierDAO
View Classes: LoginFrame, RegisterFrame, AdminDashboard, CustomerDashboard
Utility: DatabaseConnection
```