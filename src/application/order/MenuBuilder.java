package application.order;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MenuBuilder {

	private static Map<String, Meal> mealMap = new HashMap<>();

	public static void load() {
		System.out.println("--- meun loading start ---");
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/menu/menu.json"));
			JSONArray menuArray = (JSONArray) jsonObject.get("menu");
			for (Object obj : menuArray) {
				JSONObject jsonObj = ((JSONObject) obj);
				String id = (String) jsonObj.get("id");
				Meal meal = new Meal(id);
				meal.setSet((String) jsonObj.get("set"));
				meal.setName((String) jsonObj.get("name"));
				meal.setPrice(((Long) jsonObj.get("price")).intValue());
				mealMap.put(id, meal);
				System.out.println(id + ", " + meal.getSet() + "-" + meal.getName() + ", " + meal.getPrice());
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		System.out.println("--- meun loading end ---");
	}

	public static Meal getMealById(String id) {
		return mealMap.get(id);
	}
}
