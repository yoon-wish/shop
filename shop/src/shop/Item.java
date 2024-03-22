package shop;

public class Item {
	private String name;
	private int price;
	private int count;
	
	public Item() {
		
	}
	
	public Item(String name, int price) {
		this.name = name;
		this.price = price;
		this.count = 1;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getPrice() {
		return this.price;
	}

	public int getCount() {
		return this.count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void setCount() {
		count++;
	}
	
	public Item clone() {
		return new Item(this.name, this.price);
	}
	
	@Override
	public String toString() {
		return String.format("%s : %d원", this.name, this.price);
	}
	
}
