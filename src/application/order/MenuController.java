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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MenuController implements Initializable {

	@FXML
	private ComboBox<String> peopleComboBox;
	private int num_people;

	@FXML
	private Button backButton, printButton;

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

	Button alacarteButton = new Button();
	Button otherButton = new Button();
	int alacarteNum = 0;
	int otherNum = 0;

	@FXML
	private Label total_money_Label; // 金額
	private int mTotalMoney = 0;

	@FXML
	private ComboBox<String> discount_ComboBox;

	@FXML
	private ListView<String> menulist;
	private Map<String, ListItem> listItemMap = new HashMap<>();
	private ListProperty<String> listProperty = new SimpleListProperty<>();
	private List<String> passing_list = new ArrayList<>();

	private static Map<String, Integer> mMeatCostMap = new HashMap<>();

	@FXML
	private ProgressIndicator myProgressIndicator;

	public static boolean pause;

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
		discount_ComboBox.setDisable(true);

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

					MenuBean currentMeal = MenuBuilder.getMealById(currentMealId);
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

	private EventHandler<ActionEvent> plusEventHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {

			String currentMealId = ((Button) e.getSource()).getId();//
			Label currentNumLabel = numberLabelGroup.get(currentMealId);

			MenuBean currentMeal = MenuBuilder.getMealById(currentMealId);//
			int currentNum = Integer.parseInt(currentNumLabel.getText());
			if (currentNum == 0) {
				checkBoxGroup.get(currentMealId).setSelected(true);
			} else {
				currentNum = listItemMap.get(currentMealId).getNumber();//
				currentNum++;
				currentNumLabel.setText(String.valueOf(currentNum));

				ListItem listItem = new ListItem(currentMeal);
				listItem.setNumber(currentNum);//
				listItemMap.replace(currentMealId, listItem);//
				System.out.println("+金額 = " + currentMeal.getPrice());
				mTotalMoney += currentMeal.getPrice();

				updateMenuList();
			}
		}
	};

	private EventHandler<ActionEvent> minusEventHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {

			String currentMealId = ((Button) e.getSource()).getId();
			Label currentNumLabel = numberLabelGroup.get(currentMealId);

			MenuBean currentMeal = MenuBuilder.getMealById(currentMealId);
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

	private EventHandler<ActionEvent> alacarteEventHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {

			alacarteNum++;
			if (alacarteNum > 0) {
				String currentMealId = ((Button) e.getSource()).getId();

				MenuBean alacarteMeal = new MenuBean(currentMealId + "a");
				MenuBean chosenMeal = MenuBuilder.getMealById(currentMealId);
				alacarteMeal.setName(chosenMeal.getName());
				alacarteMeal.setSet("套餐單點");
				alacarteMeal.setPrice(chosenMeal.getPrice() - 30);
				alacarteMeal.setMeatClass(chosenMeal.getMeatClass());

				ListItem listItem = new ListItem(alacarteMeal);

				if (listItemMap.containsKey(currentMealId + "a")) {
					int getCurrentNum = listItemMap.get(currentMealId + "a").getNumber();
					listItem.setNumber(++getCurrentNum);
					listItemMap.replace(currentMealId + "a", listItem);
				} else {
					alacarteNum = 0;
					listItem.setNumber(++alacarteNum);
					listItemMap.put(currentMealId + "a", listItem);
				}

				mTotalMoney += alacarteMeal.getPrice();

				updateMenuList();
			}
		}

	};

	private EventHandler<ActionEvent> otherEventHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			otherNum++;
			if (otherNum > 0) {
				String currentMealId = ((Button) e.getSource()).getId();

				MenuBean otherMeal = new MenuBean(currentMealId + "o");
				MenuBean chosenMeal = MenuBuilder.getMealById(currentMealId);
				otherMeal.setName(chosenMeal.getName());
				otherMeal.setSet("套餐加點");
				otherMeal.setPrice(chosenMeal.getPrice() + 150);
				otherMeal.setMeatClass(chosenMeal.getMeatClass() + "鱈鮮蝦");

				ListItem listItem = new ListItem(otherMeal);

				if (listItemMap.containsKey(currentMealId + "o")) {
					int getCurrentNum = listItemMap.get(currentMealId + "o").getNumber();
					listItem.setNumber(++getCurrentNum);
					listItemMap.replace(currentMealId + "o", listItem);
				} else {
					otherNum = 0;
					listItem.setNumber(++otherNum);
					listItemMap.put(currentMealId + "o", listItem);
				}

				mTotalMoney += otherMeal.getPrice();

				updateMenuList();
			}
		}
	};

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
		mMeatCostMap.clear();
	}

	private void updateMenuList() {
		passing_list.clear();
		for (String id : listItemMap.keySet()) {

			ListItem listItem = listItemMap.get(id);
			String set_name = listItem.getMenuBean().getSet();
			String meal_name = listItem.getMenuBean().getName();
			int meal_price = listItem.getMenuBean().getPrice();
			int item_number = listItem.getNumber();
			int item_price = item_number * meal_price;

			passing_list.add(set_name + "\t" + meal_name + "\t" + item_number + "\t" + item_price);
		}

		listProperty.set(FXCollections.observableArrayList(passing_list));

		total_money_Label.setText("總價  " + Integer.toString(mTotalMoney));
	}

	private void disableMode(boolean disable) {
		peopleComboBox.setDisable(disable);
		printButton.setDisable(disable);
		backButton.setDisable(disable);
		menu1.setMouseTransparent(disable);
		menu1.setFocusTraversable(!disable);
		menu2.setMouseTransparent(disable);
		menu2.setFocusTraversable(!disable);
		menu3.setMouseTransparent(disable);
		menu3.setFocusTraversable(!disable);
		menu4.setMouseTransparent(disable);
		menu4.setFocusTraversable(!disable);
	}

	private void menu_content_intialize() {
		myProgressIndicator.setVisible(false);
		num_people = 1;

		String lastSetName = "";
		Map<String, MenuBean> mealMap = MenuBuilder.getMenuMap();

		int eachNum = 18;
		// System.out.println("eachNum = " + eachNum);

		VBox currentVBox;
		for (int i = 1; i <= mealMap.size(); i++) {
			String id = "meal" + String.format("%03d", i);
			MenuBean meal = mealMap.get(id);
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

			if (meal.getSet().equals("精緻特餐")) {

				alacarteButton = new Button("單點");
				alacarteButton.setOnAction(alacarteEventHandler);
				alacarteButton.setId(id);
				setAlacarteCancelEvent(id);

				otherButton = new Button("加點");
				otherButton.setOnAction(otherEventHandler);
				otherButton.setId(id);
				setOtherCancelEvent(id);
			}

			HBox hBox = new HBox(8);
			hBox.setAlignment(Pos.CENTER_LEFT);
			hBox.getChildren().add(chBox);
			hBox.getChildren().add(nameLabel);
			hBox.getChildren().add(plusButton);
			hBox.getChildren().add(numLabel);
			hBox.getChildren().add(minusButton);
			if (meal.getSet().equals("精緻特餐")) {
				hBox.getChildren().add(alacarteButton);
				hBox.getChildren().add(otherButton);
			}

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

	private void setOtherCancelEvent(String ID) {
		Timeline timer = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if (listItemMap.containsKey(ID + "o")) {
					int getCurrentNum = 0;
					listItemMap.get(ID + "o").setNumber(getCurrentNum);
					listItemMap.remove(ID + "o");
					otherNum = -1;
					updateMenuList();
				}
			}
		}));
		otherButton.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
					timer.play();
				} else {
					timer.stop();
				}
			}
		});

	}

	private void setAlacarteCancelEvent(String ID) {

		Timeline timer = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if (listItemMap.containsKey(ID + "a")) {
					int getCurrentNum = 0;
					listItemMap.get(ID + "a").setNumber(getCurrentNum);
					listItemMap.remove(ID + "a");
					alacarteNum = -1;
					updateMenuList();
				}
			}
		}));
		alacarteButton.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
					timer.play();
				} else {
					timer.stop();
				}
			}
		});
	}

	@FXML
	protected void PreviousPageButtonAction(ActionEvent event) throws IOException {
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));

		MainScene.changeScene(mainstage);
	}

	@FXML
	protected void PrintButtonAction(ActionEvent event) {
		String meals = "";
		for (String id : listItemMap.keySet()) {

			ListItem listItem = listItemMap.get(id);
			MenuBean menuBean = listItem.getMenuBean();

			for (int i = 0; i < listItem.getNumber(); i++) {
				checkSpecialMeal(menuBean.getSet());
				calMeatCost(menuBean.getMeatClass());
				meals += menuBean.getId() + ",";
			}
		}

		System.out.println(
				"風雨:" + rain_special + " 雙人:" + pair_special + " 豪華:" + deluxe_special + " 特餐:" + chef_special);

		if (mTotalMoney > 0)
			dataBaseConnection(mConsumptionType, num_people, mTotalMoney, meals);
	}

	private void dataBaseConnection(String type, int peopleNum, int totalMoney, String meals) {

		System.out.println("用餐型態:" + type + " 人數:" + peopleNum + " 金額:" + totalMoney);

		myProgressIndicator.setVisible(false);
		disableMode(true);
		new Thread(new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {
				myProgressIndicator.setVisible(true);
				MySqlConnection mySqlConnection = new MySqlConnection();
				mySqlConnection.connectSql();
				System.out.println("-- total Meat Cost --");
				for (Entry<String, Integer> id : mMeatCostMap.entrySet()) {
					String meatId = id.getKey();
					int cost = id.getValue();
					int reserve = mySqlConnection.getStockReserveById(meatId) - cost;
					mySqlConnection.updateStockReserveById(meatId, reserve);
					System.out.println(MainScene.meatsClassHashMap.get(meatId) + "=" + reserve);
				}
				System.out.println("---------------------");
				mySqlConnection.insertOrderList(type, peopleNum, totalMoney, meals);
				mySqlConnection.disconnectSql();
				return true;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				System.out.println("Menu Insert Done!");
				myProgressIndicator.setVisible(false);
				disableMode(false);

				clearAllItem();

				System.out.println("print work");
			}

			@Override
			protected void failed() {
				super.failed();
				System.out.println("Menu Insert Failed!");
				myProgressIndicator.setVisible(false);
				disableMode(false);

				clearAllItem();
			}
		}).start();

	}

	private void updateMeatCostMap(String meatId, int meatCost) {
		if (mMeatCostMap.containsKey(meatId)) {
			mMeatCostMap.replace(meatId, mMeatCostMap.get(meatId) + meatCost);
		} else {
			mMeatCostMap.put(meatId, meatCost);
		}
	}

	private void calMeatCost(String meatClass) {
		System.out.println("meatClass = " + meatClass);

		String[] meats = meatClass.split("_");
		for (String meatId : meats) {
			int meatCost = 1;
			if (meatId.contains("*")) {
				meatCost = Integer.parseInt(meatId.substring(meatId.indexOf("*") + 1));
				meatId = meatId.substring(0, meatId.indexOf("*"));
			}
			System.out.println("meat cost = " + meatId + "*" + meatCost);

			if (MainScene.meatsClassHashMap.containsKey(meatId)) {
				updateMeatCostMap(meatId, meatCost);
			}
		}
	}

	private void checkSpecialMeal(String setName) {
		System.out.println("setName = " + setName);
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