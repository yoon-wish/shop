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

	private UserManager userManager = UserManager.getInstance();
	private ItemManager itemManager = ItemManager.getInstance();

	private String title;

	public Shop(String title) {
		this.title = title;
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
		if (select == JOIN)
			join();
		else if (select == LEAVE)
			leave();
		else if (select == LOGIN)
			login();
		else if (select == LOGOUT)
			logout();
		else if (select == SHOPPING)
			shopping();
		else if (select == MY_PAGE) {
			printMyPageSubMenu();
			runMyPage(option());
		} else if (select == HOST) {
			printHostMenu();
			runHost(option());
		}
	}

	private void join() {
		String id = inputString("id");
		String password = inputString("password");
		
		userManager.createUser(id, password);
		
	}

	private void leave() {

	}

	private void login() {

	}

	private void logout() {

	}

	private void shopping() {

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

	}

	private void delete() {

	}

	private void modify() {

	}

	private void payment() {

	}

	private void addItem() {

	}

	private void deleteItem() {

	}

	private void modifyItem() {

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
}
