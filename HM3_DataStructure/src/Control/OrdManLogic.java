package Control;

import Entity.Order;
import java.util.HashMap;
import java.util.Map;

public class OrdManLogic {
	
	private static OrdManLogic _instance;
	private Map<Integer, Order> orders;
	private int orderNum = 1; 
	
    // Private constructor to prevent instantiation
    private OrdManLogic() {
    	this.orders = new HashMap<>();
    }

    // Method to get the singleton instance of OrderManLogic
    public static OrdManLogic getInstance() {
        if (_instance == null)
            _instance = new OrdManLogic();
        return _instance;
    }
    
    public boolean addOrder(String destination, int priority,int[] productsInOrder) {
        // Validate parameters in a single if statement
        if (destination == null || destination.trim().isEmpty() || priority < 0 || priority > 5 || productsInOrder.length %2 != 0) {
            return false; // Invalid parameters
        }

        // Create a new map for products in the order
        Map<Integer, Integer> productsInOrderMap = new HashMap<>();
        int i = 0;
        int totalItems = 0;
        boolean allProdAvail = true;
        ProdManLogic prodMan = ProdManLogic.getInstance();
        
        while ( i < productsInOrder.length ) {
        	int productID = productsInOrder[i];
        	int quantity = productsInOrder[i + 1];
        	totalItems += quantity;
            productsInOrderMap.put(productID, quantity);
            if (!prodMan.checkAvailability(productID, quantity)) {
            	allProdAvail = false;
            }
            i = i + 2;
        }
        Order newOrder = new Order(destination, priority, totalItems, allProdAvail, false);
        orders.put(orderNum,newOrder); // Assuming orders is a collection of Order objects
        this.orderNum += 1;
        return true; // Order added successfully
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
    
//    // Method to print the products in orders in a nice format
//    public void printProductsInOrders() {
//        System.out.println("Products in Orders:");
//        System.out.println("------------------------------------------------------------------------------------------------------------");
//        System.out.printf("%-10s %-10s %-10s%n", "Order ID", "Product ID", "Quantity");
//        System.out.println("------------------------------------------------------------------------------------------------------------");
//        for (Map.Entry<Integer, Map<Integer, ProductsInOrder>> orderEntry : productsInOrders.entrySet()) {
//            Integer orderId = orderEntry.getKey();
//            for (Map.Entry<Integer, ProductsInOrder> productEntry : orderEntry.getValue().entrySet()) {
//                ProductsInOrder productsInOrder = productEntry.getValue();
//                System.out.printf("%-10d %-10d %-10d%n", productsInOrder.getOrderID(), productsInOrder.getProductID(), productsInOrder.getquantity());
//            }
//        }
//        System.out.println("------------------------------------------------------------------------------------------------------------");
//    }
}
