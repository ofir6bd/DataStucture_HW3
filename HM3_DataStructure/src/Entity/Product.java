package Entity;

public class Product {

    // productID is not part of the instance; it will be the key in the hashmap
    private String product_name;
    private int quantity;

    // Constructor to initialize Product object with product name and quantity
    public Product(String product_name, int quan) {
        this.product_name = product_name;
        this.quantity = quan;
    }

    // Getter method for product_name
    public String getProductName() {
        return product_name;
    }

    // Setter method for product_name
    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    // Getter method for quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter method to reduce the quantity by a specified amount
    public void reduceQuantity(int quantityToReduce) {
        this.quantity = this.quantity - quantityToReduce;
    }
    
    // Setter method to reduce the quantity by a specified amount
    public void increaseQuantity(int quantityToReduce) {
        this.quantity = this.quantity + quantityToReduce;
    }
    

//    // Overriding the toString method to provide a string representation of the Product object
//    @Override
//    public String toString() {
//        return "Product{" +
//                "productName='" + product_name + '\'' +
//                ", quantity=" + quantity +
//                '}';
//    }
}
