package shop;

import java.util.ArrayList;

public class Cart {
	private String id;
	private ArrayList<Item> list;
	
	public Cart(String id) {
		this.id = id;
		list = new ArrayList<>();
	}
	
	public Cart(String id, ArrayList<Item> list) {
		this.id = id;
		this.list = list;
	}

	public String getId() {
		return this.id;
	}
	
	public int listSize() {
		return this.list.size();
	}
	
	public Item getList(int index) {
		return list.get(index);
	}
	
	public void removeList(int index) {
		list.remove(index);
	}
	
	public Cart clone() {
		return new Cart(this.id, this.list);
	}

}
