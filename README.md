# Cheese Supply Management System

## Problem Statement
Managing cheese supply and distribution for retail businesses requires efficient inventory tracking, order management, and customer relationship management. This system addresses the need for automated cheese supply chain management.

## Features
- Customer registration and authentication
- Product browsing with stock availability
- Order placement with payment options
- Admin inventory management
- Supplier management
- Automated stock replenishment
- Sales reporting and analytics

## Design Patterns Used
- **DAO (Data Access Object)**: Separates data access logic from business logic
- **MVC (Model-View-Controller)**: Separates presentation, business logic, and data layers

## Technology Stack
- **Language**: Java 11
- **Database**: MySQL 8.0
- **GUI**: Java Swing
- **Build Tool**: NetBeans/Ant

## Setup Instructions

### Prerequisites
- Java 11 or higher
- MySQL 8.0
- NetBeans IDE (optional)

### Database Setup
1. Create MySQL database: `cheese_supply_db`
2. Run the SQL scripts to create tables
3. Update database credentials in `DatabaseConnection.java`

### Running the Application
```bash
# Compile and run
javac -cp "lib/*" src/view/LoginFrame.java
java -cp "src:lib/*" view.LoginFrame
```

### Docker Setup
```bash
# Build and run with Docker
docker-compose up --build
```

## Testing
Run the test suite:
```bash
java -ea DatabaseConnectionTest
```

## Version Control
Initialize Git repository:
```bash
git init
git add .
git commit -m "Initial commit"
git remote add origin <your-repository-url>
git push -u origin main
```

## Project Structure
```
src/
├── dao/           # Data Access Objects
├── model/         # Entity classes
├── view/          # GUI classes
└── DatabaseConnectionTest.java
```