package shop;

import java.util.ArrayList;

public class UserManager {
	private ArrayList<User> list;
	
	private UserManager() {
		list = new ArrayList<>();
	}
	
	private static UserManager instance = new UserManager();
	
	public static UserManager getInstance() {
		return instance;
	}
}
