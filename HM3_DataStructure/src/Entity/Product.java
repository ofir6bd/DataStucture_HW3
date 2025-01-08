package Entity;

public class Product {

	//productID is not part of the instance it will be the key in the hashmap
    private String product_name;
    private int quantity;

    // Constructor
    public Product(String product_name, int quan) {
        this.product_name = product_name;
        this.quantity = quan;
    }

    // Getter for product_name
    public String getProductName() {
        return product_name;
    }

    // Setter for product_name
    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    // Getter for quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter for quantity
    public void reduceQuantity(int quantityToReduce) {
        this.quantity = this.quantity - quantityToReduce;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + product_name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
