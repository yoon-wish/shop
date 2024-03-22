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

	public ArrayList<Item> getList(){
		return this.list;
	}
	public Item getList(int index){
		return this.list.get(index);
	}

	public void addListItem(Item item) {
		this.list.add(item);
	}
	
	public void deleteListItem(int index) {
		this.list.remove(index);
	}
	
	public int listSize() {
		return this.list.size();
	}



}
