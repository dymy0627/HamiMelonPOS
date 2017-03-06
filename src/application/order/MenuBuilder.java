package application.order;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import db.MySqlConnection;

public class MenuBuilder {

	private static Map<String, MealBean> mealMap = new HashMap<>();

	public static void load() {
//		MySqlConnection mySqlConnection = new MySqlConnection();
//		mySqlConnection.connectSql();
		System.out.println("--- meun loading start ---");
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/menu/menu.json"));
			JSONArray menuArray = (JSONArray) jsonObject.get("menu");
			for (Object obj : menuArray) {
				JSONObject jsonObj = ((JSONObject) obj);
				String id = (String) jsonObj.get("id");
				MealBean meal = new MealBean(id);
				meal.setSet((String) jsonObj.get("set"));
				meal.setName((String) jsonObj.get("name"));
				meal.setPrice(((Long) jsonObj.get("price")).intValue());
				meal.setMeatClass((String) jsonObj.get("class"));
				// insert to DB.
//				insertMenuToDB(mySqlConnection, meal);
				mealMap.put(id, meal);
				System.out.println(id + ", " + meal.getSet() + "-" + meal.getName() + ", " + meal.getPrice() + ", "
						+ meal.getMeatClass());
			}
//			mySqlConnection.disconnectSql();
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		System.out.println("--- meun loading end ---");
	}

	private static void insertMenuToDB(MySqlConnection mySqlConnection, MealBean meal) {
		mySqlConnection.executeSql("insert into hamimelon.Menu(id, name, price, dish_set, meat_class)values('"
				+ meal.getId() + "','" + meal.getName() + "','" + meal.getPrice() + "','" + meal.getSet() + "','"
				+ meal.getMeatClass() + "')");
	}

	public static MealBean getMealById(String id) {
		return mealMap.get(id);
	}

	public static Map<String, MealBean> getMenuMap() {
		return mealMap;
	}
}
