package Entity;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TempInsertData {

    public static void main(String[] args) {
        System.out.println("Orders Management System is up");

        // Construct the relative path to the database file
        String relativePath = Paths.get("src/Entity/OrdersProductsDB.accdb").toString();
        String databaseURL = "jdbc:ucanaccess://" + relativePath;

        // Check if the database file exists
        if (!Files.exists(Paths.get(relativePath))) {
            System.err.println("Database file does not exist: " + relativePath);
            return;
        }

        try {
            Connection connection = DriverManager.getConnection(databaseURL);
            System.out.println("Connected to MS Access DB");

            Statement statement = connection.createStatement();
            // Delete all content from each table
            String[] deleteStatements = {
                "DELETE FROM OrderProducts",
                "DELETE FROM Orders",
                "DELETE FROM Products"
            };

            for (String sql : deleteStatements) {
                statement.executeUpdate(sql);
            }

            System.out.println("All table contents have been deleted.");

            // Insert into Products
            String[] productStatements = {
                "INSERT INTO Products (product_id, product_name, quan) VALUES (1, 'Table', 2)",
                "INSERT INTO Products (product_id, product_name, quan) VALUES (2, 'Chair', 8)",
                "INSERT INTO Products (product_id, product_name, quan) VALUES (3, 'Picture', 2)",
                "INSERT INTO Products (product_id, product_name, quan) VALUES (4, 'Lamp', 5)",
                "INSERT INTO Products (product_id, product_name, quan) VALUES (5, 'Sofa', 3)",
                "INSERT INTO Products (product_id, product_name, quan) VALUES (6, 'Desk', 4)",
                "INSERT INTO Products (product_id, product_name, quan) VALUES (7, 'Bookshelf', 6)",
                "INSERT INTO Products (product_id, product_name, quan) VALUES (8, 'Bed', 1)",
                "INSERT INTO Products (product_id, product_name, quan) VALUES (9, 'Dresser', 2)",
                "INSERT INTO Products (product_id, product_name, quan) VALUES (10, 'Nightstand', 7)"
            };

            for (String sql : productStatements) {
                statement.executeUpdate(sql);
            }

            // Insert into Orders and retrieve generated keys
            String[] orderStatements = {
                "INSERT INTO Orders (destination, priority, totalItems, allProdAvail, delivered) VALUES ('Israel, Tel Aviv, Rothschild Blvd 1', 1, 3, True, False)",
                "INSERT INTO Orders (destination, priority, totalItems, allProdAvail, delivered) VALUES ('Israel, Jerusalem, Jaffa St 10', 2, 5, True, True)",
                "INSERT INTO Orders (destination, priority, totalItems, allProdAvail, delivered) VALUES ('Israel, Haifa, Herzl St 5', 3, 2, False, False)",
                "INSERT INTO Orders (destination, priority, totalItems, allProdAvail, delivered) VALUES ('Israel, Beersheba, Ben Gurion Blvd 20', 4, 4, True, True)",
                "INSERT INTO Orders (destination, priority, totalItems, allProdAvail, delivered) VALUES ('Israel, Eilat, Derech Yotam 15', 5, 6, False, False)",
                "INSERT INTO Orders (destination, priority, totalItems, allProdAvail, delivered) VALUES ('Israel, Netanya, Herzl St 30', 6, 1, True, True)",
                "INSERT INTO Orders (destination, priority, totalItems, allProdAvail, delivered) VALUES ('Israel, Ashdod, HaNassi Blvd 25', 7, 3, False, False)",
                "INSERT INTO Orders (destination, priority, totalItems, allProdAvail, delivered) VALUES ('Israel, Rishon LeZion, Rothschild St 40', 8, 2, True, True)",
                "INSERT INTO Orders (destination, priority, totalItems, allProdAvail, delivered) VALUES ('Israel, Petah Tikva, Jabotinsky St 50', 9, 5, False, False)",
                "INSERT INTO Orders (destination, priority, totalItems, allProdAvail, delivered) VALUES ('Israel, Holon, Sokolov St 60', 10, 4, True, True)"
            };

            List<Integer> orderIds = new ArrayList<>();
            for (String sql : orderStatements) {
                statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    orderIds.add(rs.getInt(1));
                }
            }

            // Insert into OrderProducts using the generated order IDs
            String[] orderProductStatements = {
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(0) + ", 1, 1)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(0) + ", 2, 2)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(1) + ", 2, 3)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(1) + ", 3, 2)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(2) + ", 1, 1)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(2) + ", 3, 1)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(3) + ", 4, 2)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(3) + ", 5, 1)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(4) + ", 6, 3)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(4) + ", 7, 2)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(5) + ", 8, 1)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(5) + ", 9, 2)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(6) + ", 10, 1)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(6) + ", 1, 2)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(7) + ", 2, 3)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(7) + ", 3, 1)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(8) + ", 4, 2)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(8) + ", 5, 1)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(9) + ", 6, 3)",
                "INSERT INTO OrderProducts (orderID, productID, quan) VALUES (" + orderIds.get(9) + ", 7, 2)"
            };

            for (String sql : orderProductStatements) {
                statement.executeUpdate(sql);
            }

            connection.close();
            System.out.println("New Records have been inserted");

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}