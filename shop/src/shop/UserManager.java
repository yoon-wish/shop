package shop;

import java.util.ArrayList;

public class UserManager {
	private ArrayList<User> group;
	
	private UserManager() {
		group = new ArrayList<>();
	}
	
	private static UserManager instance = new UserManager();
	
	public static UserManager getInstance() {
		return instance;
	}
}
