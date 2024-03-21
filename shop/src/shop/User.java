package shop;

import java.util.ArrayList;

public class User {
	private String id;
	private String password;
	private Cart cart;

	public User() {

	}

	public User(String id, String password) {
		this.id = id;
		this.password = password;
		this.cart = new Cart(this.id);
	}

	public User(String id, String password, Cart cart) {
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return this.id;
	}

	public String getPassword() {
		return this.password;
	}

	public Cart getCart() {
		if(this.cart == null)
			return new Cart(this.id);
		return this.cart.clone();
	}

	public User clone() {
		return new User(this.id, this.password, this.cart);
	}
}
