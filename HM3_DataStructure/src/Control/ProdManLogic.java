package Control;

import Entity.Product;
import java.util.HashMap;
import java.util.Map;

public class ProdManLogic {
	
	private static ProdManLogic _instance; // Singleton instance
	private Map<Integer, Product> products; // inventory as map
	
    // Private constructor to prevent instantiation
    private ProdManLogic() {
    	this.products = new HashMap<>();
    }

    // Method to get the singleton instance of InvManLogic
    public static ProdManLogic getInstance() {
        if (_instance == null)
            _instance = new ProdManLogic();
        return _instance;
    }
    
    // Method to add a new product to the inventory
    public boolean addProduct(Integer productID, String productName, int quantity) {
        if (productID == null || productName == null || productName.trim().isEmpty() || quantity < 0) {
            return false;
        }
        Product newProduct = new Product(productName, quantity);
        products.put(productID, newProduct);
        return true;
    }
    
    // Method to delete a product from the inventory
    public boolean deleteProduct(int productID) {
    	Product product = products.get(productID);
    	if (product != null) { // if product exist
    		products.remove(productID);
    		return true;
    	}
    	return false;
    	
    }
    
    // Method to check the availability of a product in the inventory
    public boolean checkAvailability(int productID, int quantityToCheck) {
    	Product product = products.get(productID);
    	if (product != null && quantityToCheck > 0 && product.getQuantity() >= quantityToCheck) {
    		return true; // Product is available in the required quantity
    	}
    	return false; // Product is not available or not enough quantity
    }
    
    // Method to reduce the quantity of a product in the inventory
    public boolean reduceQuantity(int productID, int quantityToReduce) {
    	if (quantityToReduce <= 0) {
    		return false;
    	}
    	Product product = products.get(productID);
    	if (product != null) {       // if product exist
    		if (checkAvailability(product, quantityToReduce)){
    			product.reduceQuantity(quantityToReduce); 
    			return true;
    		}
    	    return false;
    	}
    	return false;
    	
    }
    
    // Overloading method to check the availability of a product in the inventory
    public boolean checkAvailability(Product product, int quantityToCheck) {
    	if (product.getQuantity() >= quantityToCheck) {  //checking if product not null in "reduceQuantity"
    		return true; // Product is available in the required quantity
    	}
    	return false; // Product is not available or not enough quantity
    }
    
    
    // Method to print the inventory in a nice format
    public void printInventory() {
        System.out.println("Inventory:");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-10s %-20s %-10s%n", "Product ID", "Product Name", "Quantity");
        System.out.println("-------------------------------------------------");
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            Integer productId = entry.getKey();
            Product product = entry.getValue();
            System.out.printf("%-10d %-20s %-10d%n", productId, product.getProductName(), product.getQuantity());
        }
        System.out.println("-------------------------------------------------");
    }
}
