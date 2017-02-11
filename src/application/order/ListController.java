package application.order;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import db.MySqlConnection;

import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ListController implements Initializable {

	@FXML
	private ComboBox<String> DiscountCombo;

	@FXML
	private Button p_page;
	@FXML
	private Button n_page;

	@FXML
	private Label num_people;// 來客數
	@FXML
	private Label total_money;// 金額
	private int list_money;

	@FXML
	private ListView<String> menulist;

	@FXML
	private Label type;// 消費型態

	private ListProperty<String> listProperty = new SimpleListProperty<>();
	private List<String> passing_list = new ArrayList<>();

	private int rain_special, pair_special, deluxe_special, chef_special;

	private static Map<String, Integer> mMealClassMap = new HashMap<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// populate the fruit combo box with item choices.
		DiscountCombo.getItems().setAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

		rain_special = 0;
		pair_special = 0;
		deluxe_special = 0;
		chef_special = 0;
		mMealClassMap.clear();

		// listen for changes to the fruit combo box selection and update the
		// displayed fruit image accordingly.
		DiscountCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> selected, String oldPeople, String newPeople) {
				if (oldPeople != null) {
					switch (oldPeople) {
					case "1":
						System.out.print("0");
						break;
					case "2":
						System.out.print("0");
						break;
					case "3":
						System.out.print("0");
						break;
					case "4":
						System.out.print("0");
						break;
					case "5":
						System.out.print("0");
						break;
					case "6":
						System.out.print("0");
						break;
					case "7":
						System.out.print("0");
						break;
					case "8":
						System.out.print("0");
						break;
					case "9":
						System.out.print("0");
						break;
					case "10":
						System.out.print("0");
						break;
					}
				}

				if (newPeople != null) {
					switch (newPeople) {
					case "1":
						System.out.print("1");
						break;
					case "2":
						System.out.print("2");
						break;
					case "3":
						System.out.print("3");
						break;
					case "4":
						System.out.print("4");
						break;
					case "5":
						System.out.print("5");
						break;
					case "6":
						System.out.print("6");
						break;
					case "7":
						System.out.print("7");
						break;
					case "8":
						System.out.print("8");
						break;
					case "9":
						System.out.print("9");
						break;
					case "10":
						System.out.print("10");
						break;
					}
				}
			}
		});

	}

	@FXML
	protected void PreviousPageButtonAction(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MenuStage.fxml"));
		fxmlLoader.load();
		MenuController controller = fxmlLoader.<MenuController>getController();
		controller.setClosePop(true);
	}

	@FXML
	protected void PrintButtonAction(ActionEvent event) {
		System.out.println("print work");
		System.out.println("用餐型態:" + type.getText() + " 人數:" + num_people.getText() + " 金額:" + list_money + " 餐點數量:"
				+ passing_list.size());
		System.out.println(
				"風雨:" + rain_special + " 雙人:" + pair_special + " 豪華:" + deluxe_special + " 特餐:" + chef_special);

		for (Entry<String, Integer> id : mMealClassMap.entrySet())
			System.out.println(id.getKey() + " " + id.getValue() + " ");

		dataBaseConnection(type.getText(), num_people.getText(), list_money);
	}

	private void dataBaseConnection(String type, String people_num, int list_money) {

		new Thread(new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {
				MySqlConnection mySqlConnection = new MySqlConnection();
				mySqlConnection.connectSql();
				mySqlConnection.insertListData(type, Integer.parseInt(people_num), list_money);
				mySqlConnection.disconnectSql();
				return true;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				System.out.println("Menu Insert Done!");
			}

			@Override
			protected void failed() {
				super.failed();
				System.out.println("Menu Insert Failed!");
			}
		}).start();

	}

	public void setMoney(int money) {
		System.out.println("money=" + money);
		total_money.setText("總價 " + Integer.toString(money));
		list_money = money;
	}

	public void setPeople(int people) {
		num_people.setText(Integer.toString(people));
	}

	public void setMenuList(Map<String, ListItem> listItemMap) {
		System.out.println("menu list size = " + listItemMap.size());
		System.out.println("--- menu list ---");
		for (String id : listItemMap.keySet()) {

			ListItem listItem = listItemMap.get(id);
			String set_name = listItem.getMeal().getSet();
			String meal_name = listItem.getMeal().getName();
			int meal_price = listItem.getMeal().getPrice();
			int item_number = listItem.getNumber();
			int item_price = item_number * meal_price;
			System.out.println(set_name + "_" + meal_name + " * " + item_number + " = " + item_price);
			passing_list.add(set_name + "\t" + meal_name + "\t" + item_number + "\t" + item_price);

			checkMeatClass(listItem);
			checkSpecialMeal(listItem);
		}
		System.out.println("-----------------");

		menulist.itemsProperty().bind(listProperty);
		listProperty.set(FXCollections.observableArrayList(passing_list));

		System.out.println("用餐型態:" + type.getText() + " 人數:" + num_people.getText() + " 金額:" + list_money + " 餐點數量:"
				+ passing_list.size());
		System.out.println(
				"風雨:" + rain_special + " 雙人:" + pair_special + " 豪華:" + deluxe_special + " 特餐:" + chef_special);
		for (Entry<String, Integer> id : mMealClassMap.entrySet())
			System.out.println(id.getKey() + " " + id.getValue() + " ");

	}

	public void setType(String consumption_type) {
		type.setText(consumption_type);
	}

	private void updateMealClassMap(String mealClassId, int listItemNum) {
		if (mMealClassMap.containsKey(mealClassId)) {
			mMealClassMap.replace(mealClassId, mMealClassMap.get(mealClassId) + listItemNum);
		} else {
			mMealClassMap.put(mealClassId, listItemNum);
		}
	}

	private void checkMeatClass(ListItem list_meal) {
		String mealMeatClassName = list_meal.getMeal().getMeatClass();
		System.out.println("checkMealClass=" + mealMeatClassName);

		int listItemNum = list_meal.getNumber();

		if (mealMeatClassName.contains("牛肉")) {
			updateMealClassMap("beef", listItemNum);
		}

		if (mealMeatClassName.contains("沙朗")) {
			updateMealClassMap("sharon", listItemNum);
		}

		if (mealMeatClassName.contains("朗心")) {
			updateMealClassMap("sharon_core", listItemNum);
		}

		if (mealMeatClassName.contains("翼板")) {
			updateMealClassMap("wing_plate", listItemNum);
		}
		if (mealMeatClassName.contains("雪花")) {
			updateMealClassMap("snow", listItemNum);
		}

		if (mealMeatClassName.contains("菲力")) {
			updateMealClassMap("philp", listItemNum);
		}

		if (mealMeatClassName.contains("紐約")) {
			updateMealClassMap("newyork", listItemNum);
		}

		if (mealMeatClassName.contains("肋眼")) {
			updateMealClassMap("ribeye", listItemNum);
		}

		if (mealMeatClassName.contains("牛筋")) {
			updateMealClassMap("tendon", listItemNum);
		}

		if (mealMeatClassName.contains("牛小")) {
			updateMealClassMap("ribs", listItemNum);
		}

		if (mealMeatClassName.contains("豬肉")) {
			updateMealClassMap("pork", listItemNum);
		}

		if (mealMeatClassName.contains("豬排")) {
			updateMealClassMap("pork_chop", listItemNum);
		}

		if (mealMeatClassName.contains("松板")) {
			updateMealClassMap("matsusaka", listItemNum);
		}

		if (mealMeatClassName.contains("雞")) {
			updateMealClassMap("chicken", listItemNum);
		}

		if (mealMeatClassName.contains("羊肉")) {
			updateMealClassMap("lamb", listItemNum);
		}

		if (mealMeatClassName.contains("花蝦")) {
			updateMealClassMap("red_shrimp", listItemNum);
		}

		if (mealMeatClassName.contains("鮮蝦")) {
			updateMealClassMap("white_shrimp", listItemNum);
		}

		if (mealMeatClassName.contains("蚵")) {
			updateMealClassMap("oyster", listItemNum);
		}

		if (mealMeatClassName.contains("鮭")) {
			updateMealClassMap("salmon", listItemNum);
		}

		if (mealMeatClassName.contains("鱈")) {
			updateMealClassMap("cod", listItemNum);
		}

		if (mealMeatClassName.contains("鯖")) {
			updateMealClassMap("mackerel", listItemNum);
		}

		if (mealMeatClassName.contains("鯛")) {
			updateMealClassMap("snapper", listItemNum);
		}

		if (mealMeatClassName.contains("沙拉")) {
			updateMealClassMap("salad", listItemNum);
		}

		if (mealMeatClassName.contains("干貝")) {
			updateMealClassMap("scallops", listItemNum);
		}

		if (mealMeatClassName.contains("鮑魚")) {
			updateMealClassMap("abalone", listItemNum);
		}

		if (mealMeatClassName.contains("大可")) {
			updateMealClassMap("huge_oyster", listItemNum);
		}

		if (mealMeatClassName.contains("燻")) {
			updateMealClassMap("smoke_salmon", listItemNum);
		}

		if (mealMeatClassName.contains("鱸")) {
			updateMealClassMap("perch", listItemNum);
		}

		if (mealMeatClassName.contains("大叚")) {
			updateMealClassMap("huge_shrimp", listItemNum);
		}

	}

	private void checkSpecialMeal(ListItem list_meal) {
		String setName = list_meal.getMeal().getSet();
		if (setName.contains("風雨"))
			rain_special++;
		if (setName.contains("雙"))
			pair_special++;
		if (setName.contains("套餐"))
			chef_special++;
		if (setName.contains("豪華"))
			deluxe_special++;
	}

}
