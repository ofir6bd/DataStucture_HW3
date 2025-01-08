package Control;


public class Main {

	public static void main(String[] args) {
		InvManLogic invMan = InvManLogic.getInstance();
		invMan.printInventory();
		invMan.reduceQuantity(2, 2);
		invMan.printInventory();
		System.out.println(invMan.checkAvailability(2, 7));
		invMan.deleteProduct(2);
		invMan.printInventory();
		invMan.addProduct("TV", 10);
		invMan.printInventory();
	}

}
