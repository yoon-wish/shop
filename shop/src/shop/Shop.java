package shop;

public class Shop {
	String title;
	
	UserManager manager = UserManager.getInstance();
	
	public Shop(String title) {
		this.title = title;
	}
	
	public void run() {
		
	}
}
