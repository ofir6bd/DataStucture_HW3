package Control;

import Entity.Product;
import java.util.HashMap;
import java.util.Map;

public class ProdManLogic {
	
	private static ProdManLogic _instance; // Singleton instance
	private Map<Integer, Product> products; // All products stored in Hashmap
	
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
	
        // Create a new product and add it to the inventory - products map
        Product newProduct = new Product(productName, quantity);
        products.put(productID, newProduct);
        return true;
    }
    
    // Method to delete a product from the inventory
    public boolean deleteProduct(int productID) {
    	Product product = products.get(productID);
    	if (product != null) { 		// If product exists
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
    private boolean checkAvailability(int productID, int quantityToCheck) {
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
                return false; // If one of the products not available return false on all map 
            }
        }
        return true; // All products are available in the required quantities
    }
    
    // Method to reduce the quantity of a specific product in the inventory
    private boolean reduceQuantity(int productID, int quantityToReduce) {
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
    
    // Method to increase the quantity of products in the inventory
    public boolean increaseQuantity(Map<Integer, Integer> productsInMap) {
        for (Map.Entry<Integer, Integer> entry : productsInMap.entrySet()) {
            int productID = entry.getKey();
            int quantityToIncrease = entry.getValue();
            if (!increaseQuantity(productID, quantityToIncrease)) {
                return false; 
            }
        }
        return true; 
    }
    
    // Method to increase the quantity of a specific product in the inventory
    private boolean increaseQuantity(int productID, int quantityToIncrease) {
    	if (quantityToIncrease <= 0) {
    		return false;
    	}
    	
    	Product product = products.get(productID);
    	if (product != null ) { // If product exists
    		product.increaseQuantity(quantityToIncrease); 
			return true;
    	}
    	return false;
    }
}
