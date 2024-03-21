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
}
