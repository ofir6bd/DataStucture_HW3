package Control;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // Get the singleton instance of ProdManLogic
        ProdManLogic prodMan = ProdManLogic.getInstance();
        
        // Add 10 products to the inventory
        prodMan.addProduct(1, "Table", 64);
        prodMan.addProduct(2, "Chair", 40);
        prodMan.addProduct(3, "Picture", 20);
        prodMan.addProduct(4, "Lamp", 55);
        prodMan.addProduct(5, "Sofa", 32);
        prodMan.addProduct(6, "Desk", 41);
        prodMan.addProduct(7, "Bookshelf", 64);
        prodMan.addProduct(8, "Bed", 13);
        prodMan.addProduct(9, "Dresser", 22);
        prodMan.addProduct(10, "Nightstand", 74);
        
        ////// Orders //////
        // Get the singleton instance of OrdManLogic
        OrdManLogic ordMan = OrdManLogic.getInstance();
        
        // Add 10 orders
        ordMan.addOrder("Israel, Tel Aviv, Rothschild Blvd 1", 1, new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        ordMan.addOrder("Israel, Rishon LeZion, Rabin St 2", 2, new int[]{1, 2, 3, 4, 5, 6, 7, 80}); 
        ordMan.addOrder("Israel, Haifa, Herzl St 3", 3, new int[]{1, 2, 3, 4, 5, 6});
        ordMan.addOrder("Israel, Eilat, Ben Gurion St 4", 4, new int[]{1, 2, 3, 4, 66, 5, 6, 7, 8, 3}); 
        ordMan.addOrder("Israel, Jerusalem, King George St 5", 5, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        ordMan.addOrder("Israel, Beersheba, Weizmann St 6", 1, new int[]{1, 2, 3, 4, 5, 6, 7, 100}); 
        ordMan.addOrder("Israel, Netanya, Dizengoff St 7", 1, new int[]{2, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        ordMan.addOrder("Israel, Ashdod, Begin St 8", 2, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1}); 
        ordMan.addOrder("Israel, Holon, Jabotinsky St 9", 3, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 2, 14});
        ordMan.addOrder("Israel, Bat-Yam, Jabotinsky St 10", 1, new int[]{1, 2, 2, 4, 3, 6, 4, 8, 5, 10, 6, 12, 7, 2});
        
        // Insert 5 more products
        prodMan.addProduct(11, "Wardrobe", 30);
        prodMan.addProduct(12, "Stool", 30);
        prodMan.addProduct(13, "Mirror", 30);
        prodMan.addProduct(14, "TV Stand", 1);
        prodMan.addProduct(15, "Ottoman", 1);
        
        System.out.println("\n----------------------------------------------------------------------------------------------");
        System.out.println("--------------------Process the top 3 prioritized orders (per capability)---------------------");
        System.out.println("----------------------------------------------------------------------------------------------");
        // Process the next 3 orders
        ordMan.processNextOrder(3);
        
        System.out.println("\n----------------------------------------------------------------------------------------------");
        System.out.println("---------------------------------------------Reports------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println("");
        
        // Get the instance of Reports
        Reports repMan = new Reports();
        
	    // Generate and print the total orders report
	    repMan.totalOrdersReport();
	    
        // Generate and print the inventory report
        repMan.getInventoryReport();
        
        // Generate and print the report of the k biggest orders
        repMan.getKBiggestOrders(3);
        
        
        
        
        System.out.println("\n\n\n\n\n\n\n\n----------------------------------------------------------------------------------------------");
        System.out.println("---------------------------------Testing Actions on products----------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------");
        // Check availability of products
        Map<Integer, Integer> productsToCheck = new HashMap<>();
        productsToCheck.put(1, 1);
        System.out.println("Check Availability: " + prodMan.checkAvailability(productsToCheck));
        
        // Reduce quantity of a product
        System.out.println("Reduce Quantity: " + prodMan.reduceQuantity(productsToCheck));
        
        productsToCheck.remove(1);
        // Delete a product from the inventory
        System.out.println("Delete Product: " + prodMan.deleteProduct(10));
        
        // Check if product deleted from the inventory
        System.out.println("Deleted Product exist: " + productsToCheck.containsKey(1));
        
        productsToCheck.put(15, 1000);
        System.out.println("Increase Quantity: " + prodMan.increaseQuantity(productsToCheck));
        
        System.out.println("\n----------------------------------------------------------------------------------------------");
        System.out.println("------------------Testing edge cases on products - all should return 'false'------------------");
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println("add Product(productID already exist): " + prodMan.addProduct(9, "Ball", 8));
        System.out.println("add Product(No product name): " + prodMan.addProduct(11, "", 7));  
        System.out.println("add Product(Negative quantity): " + prodMan.addProduct(12, "Ball", -1));   
        productsToCheck.put(1, 999);
        System.out.println("Check Availability(No availability): " + prodMan.checkAvailability(productsToCheck)); 
        productsToCheck.put(1, -5);
        System.out.println("Check Availability(Negative Quantity): " + prodMan.checkAvailability(productsToCheck)); 
        System.out.println("Reduce Quantity(Negative Quantity): " + prodMan.reduceQuantity(productsToCheck)); 
        productsToCheck.put(1, 999);
        System.out.println("Reduce Quantity(More than available): " + prodMan.reduceQuantity(productsToCheck)); 
        System.out.println("Delete Product(ProductID not exist): " + prodMan.deleteProduct(99)); 
 
        System.out.println("\n----------------------------------------------------------------------------------------------");
        System.out.println("------------------Testing edge cases on orders - all should return 'false'------------------");
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println("Add Order (No Address): " + ordMan.addOrder("", 1, new int[]{1, 2, 3, 4, 5, 6, 7, 8}));
        System.out.println("Add Order (Priority not between 1-5): " + ordMan.addOrder("", 1, new int[]{1, 2, 3, 4, 5, 6, 7, 8}));
        System.out.println("Add Order (Odd numbers- meanining no quantity for last product): " + ordMan.addOrder("Israel, Jerusalem, Ramot 4", 1, new int[]{1, 7, 3}));
    }
}