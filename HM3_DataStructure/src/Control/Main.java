package Control;

import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		ProdManLogic prodMan = ProdManLogic.getInstance();
		
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
		prodMan.printInventory();
		System.out.println("Check Availability: " + prodMan.checkAvailability(2, 7));
		System.out.println("Reduce Quantity: " + prodMan.reduceQuantity(1, 22));
		System.out.println("Delete Product: " + prodMan.deleteProduct(10));
		prodMan.printInventory();
		
		//check edge cases
		System.out.println("*******Check edge cases*******");
		System.out.println("add Product(productID already exist): " + prodMan.addProduct(9, "Ball", 8)); //product id already exist, so it will replace the old product 
		System.out.println("add Product(No product name): " + prodMan.addProduct(11, "", 7)); // no product name 
		System.out.println("add Product(Negative quantity): " + prodMan.addProduct(12, "Ball", -1));  // Negative quantity 
		
		System.out.println("Check Availability(No availability): " + prodMan.checkAvailability(2, 99)); // no availability
		System.out.println("Check Availability(Negative Quantity): " + prodMan.checkAvailability(2, -1)); //Negative quantity
		System.out.println("Reduce Quantity(Negative Quantity): " + prodMan.reduceQuantity(2, -1)); //Negative Quantity to reduce
		System.out.println("Reduce Quantity(More than available): " + prodMan.reduceQuantity(2, 100)); //More than available
		System.out.println("Delete Product(ProductID not exist): " + prodMan.deleteProduct(99)); // ProductID not exist
		prodMan.printInventory();
		
		//////Orders//////
		System.out.println("");
		System.out.println("*******Orders*******");
		
		OrdManLogic ordMan = OrdManLogic.getInstance();
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
		ordMan.printOrders();
		
		
		//insert 5 more products
		prodMan.addProduct(11, "Wardrobe", 30);
		prodMan.addProduct(12, "Stool", 30);
		prodMan.addProduct(13, "Mirror", 30);
		prodMan.addProduct(14, "TV Stand", 1);
		prodMan.addProduct(15, "Ottoman", 1);
		prodMan.printInventory();
		ordMan.printAllOrdersFromQueue();
		ordMan.processNextOrder(3);
		ordMan.printAllOrdersFromQueue();
		
		//////Reports//////
		System.out.println("");
		System.out.println("*******Reports*******");
		Reports repMan = new Reports();
		
		
		prodMan.printInventory();
		repMan.getInventoryReport();
		repMan.getKBiggestOrders();
		repMan.totalOrdersReport();
		
		
	}

}
