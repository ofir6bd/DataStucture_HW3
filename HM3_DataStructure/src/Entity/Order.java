package Entity;

public class Order {

    private int order_id;
    private String destination;
    private int priority;
    private int totalItems;
    private boolean allProdAvail;
    private boolean delivered;
    

    // Constructor
    public Order(int order_id, String destination, int priority, boolean allProdAvail, boolean delivered, int totalItems) {
        this.order_id = order_id;
        this.destination = destination;
        this.priority = priority;
        this.totalItems = totalItems;
        this.allProdAvail = allProdAvail;
        this.delivered = delivered;
        
    }

    // Getter for order_id
    public int getOrderID() {
        return order_id;
    }

    // Getter for destination
    public String getDestination() {
        return destination;
    }

    // Setter for destination
    public void setDestination(String destination) {
        this.destination = destination;
    }

    // Getter for priority
    public int getPriority() {
        return priority;
    }

    // Setter for priority
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    // Getter for totalItems
    public int getTotalItems() {
        return totalItems;
    }

    // Setter for totalItems
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
    
    // Getter for allProdAvail
    public boolean isAllProdAvail() {
        return allProdAvail;
    }

    // Setter for allProdAvail
    public void setAllProdAvail(boolean allProdAvail) {
        this.allProdAvail = allProdAvail;
    }

    // Getter for delivered
    public boolean isDelivered() {
        return delivered;
    }

    // Setter for delivered
    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "order_id=" + order_id +
                ", destination='" + destination + '\'' +
                ", priority=" + priority +
                ", totalItems=" + totalItems +
                ", allProdAvail=" + allProdAvail +
                ", delivered=" + delivered +
                '}';
    }
}