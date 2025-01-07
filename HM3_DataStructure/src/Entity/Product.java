package Entity;

public class Product {

	private int product_id;
	private String product_name;
	
	//	constructor
	public Product(int product_id, String product_name) {
		this.product_id = product_id;
		this.product_name = product_name;
	}
	
	public int getProdcutID() {
		return product_id;
	}
	
	public String getProdcutName() {
		return product_name;
	}
	
}
