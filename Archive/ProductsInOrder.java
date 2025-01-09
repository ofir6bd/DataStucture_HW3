package Entity;

public class ProductsInOrder {

    private int order_id;
    private int product_id;
    private int quantity;

    // Constructor
    public ProductsInOrder(int order_id, int product_id, int quantity) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    // Getter for order_id
    public int getOrderID() {
        return order_id;
    }

    // Setter for order_id
    public void setOrderID(int order_id) {
        this.order_id = order_id;
    }

    // Getter for product_id
    public int getProductID() {
        return product_id;
    }

    // Setter for product_id
    public void setProductID(int product_id) {
        this.product_id = product_id;
    }

    // Getter for quantity
    public int getquantity() {
        return quantity;
    }

    // Setter for quantity
    public void setquantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductsInOrder{" +
                "order_id=" + order_id +
                ", product_id=" + product_id +
                ", quantity=" + quantity +
                '}';
    }
}