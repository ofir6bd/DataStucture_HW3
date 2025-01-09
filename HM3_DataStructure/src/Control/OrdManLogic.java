package Control;

import Entity.Order;
import Entity.ProductsInOrder;
import java.util.HashMap;
import java.util.Map;


public class OrdManLogic {
	
	private static OrdManLogic _instance;
	private Map<Integer, Order> orders;
	private Map<Integer, Map<Integer, ProductsInOrder>> productsInOrders;

    // Private constructor to prevent instantiation
    private OrdManLogic() {
    	this.orders = new HashMap<>();
    	this.productsInOrders = new HashMap<>();
    }

    // Method to get the singleton instance of OrderManLogic
    public static OrdManLogic getInstance() {
        if (_instance == null)
            _instance = new OrdManLogic();
        return _instance;
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
    
    public void addOrder(String destination, int priority, int totalItems, boolean allProdAvail, boolean delivered) {
    	
    	Order newOrder = new Order(destination, priority, totalItems, allProdAvail, delivered);
//    	TODO: how to get the orderID
    	orders.put(200, newOrder);
    	System.out.println("done");
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
}