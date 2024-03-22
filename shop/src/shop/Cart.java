package shop;

import java.util.ArrayList;

public class Cart {
	private String id;
	private ArrayList<Item> list;

	public Cart(String id) {
		this.id = id;
		list = new ArrayList<>();
	}

	public String getId() {
		return this.id;
	}

	public void addList(Item item) {
		this.list.add(item);
	}
	
	public ArrayList<Item> getList(){
		return this.list;
	}
	
	public int listSize() {
		return this.list.size();
	}

	public void removeList(int index) {
		this.list.remove(index);
	}


}
