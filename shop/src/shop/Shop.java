package shop;

import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
	private Scanner sc = new Scanner(System.in);

	private final int JOIN = 1;
	private final int LEAVE = 2;
	private final int LOGIN = 3;
	private final int LOGOUT = 4;
	private final int SHOPPING = 5;
	private final int MY_PAGE = 6;
	private final int HOST = 7;

	private final int BASKET = 1;
	private final int DELETE = 2;
	private final int MODIFY = 3;
	private final int PAYMENT = 4;

	private final int ADD_ITEM = 1;
	private final int DELETE_ITEM = 2;
	private final int MODIFY_ITEM = 3;
	private final int RESULT = 4;

	private final int CHECK_LOGIN = 1;
	private final int CHECK_LOGOUT = 2;

	private final int MODIFY_NAME = 1;
	private final int MODIFY_PRICE = 2;

	private UserManager userManager = UserManager.getInstance();
	private ItemManager itemManager = ItemManager.getInstance();

	private String title;
	private int log;
	private int result;

	public Shop(String title) {
		this.title = title;
		setMarket();
	}

	private void setMarket() {
		log = -1;
		userManager.createUser("1111", "1111"); // 관리자 계정
	}

	private void printMenu() {
		System.out.printf("==== [%s MARKET] ====\n", title);
		System.out.println("[1] 회원가입");
		System.out.println("[2] 회원탈퇴");
		System.out.println("[3] 로 그 인");
		System.out.println("[4] 로그아웃");
		System.out.println("[5] 쇼핑하기");
		System.out.println("[6] 마이페이지");
		System.out.println("[7] 관리자");
	}

	private int option() {
		return inputNumber("메뉴");
	}

	private void runMenu(int select) {
		if (select == JOIN && isLogin(CHECK_LOGOUT))
			join();
		else if (select == LEAVE && isLogin(CHECK_LOGIN))
			leave();
		else if (select == LOGIN && isLogin(CHECK_LOGOUT))
			login();
		else if (select == LOGOUT && isLogin(CHECK_LOGIN))
			logout();
		else if (select == SHOPPING && isLogin(CHECK_LOGIN))
			shopping();
		else if (select == MY_PAGE && isLogin(CHECK_LOGIN)) {
			printMyPageSubMenu();
			runMyPage(option());
		} else if (select == HOST && checkHost()) {
			printHostMenu();
			runHost(option());
		}
	}

	private boolean isLogin(int check) {
		if (check == CHECK_LOGOUT && log != -1) {
			System.err.println("로그아웃 후 이용가능");
			return false;
		}

		if (check == CHECK_LOGIN && log == -1) {
			System.err.println("로그인 후 이용가능");
			return false;
		}

		return true;
	}

	private boolean checkHost() {
		if (log != 0) {
			System.err.println("관리자 계정만 접근가능합니다.");
			return false;
		}
		return true;
	}

	private void join() {
		String id = inputString("id");
		String password = inputString("password");

		userManager.createUser(id, password);
	}

	private void leave() {
		if (log == 0) {
			System.err.println("관리자 계정은 탈퇴 불가");
			return;
		}

		String password = inputString("password");
		if (userManager.readUser(log).getPassword().equals(password)) {
			System.out.println("탈퇴완료");
			log = -1;
		} else
			System.err.println("비밀번호가 일치하지 않습니다.");
	}

	private void login() {
		String id = inputString("id");
		int index = userManager.findIndexById(id);

		if (index == -1) {
			System.err.println("존재하지 않는 회원입니다.");
			return;
		}

		String password = inputString("password");
		User user = userManager.readUser(index);
		if (user.getPassword().equals(password)) {
			log = index;
			System.out.printf("[%s]님 로그인 성공\n", user.getId());
		} else
			System.err.println("비밀번호가 틀렸습니다.");
	}

	private void logout() {
		log = -1;
		System.err.println("로그아웃 되었습니다.");
	}

	private void shopping() {
		showItem();
		int number = inputNumber("상품 번호") - 1;
		if (number < 0 || number >= itemManager.itemSize()) {
			return;
		}

		Item item = itemManager.readItem(number);
		userManager.addListItem(log, item);
		System.out.println("장바구니를 확인하세요.");
	}

	private void printMyPageSubMenu() {
		System.out.println("1. 장바구니");
		System.out.println("2. 항목삭제");
		System.out.println("3. 수량수정");
		System.out.println("4. 결    제");
	}

	private void printHostMenu() {
		System.out.println("1. 아이템등록");
		System.out.println("2. 아이템삭제");
		System.out.println("3. 아이템수정");
		System.out.println("4. 총매출조회");
	}

	private void runMyPage(int select) {
		if (select == BASKET)
			myBasket();
		else if (select == DELETE)
			delete();
		else if (select == MODIFY)
			modify();
		else if (select == PAYMENT)
			payment();
	}

	private void runHost(int select) {
		if (select == ADD_ITEM)
			addItem();
		else if (select == DELETE_ITEM)
			deleteItem();
		else if (select == MODIFY_ITEM)
			modifyItem();
		else if (select == RESULT)
			result();
	}

	private void myBasket() {
		int[] count = new int[itemManager.itemSize()];
		countProduct(count);
		showBasket(count);
	}

	private void countProduct(int[] count) {
		for (int i = 0; i < userManager.getMyListSize(log); i++) {
			Item item = userManager.getMyList(log, i);
			for (int j = 0; j < itemManager.itemSize(); j++) {
				if (item.getName().equals(itemManager.readItem(j).getName())) {
					count[j]++;
				}
			}
		}
	}

	private int countProductPrice() {
		int tempResult = 0;
		for (int i = 0; i < userManager.getMyListSize(log); i++) {
			Item item = userManager.getMyList(log, i);
			for (int j = 0; j < itemManager.itemSize(); j++) {
				if (item.getName().equals(itemManager.readItem(j).getName())) {
					tempResult += itemManager.readItem(j).getPrice();
				}
			}
		}
		return tempResult;
	}

	private void showBasket(int[] count) {
		for (int i = 0; i < count.length; i++) {
			String itemName = itemManager.readItem(i).getName();
			int itemPrice = itemManager.readItem(i).getPrice();

			if (count[i] > 0) {
				System.out.printf("%s) %d개 (개당 %d원)\n", itemName, count[i], itemPrice);
			}
		}
	}

	private void delete() {

	}

	private void modify() {

	}

	private void payment() {

	}

	private void addItem() {
		String name = inputString("아이템명");
		int price = inputNumber("상품 가격");

		itemManager.createItem(name, price);
	}

	private void showItem() {
		for (int i = 0; i < itemManager.itemSize(); i++)
			System.out.println((i + 1) + ") " + itemManager.readItem(i));
	}

	private void deleteItem() {
		showItem();
		int index = inputNumber("삭제할 아이템 번호") - 1;
		if (index < 0 || index >= itemManager.itemSize())
			return;

		deleteUserItem(index); // 유저가 장바구니에 담아둔 아이템들도 지우기
		itemManager.deleteItem(index);
	}

	private void deleteUserItem(int index) {
		String itemName = itemManager.readItem(index).getName();

		for (int i = 0; i < userManager.userSize(); i++) {
			Cart cart = userManager.readUser(i).getCart();
			for (int j = 0; j < cart.listSize(); j++) {
				Item item = cart.getList().get(j);
				if (item.getName().equals(itemName)) {
					cart.removeList(index);
				}
			}
		}
		System.out.println("삭제완료");
	}

	private void modifyItem() {
		showItem();
		int index = inputNumber("수정할 품목") - 1;
		int menu = printModifySubMenu();
		if (menu == MODIFY_NAME) {
			modifyItemName(index);
		} else if (menu == MODIFY_PRICE)
			modifyItemPrice(index);
	}

	private void modifyItemName(int index) {
		String name = inputString("수정할 아이템명");
		if (itemManager.duplItemName(name)) {
			System.err.println("이미 존재하는 품목입니다.");
			return;
		}

		Item item = new Item(name, itemManager.readItem(index).getPrice());
		itemManager.updateItem(index, item);

		System.out.println("수정완료");
	}

	private void modifyItemPrice(int index) {
		int price = inputNumber("수정할 가격");

		Item item = new Item(itemManager.readItem(index).getName(), price);
		itemManager.updateItem(index, item);

		System.out.println("수정완료");
	}

	private int printModifySubMenu() {
		System.out.println("1. 상품명");
		System.out.println("2. 가격");

		return inputNumber("선택");
	}

	private void result() {

	}

	private int inputNumber(String message) {
		System.out.print(message + " : ");
		int number = -1;
		try {
			String input = sc.next();
			number = Integer.parseInt(input);
		} catch (Exception e) {
			System.err.println("숫자를 입력하세요.");
		}
		return number;
	}

	private String inputString(String message) {
		System.out.print(message + " : ");
		return sc.next();
	}

	public void run() {
		while (true) {
			printMenu();
			runMenu(option());
		}
	}

	// 유저 -
	// ㄴ 회원가입 [O]
	// ㄴ 탈퇴 [O]
	// ㄴ 로그인 [O]
	// ㄴ 로그아웃 [O]
	// ㄴ 쇼핑하기 [O]
	// ㄴ 마이페이지
	// ㄴ 내장바구니 [X]
	// ㄴ 항목삭제 [X]
	// ㄴ 수량수정 [X]
	// ㄴ 결제 [X]
	// 파일
	// ㄴ 자동저장 [X]
	// ㄴ 자동로드 [X]

	// 관리자 -
	// ㄴ 아이템
	// ㄴ 등록 [O]
	// ㄴ 삭제 [O]
	// ㄴ 수정 [O]
	// ㄴ 조회(총 매출) [X]

}
