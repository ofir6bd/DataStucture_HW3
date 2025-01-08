package Control;

import Entity.Product;
import Entity.Consts;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InvManLogic {
	
	private static InvManLogic _instance; // Singleton instance
	private Map<Integer, Product> inventory; // In-memory inventory map
	
    // Private constructor to prevent instantiation
    private InvManLogic() {
    	this.inventory = new HashMap<>();
    	loadInventoryFromDB();
    }

    // Method to get the singleton instance of InvManLogic
    public static InvManLogic getInstance() {
        if (_instance == null)
            _instance = new InvManLogic();
        return _instance;
    }
    
    // Method to load inventory from the database
    private void loadInventoryFromDB() {
        try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
             PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ALL_PROD)) {

        	try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    Product product = new Product(productName, quantity);
                    inventory.put(productID, product); // Add product to the inventory map
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a new product to the inventory
    public void addProduct(String productName, int quantity) {
    	// TODO: need to check who assigns the ID and if it already exists
    	Product newProduct = new Product(productName, quantity);
    	inventory.put(11,newProduct);
    }
    
    // Method to delete a product from the inventory
    public void deleteProduct(int productID) {
    	Product product = inventory.get(productID);
    	if (product != null) {
    		inventory.remove(productID);
    	}else {
    		System.out.println("Product does not exist");
    	}
    	
    }
    
    // Method to reduce the quantity of a product in the inventory
    public void reduceQuantity(int productID, int quantityToReduce) {
    	Product product = inventory.get(productID);
    	if (product != null) {
    	    product.reduceQuantity(quantityToReduce); 
    	} else {
    	    System.out.println("Product does not exist");
    	}
    }
    
    // Method to check the availability of a product in the inventory
    public boolean checkAvailability(int productID, int quantityToCheck) {
    	Product product = inventory.get(productID);
    	if (product != null && product.getQuantity() >= quantityToCheck) {
    		return true; // Product is available in the required quantity
    	}
    	return false; // Product is not available or not enught quantity
    }
    
    
    // Method to print the inventory in a nice format
    public void printInventory() {
        System.out.println("Inventory:");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-10s %-20s %-10s%n", "Product ID", "Product Name", "Quantity");
        System.out.println("-------------------------------------------------");
        for (Map.Entry<Integer, Product> entry : inventory.entrySet()) {
            Integer productId = entry.getKey();
            Product product = entry.getValue();
            System.out.printf("%-10d %-20s %-10d%n", productId, product.getProductName(), product.getQuantity());
        }
        System.out.println("-------------------------------------------------");
    }
   
//    public static void main(String[] args) {
//        // Create the inventory and order management system
//        InvManLogic inst = InvManLogic.getInstance();
//        
//        // Print the inventory
//        inst.printInventory();
//    }


}
