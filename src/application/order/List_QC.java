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

public class List_QC {

	public static void InsertQCData() {
		
		MySqlConnection mySqlConnection = new MySqlConnection();
		mySqlConnection.connectSql();
		
		System.out.println("--- QC_List loading start ---");
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("src/menu/list_qc.json"));
			JSONArray menuArray = (JSONArray) jsonObject.get("list_qc");
			for (Object obj : menuArray) {
				JSONObject jsonObj = ((JSONObject) obj);
				String type = (String) jsonObj.get("type");
				int people = ((Long)jsonObj.get("people")).intValue();
				int cost = ((Long)jsonObj.get("cost")).intValue();
				String meals = (String)jsonObj.get("meals");
				String time = (String)jsonObj.get("time");
				
				System.out.println("Type: " + type + " People: " + people + " Cost: " + cost + " Meals: " + meals + " Time: " + time);
				mySqlConnection.insertQCList(type, people, cost, meals,time);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		System.out.println("--- QC_List loading end ---");
		
		mySqlConnection.disconnectSql();
	}


}
