package Entity;

public class Order {

	//orderID is not part of the instance it will be the key in the hashmap
    private String destination;
    private int priority;
    private int totalItems;
    private boolean allProdAvail = false;
    private boolean delivered = false;
    

    // Constructor
    public Order(String destination, int priority, int totalItems) {
        this.destination = destination;
        this.priority = priority;
        this.totalItems = totalItems;
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
                "destination='" + destination + '\'' +
                ", priority=" + priority +
                ", totalItems=" + totalItems +
                ", allProdAvail=" + allProdAvail +
                ", delivered=" + delivered +
                '}';
    }
}