package shop;

public class Item {
	private String name;
	private int price;
	private int count;
	
	// 로드용 생성자
	public Item(String name, int price, int count) {
		this.name = name;
		this.price = price;
		this.count = count;
	}
	
	public Item(String name, int price) {
		this.name = name;
		this.price = price;
		this.count = 1;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name =  name;
	}
	
	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
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
