package Control;

import Entity.Order;
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

    // Private constructor to prevent instantiation
    private OrderManLogic() {
    	this.orders = new HashMap<>();
    	loadOrdersFromDB();
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
   
    public static void main(String[] args) {
        // Create the order management system
        OrderManLogic inst = OrderManLogic.getInstance();
        
        // Print the orders
        inst.printOrders();
    }
}