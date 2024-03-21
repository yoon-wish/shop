package shop;

import java.util.ArrayList;

public class ItemManager {
	private ArrayList<Item> items;

	private ItemManager() {
		items = new ArrayList<>();
	}

	private static ItemManager instance = new ItemManager();

	public static ItemManager getInstance() {
		return instance;
	}
	
	public Item createItem(String name, int price) {
		if (duplItemName(name)) {
			System.err.println("중복된 아이디입니다.");
			return new Item();
		}
		Item item = new Item(name, price);
		items.add(item);
		System.out.println("아이템 추가완료");
		return item.clone();
	}
	
	public boolean duplItemName(String name) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName().equals(name))
				return true;
		}
		return false;
	}
	
	public Item readItem(int index) {
		return items.get(index).clone();
	}
	
	public Item deleteItem(int index) {
		return items.remove(index);
	}
	
	public Item updateItem(int index, Item item) {
		return items.set(index, item);
	}
	
	public int itemSize() {
		return items.size();
	}
}
