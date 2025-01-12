package Control;

import Entity.Order;

import java.util.HashMap;
import java.util.Map;

// Class representing a node in the priority queue
class QueNode {
    private int orderID;
    private Order order;
    private QueNode next;

    // Constructor to initialize QueNode with orderID, order, and next node
    public QueNode(int orderID, Order order, QueNode next) {
        this.orderID = orderID;
        this.order = order;
        this.next = next;
    }

    // Getter for orderID
    public int getOrderID() {
        return orderID;
    }

    // Setter for orderID
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    // Getter for order
    public Order getOrder() {
        return order;
    }

    // Setter for order
    public void setOrder(Order order) {
        this.order = order;
    }

    // Getter for next node
    public QueNode getNext() {
        return next;
    }

    // Setter for next node
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
    
    // Getter for prioQueStart
    public QueNode[] getPrioQueStart() {
        return prioQueStart;
    }

    // Getter for the orders map
    public Map<Integer, Order> getOrders() {
        return orders;
    }
    
    // Method to add an order
    public boolean addOrder(String destination, int priority, int[] productsInOrder) {
        // Validate parameters in a single if statement
        if (destination == null || destination.trim().isEmpty() || priority < 1 || priority > 5 || productsInOrder.length % 2 != 0) {
            return false; // Invalid parameters
        }

        // Create a new map for products in the order
        Map<Integer, Integer> productsInOrderMap = new HashMap<>();
        int i = 0;
        int totalItems = 0; //count all products in order
        while (i < productsInOrder.length) {
            int productID = productsInOrder[i];
            int quantity = productsInOrder[i + 1];
            totalItems += quantity;
            productsInOrderMap.put(productID, quantity);
            i = i + 2; // jump 2 steps to the next product
        }

        // Create a new order and add it to the orders map
        Order newOrder = new Order(destination, priority, totalItems, productsInOrderMap);
        orders.put(orderID, newOrder);

        // Create a Queue Node for the order
        QueNode newNode = new QueNode(orderID, newOrder, null);

        // Add to prioQueue
        if (prioQueStart[priority - 1] == null) { // Initiate prioQueStart  
            prioQueStart[priority - 1] = newNode;
            prioQueEnd[priority - 1] = newNode;
        } else {
            QueNode endNode = prioQueEnd[priority - 1];
            endNode.setNext(newNode); // Connect newNode to the end of the queue
            prioQueEnd[priority - 1] = newNode;
        }
        this.orderID += 1;
        return true; // Order added successfully
    }

    // Method to process the next order
    public boolean processNextOrder() {
        return processNextOrder(1);
    }
    
    // Method to process the next k orders
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
                if (available) {   // all products available
                	System.out.println("OrderID: " + currentNode.getOrderID() + " - Processed - removed from Queue");
                    prodMan.reduceQuantity(order.getProductsInOrderMap());
                    order.setDelivered(true);
                    
                    if (preCurrentNode == currentNode) { // If it's on the first node of the linked list
                        prioQueStart[priority - 1] = currentNode.getNext();
                        preCurrentNode = prioQueStart[priority - 1];
                        currentNode = prioQueStart[priority - 1];
                    } else {         // Not on the first Node in the specific priority
                        preCurrentNode.setNext(currentNode.getNext());
                        currentNode = currentNode.getNext();    
                        if (currentNode == null) { // reached to the end of the linked list
                            prioQueEnd[priority - 1] = preCurrentNode.getNext();    
                        }
                    }
                } else { // Not all the products available - not delivered
                	System.out.println("OrderID: " + currentNode.getOrderID() + " - Processed not all products available - still in Queue");
                    preCurrentNode = currentNode;
                    currentNode = currentNode.getNext();
                }
            }
        }
        return false;
    }
}
