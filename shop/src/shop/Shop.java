package shop;

public class Shop {
	UserManager userManager = UserManager.getInstance();
	ItemManager itemManager = ItemManager.getInstance();

	String title;
	
	public Shop(String title) {
		this.title = title;
	}
	
	public void run() {
		
	}
}
