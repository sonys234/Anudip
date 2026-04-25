# BookVault 📚

BookVault is a Java-based console application designed to manage a digital bookstore. It features a complete shopping pipeline from browsing inventory to securely processing orders. The project is built using strict 3-Tier MVC architecture and connects to a MySQL database using JDBC.

## ⚙️ Tech Stack
* **Language:** Java (JDK 8+)
* **Database:** MySQL
* **Connectivity:** JDBC (Java Database Connectivity)
* **Architecture:** 3-Tier MVC (Model-View-Controller) & DTO (Data Transfer Object) Design Pattern

## ✨ Core Features
* **Role-Based Access:** Separate dashboards for Users (shopping) and Admins (inventory management).
* **Inventory Management:** Admins can add, update, or remove books. Users can view books using a Master-Detail console UI pattern.
* **Smart Shopping Cart:** Real-time stock validation prevents users from adding more books than are currently available in the database.
* **Transactional Checkout:** A strict order processing pipeline that generates unique Transaction IDs (UUID), deducts stock, clears the cart, and saves an itemized receipt.
* **Historical Tracking:** Uses junction tables (`order_items`) to maintain a permanent record of past orders and prices, even if the user clears their cart.

## 🗄️ Database Structure
The system uses a normalized relational database containing 6 core tables:
1. `Users` - Stores customer credentials and details.
2. `Books` - Stores inventory, pricing, and descriptions.
3. `Cart` - A temporary bridge table managing active shopping sessions.
4. `Orders` - Tracks final checkout totals and user associations.
5. `order_items` - A permanent bridge table acting as an itemized receipt.
6. `Payments` - Logs successful transaction UUIDs linked to specific orders.

## 🚀 How to Run Locally

### 1. Database Setup
1. Open your MySQL client (e.g., MySQL Workbench).
2. Import the provided `BookVault_Database.sql` file to create the tables and dummy data.
3. Verify that the 6 tables have been created successfully.

### 2. Application Setup
1. Clone this repository to your local machine.
2. Open the project in your preferred Java IDE (Eclipse, IntelliJ, etc.).
3. Navigate to `src/util/DbConnection.java` (or wherever your database connection class is located).
4. Update the database credentials to match your local MySQL setup:
   ```java
   String user = "root"; 
   String password = "your_mysql_password_here"; // Replace with your password

   
