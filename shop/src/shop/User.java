package shop;


public class User {
	private String id;
	private String password;
	private Cart cart;

//	public User() {
//
//	}
	
	public User(String id, String password) {
		this.id = id;
		this.password = password;
		this.cart = new Cart(this.id);
	}

	public User(String id, String password, Cart cart) {
		this.id = id;
		this.password = password;
		this.cart = cart;
	}

	public String getId() {
		return this.id;
	}

	public String getPassword() {
		return this.password;
	}

	public Cart getCart() {
		return this.cart;
	}
	
	public void setCart() {
		this.cart = new Cart(this.id);
	}

	public User clone() {
		return new User(this.id, this.password, this.cart);
	}
}
