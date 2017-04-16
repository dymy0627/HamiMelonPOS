package application.order;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import application.MainScene;
import db.MySqlConnection;

public class MenuBuilder {

	private static Map<String, MenuBean> menuHashMap = new HashMap<>();

	public static void loadFromJson() {
		//MySqlConnection mySqlConnection = new MySqlConnection();
		//mySqlConnection.connectSql();
		if (!menuHashMap.isEmpty()) {
			System.out.println("--- cache meun ---");
			return;
		}
		System.out.println("--- meun loading start ---");
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/menu/menu.json"));
			JSONArray menuArray = (JSONArray) jsonObject.get("menu");
			for (Object obj : menuArray) {
				JSONObject jsonObj = ((JSONObject) obj);
				String id = (String) jsonObj.get("id");
				MenuBean menu = new MenuBean(id);
				menu.setSet((String) jsonObj.get("set"));
				menu.setName((String) jsonObj.get("name"));
				menu.setPrice(((Long) jsonObj.get("price")).intValue());
				menu.setMeatClass((String) jsonObj.get("class"));
				
				// insert to DB.
				//insertMenuToDB(mySqlConnection, menu);

				menuHashMap.put(id, menu);
				System.out.println(id + ", " + menu.getSet() + "-" + menu.getName() + ", " + menu.getPrice() + ", "
						+ menu.getMeatClass());
			}
			//mySqlConnection.disconnectSql();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		System.out.println("--- meun loading end ---");
	}

	public static void loadFromDB(MySqlConnection mySqlConnection) {
		if (!menuHashMap.isEmpty()) {
			System.out.println("--- cache meun ---");
			return;
		}

		if (mySqlConnection == null) {
			System.out.println("no SQL Connection");
			return;
		}

		List<MenuBean> menuBeanList;
		menuBeanList = mySqlConnection.selectMenu();

		System.out.println("--- meun loading start ---");
		for (MenuBean menu : menuBeanList) {
			menuHashMap.put(menu.getId(), menu);
			System.out.println(menu.getId() + ", " + menu.getSet() + "-" + menu.getName() + ", " + menu.getPrice()
					+ ", " + menu.getMeatClass());
		}
		System.out.println("--- meun loading end ---");

		// writeToJsonFile(menuBeanList);
	}

	public static void loadFromDB() {
		if (!menuHashMap.isEmpty()) {
			System.out.println("--- cache meun ---");
			return;
		}

		List<MenuBean> menuBeanList;
		MySqlConnection mySqlConnection = new MySqlConnection();
		mySqlConnection.connectSql();
		menuBeanList = mySqlConnection.selectMenu();
		mySqlConnection.disconnectSql();

		System.out.println("--- meun loading start ---");
		for (MenuBean menu : menuBeanList) {
			menuHashMap.put(menu.getId(), menu);
			System.out.println(menu.getId() + ", " + menu.getSet() + "-" + menu.getName() + ", " + menu.getPrice()
					+ ", " + menu.getMeatClass());
		}
		System.out.println("--- meun loading end ---");

		// writeToJsonFile(menuBeanList);
	}

	@SuppressWarnings("unused")
	private static void insertMenuToDB(MySqlConnection mySqlConnection, MenuBean menu) {
		String meat_class = menu.getMeatClass();
		String[] meats = meat_class.split("_");
		for (String meatName : meats) {

			System.out.println("meatName = " + meatName);
			if (meatName.contains("*")) {
				meatName = meatName.substring(0, meatName.indexOf("*"));
				System.out.println("meatName * = " + meatName);
			}

			if (meatName.equals("鯖")) {
				meat_class = meat_class.replaceAll(meatName, "鯖魚");
				meatName = "鯖魚";
			} else if (meatName.contains("鮭")) {
				meat_class = meat_class.replaceAll(meatName, "鮭魚");
				meatName = "鮭魚";
			} else if (meatName.contains("鱈")) {
				meat_class = meat_class.replaceAll(meatName, "鱈魚");
				meatName = "鱈魚";
			} else if (meatName.contains("朗心")) {
				meat_class = meat_class.replaceAll(meatName, "沙朗心");
				meatName = "沙朗心";
			} else if (meatName.contains("蚵")) {
				meat_class = meat_class.replaceAll(meatName, "蚵仔");
				meatName = "蚵仔";
			} else if (meatName.contains("牛肉")) {
				meat_class = meat_class.replaceAll(meatName, "牛肉片");
				meatName = "牛肉片";
			} else if (meatName.contains("羊肉")) {
				meat_class = meat_class.replaceAll(meatName, "羊肉片");
				meatName = "羊肉片";
			} else if (meatName.contains("豬肉")) {
				meat_class = meat_class.replaceAll(meatName, "豬肉片");
				meatName = "豬肉片";
			} else if (meatName.contains("牛小")) {
				meat_class = meat_class.replaceAll(meatName, "牛小排");
				meatName = "牛小排";
			} else if (meatName.contains("鯛")) {
				meat_class = meat_class.replaceAll(meatName, "鯛魚");
				meatName = "鯛魚";
			} else if (meatName.contains("沙拉")) {
				meat_class = meat_class.replaceAll(meatName, "龍蝦沙拉");
				meatName = "龍蝦沙拉";
			} else if (meatName.contains("紐約")) {
				meat_class = meat_class.replaceAll(meatName, "紐約克");
				meatName = "紐約克";
			} else if (meatName.contains("魷")) {
				meat_class = meat_class.replaceAll(meatName, "魷魚");
				meatName = "魷魚";
			}
			System.out.println("meatName change= " + meatName);

			for (String key : MainScene.meatsClassHashMap.keySet()) {
				if (meatName.equals(MainScene.meatsClassHashMap.get(key))) {
					meat_class = meat_class.replaceAll(meatName, key);
				}
			}
		}
		System.out.println("meat_class = " + meat_class);
		mySqlConnection.executeSql(
				"insert into hamimelon.Menu(id, name, price, dish_set, meat_class)values('" + menu.getId() + "','"
						+ menu.getName() + "','" + menu.getPrice() + "','" + menu.getSet() + "','" + meat_class + "')");
	}

	private static String parseMeatClassToString(String meat_class) {
		String[] meats = meat_class.split("_");
		for (String meatId : meats) {
			if (meatId.contains("*")) {
				meatId = meatId.substring(0, meatId.indexOf("*"));
				System.out.println("meatId = " + meatId);
			}
			for (String key : MainScene.meatsClassHashMap.keySet()) {
				if (meatId.equals(key)) {
					meat_class = meat_class.replaceAll(meatId, MainScene.meatsClassHashMap.get(key));
					System.out.println("meat_class = " + meat_class);
				}
			}
		}
		return meat_class;
	}

	@SuppressWarnings("unchecked")
	public static void writeToJsonFile(List<MenuBean> menuBeanList) {
		JSONObject obj = new JSONObject();
		JSONArray menuArray = new JSONArray();
		for (MenuBean menu : menuBeanList) {
			JSONObject obj2 = new JSONObject();
			obj2.put("set", menu.getSet());
			obj2.put("id", menu.getId());
			obj2.put("name", menu.getName());
			obj2.put("price", menu.getPrice());
			obj2.put("class", parseMeatClassToString(menu.getMeatClass()));
			menuArray.add(obj2);
		}
		obj.put("menu", menuArray);

		try (FileWriter file = new FileWriter("src/menu/menu.json")) {
			file.write(obj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static MenuBean getMealById(String id) {
		return menuHashMap.get(id);
	}

	public static Map<String, MenuBean> getMenuMap() {
		return menuHashMap;
	}
}
