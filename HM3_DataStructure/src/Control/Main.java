package Control;


public class Main {

	public static void main(String[] args) {
		ProdManLogic prodMan = ProdManLogic.getInstance();
		
		prodMan.addProduct(1, "Table", 2);
		prodMan.addProduct(2, "Chair", 8);
		prodMan.addProduct(3, "Picture", 2);
		prodMan.addProduct(4, "Lamp", 5);
		prodMan.addProduct(5, "Sofa", 3);
		prodMan.addProduct(6, "Desk", 4);
		prodMan.addProduct(7, "Bookshelf", 6);
		prodMan.addProduct(8, "Bed", 1);
		prodMan.addProduct(9, "Dresser", 2);
		prodMan.addProduct(10, "Nightstand", 7);
		prodMan.printInventory();
		System.out.println("Check Availability: " + prodMan.checkAvailability(2, 7));
		System.out.println("Reduce Quantity: " + prodMan.reduceQuantity(1, 2));
		System.out.println("Delete Product: " + prodMan.deleteProduct(10));
		prodMan.printInventory();
		
		//check edge cases
		System.out.println("*******Check edge cases*******");
		System.out.println("add Product(product id already exist - should replace): " + prodMan.addProduct(9, "Ball", 8)); //product id already exist, so it will replace the old product 
		System.out.println("add Product(No product name): " + prodMan.addProduct(11, "", 7)); // no product name 
		System.out.println("add Product(Negative quantity): " + prodMan.addProduct(12, "Ball", -1));  // Negative quantity 
		
		System.out.println("Check Availability(No availability): " + prodMan.checkAvailability(2, 99)); // no availability
		System.out.println("Check Availability(Negative Quantity): " + prodMan.checkAvailability(2, -1)); //Negative quantity
		System.out.println("Reduce Quantity(Negative Quantity): " + prodMan.reduceQuantity(2, -1)); //Negative Quantity to reduce
		System.out.println("Reduce Quantity(More than available): " + prodMan.reduceQuantity(2, 100)); //More than available
		System.out.println("Delete Product(ProductID not exist): " + prodMan.deleteProduct(99)); // ProductID not exist
		prodMan.printInventory();

		
//		OrdManLogic ordMan = OrdManLogic.getInstance();
//		ordMan.printOrders();
//		ordMan.addOrder("Israel, Rishon LeZion, Rabin St 2", 4, 10, true,false);
//		ordMan.printOrders();
	}

}
