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
	
	private static InvManLogic _instance;
	private Map<Integer, Product> inventory;

    // Private constructor to prevent instantiation
    private InvManLogic() {
    	this.inventory = new HashMap<>();
    	loadInventoryFromDB();
    }

    // Method to get the singleton instance of CountryLogic
    public static InvManLogic getInstance() {
        if (_instance == null)
            _instance = new InvManLogic();
        return _instance;
    }
    
    private void loadInventoryFromDB() {
        try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
             PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_ALL_PROD)) {

        	try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    String productName = rs.getString("product_name");
                    int quantity = rs.getInt("quantity");
                    Product product = new Product(productId, productName, quantity);
                    inventory.put(productId, product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 // Method to print the inventory in a nice format
    public void printInventory() {
        System.out.println("Inventory:");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-10s %-20s %-10s%n", "Product ID", "Product Name", "Quantity");
        System.out.println("-------------------------------------------------");
        for (Product product : inventory.values()) {
            System.out.printf("%-10d %-20s %-10d%n", product.getProductID(), product.getProductName(), product.getQuan());
        }
        System.out.println("-------------------------------------------------");
    }
   
    public static void main(String[] args) {
        // יצירת המחסן ומערכת ניהול ההזמנות
        InvManLogic inst = InvManLogic.getInstance();
        
        // הדפסת המלאי
        inst.printInventory();
    }


}
