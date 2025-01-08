package Control;

import Entity.Order;
import Entity.ProductsInOrder;
import Entity.Consts;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderManLogic {
	
	private static OrderManLogic _instance;
	private Map<Integer, Order> orders;
	private Map<Integer, Map<Integer, ProductsInOrder>> productsInOrders;

    // Private constructor to prevent instantiation
    private OrderManLogic() {
    	this.orders = new HashMap<>();
    	this.productsInOrders = new HashMap<>();
    	loadOrdersFromDB();
    	loadProductsInOrdersFromDB();
    }

    // Method to get the singleton instance of OrderManLogic
    public static OrderManLogic getInstance() {
        if (_instance == null)
            _instance = new OrderManLogic();
        return _instance;
    }
    
    private void loadOrdersFromDB() {
        try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
             PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ALL_ORDERS)) {

        	try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("orderID");
                    String destination = rs.getString("destination");
                    int priority = rs.getInt("priority");
                    int totalItems = rs.getInt("totalItems");
                    boolean allProdAvail = rs.getBoolean("allProdAvail");
                    boolean delivered = rs.getBoolean("delivered");
                    
                    Order order = new Order(destination, priority, totalItems, allProdAvail, delivered);
                    orders.put(orderId, order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to print the orders in a nice format
    public void printOrders() {
        System.out.println("Orders:");
        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-40s %-10s %-12s %-15s %-10s%n", "Order ID", "Destination", "Priority", "Total Items", "All Prod Avail", "Delivered");
        System.out.println("------------------------------------------------------------------------------------------------------------");
        for (Map.Entry<Integer, Order> entry : orders.entrySet()) {
            Integer orderId = entry.getKey();
            Order order = entry.getValue();
            System.out.printf("%-10d %-40s %-10d %-12d %-15b %-10b%n", orderId, order.getDestination(), order.getPriority(), order.getTotalItems(), order.isAllProdAvail(), order.isDelivered());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------");
    }
    
    private void loadProductsInOrdersFromDB() {
        try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
             PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ALL_PRODUCTS_IN_ORDER)) {

        	try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderID = rs.getInt("orderID");
                    int productID = rs.getInt("productID");
                    int quantity = rs.getInt("quantity");
                    
                    ProductsInOrder productsInOrder = new ProductsInOrder(orderID, productID, quantity);
                    
                    productsInOrders
                        .computeIfAbsent(orderID, k -> new HashMap<>())
                        .put(productID, productsInOrder);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method to print the products in orders in a nice format
    public void printProductsInOrders() {
        System.out.println("Products in Orders:");
        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-10s %-10s%n", "Order ID", "Product ID", "Quantity");
        System.out.println("------------------------------------------------------------------------------------------------------------");
        for (Map.Entry<Integer, Map<Integer, ProductsInOrder>> orderEntry : productsInOrders.entrySet()) {
            Integer orderId = orderEntry.getKey();
            for (Map.Entry<Integer, ProductsInOrder> productEntry : orderEntry.getValue().entrySet()) {
                ProductsInOrder productsInOrder = productEntry.getValue();
                System.out.printf("%-10d %-10d %-10d%n", productsInOrder.getOrderID(), productsInOrder.getProductID(), productsInOrder.getquantity());
            }
        }
        System.out.println("------------------------------------------------------------------------------------------------------------");
    }
    public static void main(String[] args) {
        // Create the order management system
        OrderManLogic inst = OrderManLogic.getInstance();
        
        // Print the orders
        inst.printOrders();
        
        // Print the products in orders
        inst.printProductsInOrders();
        
    }
}