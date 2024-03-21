package shop;

import java.util.ArrayList;

public class User {
	private ArrayList<Cart> carts;
	private String id; 
	private String password;

	public User() {
		
	}
	
	public User(String id, String password) {
		this.id = id;
		this.password = password;
		this.carts = new ArrayList<>();
	}

	public User(String id, String password, ArrayList<Cart> carts) {
		this.id = id;
		this.password = password;
		this.carts = carts;
	}

	public String getId() {
		return this.id;
	}

	public String getPassword() {
		return this.password;
	}

	public User clone() {
		return new User(this.id, this.password, this.carts);
	}
}
