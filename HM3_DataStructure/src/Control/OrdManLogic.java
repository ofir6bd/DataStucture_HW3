package Control;

import Entity.Order;
import java.util.HashMap;
import java.util.Map;

class Node {
	private int orderID;
    private Order order;
    private Node next;

    public Node(int orderID, Order order, Node next) {
    	this.orderID = orderID;
        this.order = order;
        this.next = next;
    }
    
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

public class OrdManLogic {

    private static OrdManLogic _instance;
    private Map<Integer, Order> orders;
    private Map<Integer, Node> prioQueStart;
    private Map<Integer, Node> prioQueEnd;
    private int orderID = 1;

    // Private constructor to prevent instantiation
    private OrdManLogic() {
        this.orders = new HashMap<>();
        this.prioQueStart = new HashMap<>();
        this.prioQueEnd = new HashMap<>();
    }

    // Method to get the singleton instance of OrderManLogic
    public static OrdManLogic getInstance() {
        if (_instance == null)
            _instance = new OrdManLogic();
        return _instance;
    }

    public boolean addOrder(String destination, int priority, int[] productsInOrder) {
        // Validate parameters in a single if statement
        if (destination == null || destination.trim().isEmpty() || priority < 0 || priority > 5 || productsInOrder.length % 2 != 0) {
            return false; // Invalid parameters
        }

        // Create a new map for products in the order
        Map<Integer, Integer> productsInOrderMap = new HashMap<>();
        int i = 0;
        int totalItems = 0;
        while (i < productsInOrder.length) {
            int productID = productsInOrder[i];
            int quantity = productsInOrder[i + 1];
            totalItems += quantity;
            productsInOrderMap.put(productID, quantity);
            i = i + 2;
        }

        Order newOrder = new Order(destination, priority, totalItems);
        orders.put(orderID, newOrder); // Assuming orders is a collection of Order objects

        // Create a new Node for the order
        Node newNode = new Node(orderID,newOrder, null);

       // Add to prioQueue
        if (!prioQueStart.containsKey(priority)) {// Initiate both 
        	prioQueStart.put(priority, newNode);
        	prioQueEnd.put(priority, newNode);
        } else {
        	Node currentEndNode = prioQueEnd.get(priority);
            currentEndNode.setNext(newNode);
        	prioQueEnd.put(priority, newNode); //replace the end node pointer
        }
        
        this.orderID += 1;
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
    
    public void printAllOrdersFromQueue() {
        System.out.println("Orders from Priority Queues:");
        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.printf("Order ID");
        System.out.println("------------------------------------------------------------------------------------------------------------");
        for (int priority = 0; priority <= 5; priority++) {
            Node currentNode = prioQueStart.get(priority);
            while (currentNode != null) {
                System.out.println(currentNode.getOrderID());
                currentNode = currentNode.getNext();
            }
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
