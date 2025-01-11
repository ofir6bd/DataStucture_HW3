package Control;

import Entity.Order;
import java.util.HashMap;
import java.util.Map;

class QueNode {
    private int orderID;
    private Order order;
    private QueNode next;

    public QueNode(int orderID, Order order, QueNode next) {
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

    public QueNode getNext() {
        return next;
    }

    public void setNext(QueNode next) {
        this.next = next;
    }
}

public class OrdManLogic {

    private static OrdManLogic _instance;
    private Map<Integer, Order> orders;
    private QueNode[] prioQueStart;
    private QueNode[] prioQueEnd;
    private int orderID = 1;

    // Private constructor to prevent instantiation
    private OrdManLogic() {
        this.orders = new HashMap<>();
        this.prioQueStart = new QueNode[5];
        this.prioQueEnd = new QueNode[5];
    }

    // Method to get the singleton instance of OrderManLogic
    public static OrdManLogic getInstance() {
        if (_instance == null)
            _instance = new OrdManLogic();
        return _instance;
    }

    public boolean addOrder(String destination, int priority, int[] productsInOrder) {
        // Validate parameters in a single if statement
        if (destination == null || destination.trim().isEmpty() || priority < 1 || priority > 5 || productsInOrder.length % 2 != 0) {
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

        Order newOrder = new Order(destination, priority, totalItems, productsInOrderMap); // Pass the map to the constructor
        orders.put(orderID, newOrder); // Assuming orders is a collection of Order objects

        // Create a Queue Node for the order
        QueNode newNode = new QueNode(orderID, newOrder, null);

        // Add to prioQueue
        if (prioQueStart[priority - 1] == null) { // Initiate prioQueStart  
            prioQueStart[priority - 1] = newNode;
            prioQueEnd[priority - 1] = newNode;
        } else {
            QueNode endNode = prioQueEnd[priority - 1];
            endNode.setNext(newNode); // connect newNode to the end of the Que
            prioQueEnd[priority - 1] = newNode;
        }
        this.orderID += 1;
        return true; // Order added successfully
    }

    public boolean processNextOrder(int kOrders) {
        int k = 0;
        ProdManLogic prodMan = ProdManLogic.getInstance();

        for (int priority = 1; priority <= 5; priority++) {
            if (k >= kOrders) {
                return true;
            }
            QueNode preCurrentNode = prioQueStart[priority - 1];
            QueNode currentNode = prioQueStart[priority - 1];

            while (currentNode != null && k < kOrders) {
            	k++;
            	Order order = currentNode.getOrder();
                boolean available = prodMan.checkAvailability(order.getProductsInOrderMap());
                if (available) {
                    prodMan.reduceQuantity(order.getProductsInOrderMap());
                    order.setDelivered(true);
                    
                    if (preCurrentNode == currentNode) {
                    	prioQueStart[priority - 1] = currentNode.getNext();
                    	preCurrentNode = prioQueStart[priority - 1];
                    	currentNode = prioQueStart[priority - 1];
                    }else {
                    	System.out.println(currentNode.getNext());
                    	preCurrentNode.setNext(currentNode.getNext());
                    	currentNode =  currentNode.getNext();	
                    	if (currentNode == null) {
                    		prioQueEnd[priority - 1] = preCurrentNode.getNext();	
                    	}
                    }
                } else {
                	preCurrentNode = currentNode;
                	currentNode = currentNode.getNext();
                }
            }
        }
        return false;
    }

    // Method to print the orders in a nice format
    public void printOrders() {
        System.out.println("Orders:");
        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-40s %-10s %-12s %-10s%n", "Order ID", "Destination", "Priority", "Total Items", "Delivered");
        System.out.println("------------------------------------------------------------------------------------------------------------");
        for (Map.Entry<Integer, Order> entry : orders.entrySet()) {
            Integer orderId = entry.getKey();
            Order order = entry.getValue();
            System.out.printf("%-10d %-40s %-10d %-12d %-10b%n", orderId, order.getDestination(), order.getPriority(), order.getTotalItems(), order.isDelivered());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------");
    }
    
    public void printAllOrdersFromQueue() {
        System.out.println("Orders from Priority Queues:");
        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-40s %-10s %-12s %-15s %-10s%n", "Order ID", "Destination", "Priority", "Total Items", "All Prod Avail", "Delivered");
        System.out.println("------------------------------------------------------------------------------------------------------------");
        for (int priority = 1; priority <= 5; priority++) {
            QueNode currentNode = prioQueStart[priority - 1];
            while (currentNode != null) {
                Order order = currentNode.getOrder();
                System.out.printf("%-10d %-40s %-10d %-12d %-10b%n", currentNode.getOrderID(), order.getDestination(), order.getPriority(), order.getTotalItems(), order.isDelivered());
                currentNode = currentNode.getNext();
            }
        }
        System.out.println("------------------------------------------------------------------------------------------------------------");
    }
    
}
