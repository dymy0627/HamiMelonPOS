package application.order;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import application.MainScene;
import db.MySqlConnection;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MenuController implements Initializable {

	@FXML
	private ComboBox<String> peopleComboBox;
	private int num_people;

	@FXML
	private Button p_page, n_page;

	@FXML
	private VBox menu1, menu2, menu3, menu4;

	private ToggleGroup mTypeGroup;
	private String mConsumptionType = "內用";

	@FXML
	private ToggleButton typeHereButton, typeTakeOutButton, typeDeliverButton;

	private Map<String, CheckBox> checkBoxGroup = new HashMap<>();
	private Map<String, Button> plusButtonGroup = new HashMap<>();
	private Map<String, Label> numberLabelGroup = new HashMap<>();
	private Map<String, Button> minusButtonGroup = new HashMap<>();

	@FXML
	private Label total_money_Label;// 金額
	private int mTotalMoney = 0;

	@FXML
	private ComboBox<String> discount_ComboBox;

	@FXML
	private ListView<String> menulist;
	private Map<String, ListItem> listItemMap = new HashMap<>();
	private ListProperty<String> listProperty = new SimpleListProperty<>();
	private List<String> passing_list = new ArrayList<>();

	private static Map<String, Integer> mMealClassMap = new HashMap<>();

	public static boolean pause;

	private int beef;// 牛肉片
	private int sharon;// 沙朗
	private int sharon_core;// 沙朗心
	private int wing_plate;// 翼板
	private int snow;// 雪花
	private int philp;// 菲力
	private int newyork;// 紐約克
	private int ribeye;// 肋眼
	private int tendon;// 牛筋
	private int ribs;// 牛小排

	private int pork;// 豬肉片
	private int pork_chop;// 豬排
	private int matsusaka;// 松阪

	private int chicken;// 雞腿

	private int lamb;// 羊肉片

	private int red_shrimp;// 花蝦
	private int white_shrimp;// 鮮蝦
	private int oyster;// 蚵
	private int salman;// 鮭魚
	private int cod;// 鱈魚
	private int mackerel;// 鯖魚
	private int snapper;// 鯛魚
	private int salad;// 沙拉
	private int scallops;// 干貝
	private int abalone;// 鮑魚
	private int huge_oyster;// 大蚵
	private int smoke_salmon;// 煄鮭
	private int perch;// 鱸魚
	private int huge_shrimp;// 大蝦

	private int rain_special, pair_special, deluxe_special, chef_special;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		menu_content_intialize();

		// Toggle button listener
		mTypeGroup = new ToggleGroup();
		mTypeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_select, Toggle new_select) {

				if (new_select != null) {
					((ToggleButton) new_select).setSelected(true);
					System.out.println("new_select = " + new_select.toString());
				} else {
					((ToggleButton) old_select).setSelected(true);
					System.out.println("new_select = " + old_select.toString());
					return;
				}

				if (old_select != null) {
					((ToggleButton) old_select).setSelected(false);
					System.out.println("old_select = " + old_select.toString());
				} else {
					System.out.println("old_select == null");
				}

				if (new_select == typeHereButton) {
					mConsumptionType = "內用";
					peopleComboBox.setValue("1");
					peopleComboBox.setDisable(false);
				} else if (new_select == typeTakeOutButton) {
					mConsumptionType = "外帶";
					peopleComboBox.setDisable(true);
				} else if (new_select == typeDeliverButton) {
					mConsumptionType = "外送";
					peopleComboBox.setDisable(true);
				}

				clearAllItem();
			}
		});

		typeHereButton.setToggleGroup(mTypeGroup);
		typeTakeOutButton.setToggleGroup(mTypeGroup);
		typeDeliverButton.setToggleGroup(mTypeGroup);

		typeHereButton.setSelected(true);

		discount_ComboBox.getItems().setAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		discount_ComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> selected, String oldSelected, String newSelected) {
				System.out.println("DiscountCombo newSelected = " + newSelected);
			}
		});

		// populate the fruit combo box with item choices.
		peopleComboBox.getItems().setAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
				"15");

		// listen for changes to the fruit combo box selection and update the
		// displayed fruit image accordingly.
		peopleComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldSelected, String newSelected) {
				num_people = Integer.parseInt(newSelected);
				System.out.println("num_people: " + num_people);
				clearAllItem();
			}
		});

		for (Entry<String, CheckBox> inti : checkBoxGroup.entrySet()) {
			CheckBox checkbox = inti.getValue();
			checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> observable, Boolean wasSelected,
						Boolean isSelected) {

					String currentMealId = checkbox.getId();
					Label currentNumLabel = numberLabelGroup.get(currentMealId);
					System.out.println(currentMealId + " checked = " + isSelected);

					Meal currentMeal = MenuBuilder.getMealById(currentMealId);
					if (isSelected) {
						currentNumLabel.setText(String.valueOf(1));
						mTotalMoney += currentMeal.getPrice();
						System.out.println("+金額 = " + currentMeal.getPrice());
						listItemMap.put(currentMealId, new ListItem(currentMeal));

					} else {
						currentNumLabel.setText(String.valueOf(0));
						int listItemNum = listItemMap.get(currentMealId).getNumber();
						mTotalMoney -= currentMeal.getPrice() * listItemNum;
						System.out.println("-金額 = " + currentMeal.getPrice() * listItemNum);
						if (listItemMap.containsKey(currentMealId)) {
							listItemMap.remove(currentMealId);
						}
					}
					updateMenuList();
				}
			});
		}

		menulist.itemsProperty().bind(listProperty);

	}

	private void clearAllItem() {
		for (Entry<String, CheckBox> inti : checkBoxGroup.entrySet()) {
			inti.getValue().setSelected(false);
			numberLabelGroup.get(inti.getValue().getId()).setText("0");
		}
		listItemMap.clear();
		mTotalMoney = 0;

		rain_special = 0;
		pair_special = 0;
		deluxe_special = 0;
		chef_special = 0;
		mMealClassMap.clear();
	}

	private EventHandler<ActionEvent> plusEventHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {

			String currentMealId = ((Button) e.getSource()).getId();
			Label currentNumLabel = numberLabelGroup.get(currentMealId);

			Meal currentMeal = MenuBuilder.getMealById(currentMealId);
			int currentNum = Integer.parseInt(currentNumLabel.getText());
			if (currentNum == 0) {
				checkBoxGroup.get(currentMealId).setSelected(true);
			} else {
				currentNum = listItemMap.get(currentMealId).getNumber();
				currentNum++;
				currentNumLabel.setText(String.valueOf(currentNum));

				ListItem listItem = new ListItem(currentMeal);
				listItem.setNumber(currentNum);
				listItemMap.replace(currentMealId, listItem);
				System.out.println("+金額 = " + currentMeal.getPrice());
				mTotalMoney += currentMeal.getPrice();

				updateMenuList();
			}
		}
	};

	private void updateMenuList() {

		passing_list.clear();
		for (String id : listItemMap.keySet()) {

			ListItem listItem = listItemMap.get(id);
			String set_name = listItem.getMeal().getSet();
			String meal_name = listItem.getMeal().getName();
			int meal_price = listItem.getMeal().getPrice();
			int item_number = listItem.getNumber();
			int item_price = item_number * meal_price;

			passing_list.add(set_name + "\t" + meal_name + "\t" + item_number + "\t" + item_price);
		}

		listProperty.set(FXCollections.observableArrayList(passing_list));

		total_money_Label.setText("總價 " + Integer.toString(mTotalMoney));
	}

	private EventHandler<ActionEvent> minusEventHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {

			String currentMealId = ((Button) e.getSource()).getId();
			Label currentNumLabel = numberLabelGroup.get(currentMealId);

			Meal currentMeal = MenuBuilder.getMealById(currentMealId);
			int currentNum = Integer.parseInt(currentNumLabel.getText());
			if (currentNum > 0) {
				if (currentNum == 1) {
					checkBoxGroup.get(currentMealId).setSelected(false);
				} else {
					currentNum = listItemMap.get(currentMealId).getNumber();
					currentNum--;
					currentNumLabel.setText(String.valueOf(currentNum));

					ListItem listItem = new ListItem(currentMeal);
					listItem.setNumber(currentNum);
					listItemMap.replace(currentMealId, listItem);
					System.out.println("-金額 = " + currentMeal.getPrice());
					mTotalMoney -= currentMeal.getPrice();

					updateMenuList();
				}
			}
		}
	};

	private void menu_content_intialize() {
		num_people = 1;

		String lastSetName = "";
		Map<String, Meal> mealMap = MenuBuilder.getMenuMap();

		int eachNum = 18;
		System.out.println("eachNum = " + eachNum);

		VBox currentVBox;
		for (int i = 1; i <= mealMap.size(); i++) {
			String id = "meal" + i;
			Meal meal = mealMap.get(id);
			// System.out.println(i + " " +mealMap.size());

			CheckBox chBox = new CheckBox();
			chBox.setId(id);
			checkBoxGroup.put(id, chBox);

			Label nameLabel = new Label(meal.getName());
			nameLabel.setId(id);
			nameLabel.setStyle("-fx-font-size: 18;");

			Button plusButton = new Button("+");
			plusButton.setOnAction(plusEventHandler);
			plusButton.setId(id);
			plusButtonGroup.put(id, plusButton);

			Label numLabel = new Label("0");
			numLabel.setId(id);
			numLabel.setStyle("-fx-font-size: 16");
			numberLabelGroup.put(id, numLabel);

			Button minusButton = new Button("-");
			minusButton.setOnAction(minusEventHandler);
			minusButton.setId(id);
			minusButtonGroup.put(id, minusButton);

			HBox hBox = new HBox(8);
			hBox.setAlignment(Pos.CENTER_LEFT);
			hBox.getChildren().add(chBox);
			hBox.getChildren().add(nameLabel);
			hBox.getChildren().add(plusButton);
			hBox.getChildren().add(numLabel);
			hBox.getChildren().add(minusButton);

			String setName = meal.getSet();
			if (setName.equals("精緻特餐")) {
				currentVBox = menu3;
			} else if (setName.equals("單點")) {
				currentVBox = menu4;
			} else {
				if (i <= eachNum) {
					currentVBox = menu1;
				} else {
					if (i == eachNum + 1) {
						lastSetName = "";
					}
					currentVBox = menu2;
				}
			}

			if (!setName.equals(lastSetName)) {
				lastSetName = setName;
				Label setLabel = new Label(setName);
				setLabel.setStyle("-fx-font-size: 22; -fx-font-weight: bold;"); // -fx-border-color:
				// black;
				currentVBox.getChildren().add(setLabel);
			}

			currentVBox.getChildren().add(hBox);

		}
	}

	@FXML
	protected void PreviousPageButtonAction(ActionEvent event) throws IOException {
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));

		MainScene.changeScene(mainstage);
	}

	@FXML
	protected void PrintButtonAction(ActionEvent event) {
		System.out.println("print work");

		for (String id : listItemMap.keySet()) {

			ListItem listItem = listItemMap.get(id);

			checkMeatClass(listItem);
			checkSpecialMeal(listItem);
		}

		for (Entry<String, Integer> id : mMealClassMap.entrySet())
			System.out.println(id.getKey() + " " + id.getValue() + " ");

		System.out.println("用餐型態:" + mConsumptionType + " 人數:" + num_people + " 金額:" + mTotalMoney + " 餐點數量:"
				+ passing_list.size());
		System.out.println(
				"風雨:" + rain_special + " 雙人:" + pair_special + " 豪華:" + deluxe_special + " 特餐:" + chef_special);

		dataBaseConnection(mConsumptionType, num_people, mTotalMoney);
	}

	private void dataBaseConnection(String type, int people_num, int list_money) {

		new Thread(new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {
				MySqlConnection mySqlConnection = new MySqlConnection();
				mySqlConnection.connectSql();
				mySqlConnection.insertListData(type, people_num, list_money);
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