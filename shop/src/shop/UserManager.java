package shop;

import java.util.ArrayList;
import java.util.Random;

public class UserManager {
	private ArrayList<User> group;

	private UserManager() {
		group = new ArrayList<>();
	}

	private static UserManager instance = new UserManager();

	public static UserManager getInstance() {
		return instance;
	}

	// CRUD
	public User createUser(String id, String password) {
		if (duplUserId(id)) {
			System.err.println("중복된 아이디입니다.");
			return new User();
		}

		User user = new User(id, password);
		group.add(user);
		System.out.println("회원가입 완료");
		return user.clone();
	}

	private boolean duplUserId(String id) {
		for (int i = 0; i < group.size(); i++) {
			if (group.get(i).getId().equals(id))
				return true;
		}
		return false;
	}
}
