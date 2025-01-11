package Control;

import Entity.Product;
import java.util.HashMap;
import java.util.Map;

public class ProdManLogic {
	
	private static ProdManLogic _instance; // Singleton instance
	private Map<Integer, Product> products; // Inventory as a map
	
    // Private constructor to prevent instantiation
    private ProdManLogic() {
    	this.products = new HashMap<>();
    }

    // Method to get the singleton instance of ProdManLogic
    public static ProdManLogic getInstance() {
        if (_instance == null)
            _instance = new ProdManLogic();
        return _instance;
    }
    
    // Method to get the products map
    public Map<Integer, Product> getProducts() {
        return products;
    }
    
    // Method to add a new product to the inventory
    public boolean addProduct(Integer productID, String productName, int quantity) {
        // Validate input parameters
		if (productID == null || productName == null || productName.trim().isEmpty() || quantity < 0) {
	            return false;
	        }
		if(products.containsKey(productID)) {
			return false;
		}
	
        // Create a new product and add it to the inventory
        Product newProduct = new Product(productName, quantity);
        products.put(productID, newProduct);
        return true;
    }
    
    // Method to delete a product from the inventory
    public boolean deleteProduct(int productID) {
    	Product product = products.get(productID);
    	if (product != null) { // If product exists
    		products.remove(productID);
    		return true;
    	}
    	return false;
    }
    
    // Method to check the availability of products in the inventory
    public boolean checkAvailability(Map<Integer, Integer> productsInOrderMap) {
        for (Map.Entry<Integer, Integer> entry : productsInOrderMap.entrySet()) {
            int productID = entry.getKey();
            int quantityToCheck = entry.getValue();
            if (!checkAvailability(productID, quantityToCheck)) {
                return false; // If any product is not available in the required quantity, return false
            }
        }
        return true; // All products are available in the required quantities
    }
    
    // Method to check the availability of a specific product in the inventory
    public boolean checkAvailability(int productID, int quantityToCheck) {
    	Product product = products.get(productID);
    	if (product != null && quantityToCheck > 0 && product.getQuantity() >= quantityToCheck) {
    		return true; // Product is available in the required quantity
    	}
    	return false; // Product is not available or not enough quantity
    }
    
    // Method to reduce the quantity of products in the inventory
    public boolean reduceQuantity(Map<Integer, Integer> productsInOrderMap) {
        for (Map.Entry<Integer, Integer> entry : productsInOrderMap.entrySet()) {
            int productID = entry.getKey();
            int quantityToReduce = entry.getValue();
            if (!reduceQuantity(productID, quantityToReduce)) {
                return false; 
            }
        }
        return true; // All products are available in the required quantities
    }
    
    // Method to reduce the quantity of a specific product in the inventory
    public boolean reduceQuantity(int productID, int quantityToReduce) {
    	
    	if (quantityToReduce <= 0) {
    		return false;
    	}
    	
    	Product product = products.get(productID);
    	if (product != null && product.getQuantity() >= quantityToReduce ) { // If product exists
    		product.reduceQuantity(quantityToReduce); 
			return true;
    	}
    	return false;
    }
    
    // Overloaded method to check the availability of a product in the inventory
    public boolean checkAvailability(Product product, int quantityToCheck) {
    	if (product.getQuantity() >= quantityToCheck) { // Checking if product is not null in "reduceQuantity"
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
