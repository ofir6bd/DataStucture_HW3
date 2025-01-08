package Entity;

public class Product {

    private int product_id;
    private String product_name;
    private int quantity;

    // Constructor
    public Product(int product_id, String product_name, int quan) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quan;
    }

    // Getter for product_id
    public int getProductID() {
        return product_id;
    }

    // Setter for product_id
    public void setProductID(int product_id) {
        this.product_id = product_id;
    }

    // Getter for product_name
    public String getProductName() {
        return product_name;
    }

    // Setter for product_name
    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    // Getter for quan
    public int getQuan() {
        return quantity;
    }

    // Setter for quan
    public void setQuan(int quan) {
        this.quantity = quan;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + product_id +
                ", productName='" + product_name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
