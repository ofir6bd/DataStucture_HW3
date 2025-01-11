package Entity;

import java.util.Map;

public class Order {
    // orderID is not part of the instance; it will be the key in the hashmap
    private String destination;
    private int priority;
    private int totalItems;
    private boolean delivered = false;
    private Map<Integer, Integer> productsInOrderMap; // Map to store product IDs and their quantities in the order

    // Constructor to initialize Order object with destination, priority, total items, and products in order map
    public Order(String destination, int priority, int totalItems, Map<Integer, Integer> productsInOrderMap) {
        this.destination = destination;
        this.priority = priority;
        this.totalItems = totalItems;
        this.productsInOrderMap = productsInOrderMap;
    }

    // Getter method for destination
    public String getDestination() {
        return destination;
    }

    // Setter method for destination
    public void setDestination(String destination) {
        this.destination = destination;
    }

    // Getter method for priority
    public int getPriority() {
        return priority;
    }

    // Setter method for priority
    public void setPriority(int priority) {
        this.priority = priority;
    }

    // Getter method for totalItems
    public int getTotalItems() {
        return totalItems;
    }

    // Setter method for totalItems
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    // Getter method for delivered status
    public boolean isDelivered() {
        return delivered;
    }

    // Setter method for delivered status
    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    // Getter method for productsInOrderMap
    public Map<Integer, Integer> getProductsInOrderMap() {
        return productsInOrderMap;
    }

    // Setter method for productsInOrderMap
    public void setProductsInOrderMap(Map<Integer, Integer> productsInOrderMap) {
        this.productsInOrderMap = productsInOrderMap;
    }
}
