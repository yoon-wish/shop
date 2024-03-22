package shop;

import java.util.ArrayList;
import java.util.Arrays;
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
	private FileManager fileManager = FileManager.getInstance();

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

		String name = itemManager.readItem(number).getName();
		int index = checkDuplProducts(name);
		if (index == -1) {
			Item item = itemManager.readItem(number);
			userManager.readUserCart(log).addListItem(item);
		} else {
			Item item = userManager.readUserCart(log).getList(index);
			item.setCount();
		}
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
		showBasket();
	}

	private void showBasket() {
		int size = userManager.readUserCart(log).listSize();
		for (int i = 0; i < size; i++) {
			Item item = userManager.readUserCart(log).getList(i);
			String itemName = item.getName();
			int itemCount = item.getCount();
			int itemPrice = item.getPrice();
			System.out.printf("%s) %d개 (개당 %d원)\n", itemName, itemCount, itemPrice);

		}
	}

	private void delete() {
		myBasket();
		String delProduct = inputString("품목명");

		int index = checkDuplProducts(delProduct);
		if (index == -1) {
			System.err.println("존재하지 않습니다.");
			return;
		}

		userManager.readUserCart(log).deleteListItem(index);
		System.out.println("삭제완료");

	}

	public int checkDuplProducts(String name) {
		ArrayList<Item> temp = userManager.readUser(log).getCart().getList();
		int size = temp.size();
		for (int i = 0; i < size; i++) {
			Item item = temp.get(i);
			if (item.getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	private void modify() {
		myBasket();
		String delProduct = inputString("품목명");

		int index = checkDuplProducts(delProduct);
		if (index == -1) {
			System.err.println("존재하지 않습니다.");
			return;
		}

		int mount = inputNumber("수량");
		if (mount < 1) {
			System.err.println("1이상 입력");
			return;
		}

		userManager.readUserCart(log).getList(index).setCount(mount);
		System.out.println("수정완료");
	}

	private void payment() {
		int size = userManager.readUserCart(log).listSize();
		int cash = 0;
		for (int i = 0; i < size; i++) {
			Item item = userManager.readUserCart(log).getList(i);
			cash += item.getCount() * item.getPrice();
		}

		System.out.println("총액: " + cash + "원");
		String input = inputString("결제하시겠습니까?(y/n)");
		if (input.equals("y")) {
			result += cash;
			System.out.println("결제완료");
			userManager.updateUser(log); // 털어내기
		}
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
			Cart cart = userManager.readUserCart(i);
			for (int j = 0; j < cart.listSize(); j++) {
				Item item = cart.getList(j);
				if (item.getName().equals(itemName)) {
					cart.deleteListItem(index);
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

		modifyMyItemName(index, name);

		Item item = new Item(name, itemManager.readItem(index).getPrice());
		itemManager.updateItem(index, item);

		System.out.println("수정완료");
	}

	private void modifyMyItemName(int index, String changeName) {
		String itemName = itemManager.readItem(index).getName();
		for (int i = 0; i < userManager.userSize(); i++) {
			Cart cart = userManager.readUserCart(i);
			for (int j = 0; j < cart.listSize(); j++) {
				if (itemName.equals(cart.getList(j).getName())) {
					cart.getList(j).setName(changeName);
				}
			}
		}
	}

	private void modifyItemPrice(int index) {
		int price = inputNumber("수정할 가격");

		if (price < 1) {
			System.err.println("1원이상 입력");
			return;
		}

		modifyMyItemPrice(index, price);

		Item item = new Item(itemManager.readItem(index).getName(), price);
		itemManager.updateItem(index, item);

		System.out.println("수정완료");
	}

	private void modifyMyItemPrice(int index, int price) {
		int itemPrice = itemManager.readItem(index).getPrice();
		for (int i = 0; i < userManager.userSize(); i++) {
			Cart cart = userManager.readUserCart(i);
			for (int j = 0; j < cart.listSize(); j++) {
				if (itemPrice == cart.getList(j).getPrice()) {
					cart.getList(j).setPrice(price);
				}
			}
		}
	}

	private int printModifySubMenu() {
		System.out.println("1. 상품명");
		System.out.println("2. 가격");

		return inputNumber("선택");
	}

	private void result() {
		System.out.printf("총 매출은 %d원입니다.\n", result);
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

	public String saveInfo() {
		String info = "";

		// 품목 정보들
		for (int i = 0; i < itemManager.itemSize(); i++) {
			info += itemManager.readItem(i).getName() + "/";
			info += itemManager.readItem(i).getPrice();

			if (i < itemManager.itemSize() - 1)
				info += "/";
		}

		info += "\n";

		// 회원 정보들
		for (int i = 0; i < userManager.userSize(); i++) {
			info += userManager.readUser(i).getId() + "/";
			info += userManager.readUser(i).getPassword();
			Cart cart = userManager.readUserCart(i);
			if (cart.listSize() > 0) {
				info += "/";
			}
			for (int j = 0; j < cart.listSize(); j++) {
				info += cart.getList(j).getName() + "/";
				info += cart.getList(j).getPrice() + "/";
				info += cart.getList(j).getCount();

				if (j < cart.listSize() - 1)
					info += "/";
			}

			if (i < userManager.userSize() - 1) {
				info += "\n";
			}

		}

		return info;
	}

	// 품목이름1/가격1/품목이름2/가격2/품목이름3/가격3 ...
	// 아이디/비밀번호/품목이름1/가격1/개수1/품목이름2/가격2/개수2 ...
	// 아이디/비밀번호/품목이름1/가격1/개수1
	// ...

	public void init() {
		String text = fileManager.load();

		if (text != "") {
			String[] temp = text.split("\n");
			int index = 0;
			for (int i = 1; i < temp.length; i++) {
				String[] info = temp[i].split("/");
				if (!info[0].equals("1111"))
					userManager.createUser(info[0], info[1]);
				int size = info.length - 2;
				for (int j = 2; j < size; j += 3) {
					Item item = new Item(info[j], Integer.parseInt(info[j + 1]), Integer.parseInt(info[j + 2]));
					userManager.readUserCart(index).addListItem(item);
				}
				index++;
			}

			String[] itemInfo = temp[0].split("/");
			for (int i = 0; i < itemInfo.length; i += 2) {
				itemManager.createItem(itemInfo[i], Integer.parseInt(itemInfo[i + 1]));
			}
		}
	}

	public void run() {
		init();
		while (true) {
			printMenu();
			runMenu(option());
			fileManager.save(saveInfo());
		}
	}

	// 유저 -
	// ㄴ 회원가입 [O]
	// ㄴ 탈퇴 [O]
	// ㄴ 로그인 [O]
	// ㄴ 로그아웃 [O]
	// ㄴ 쇼핑하기 [O]
	// ㄴ 마이페이지
	// ㄴ 내장바구니 [O]
	// ㄴ 항목삭제 [O]
	// ㄴ 수량수정 [O]
	// ㄴ 결제 [O]
	// 파일
	// ㄴ 자동저장 [O]
	// ㄴ 자동로드 [X]

	// 관리자 -
	// ㄴ 아이템
	// ㄴ 등록 [O]
	// ㄴ 삭제 [O]
	// ㄴ 수정 [X]
	// ㄴ 조회(총 매출) [O]

}
