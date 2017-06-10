package application.order;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import application.MainScene;
import application.print.PrinterService;
import db.MySqlConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

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

	@FXML
	private Button otherButton;
	@FXML
	private Button plusButton, minusButton;

	private Map<String, Button> menuButtonGroup = new HashMap<>();

	@FXML
	private Label total_money_Label; // 金額
	private int mTotalMoney = 0;

	@FXML
	private Label currentTimeLabel;

	@FXML
	private ComboBox<String> discount_ComboBox;

	@FXML
	private ListView<ListItem> menulist;
	private static ListItem currentListItem;

	private ObservableList<ListItem> mMenuListObservableList = FXCollections.observableArrayList();
	private List<ListItem> passing_list = new ArrayList<>();

	private static Map<String, Integer> mMeatCostMap = new HashMap<>();

	@FXML
	private ProgressIndicator myProgressIndicator;

	public static boolean pause;

	private DateFormat timeFormat = new SimpleDateFormat("hh:mm");
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/M/dd");

	private static boolean timeStart = true;
	private Thread mTimeThread = new Thread(new Runnable() {

		@Override
		public void run() {
			System.out.println("timeStart=" + timeStart);
			while (timeStart) {
				Calendar calendar = Calendar.getInstance();
				String am_pm = (calendar.get(Calendar.HOUR_OF_DAY) < 12) ? "上午" : "下午";
				Platform.runLater(() -> {
					currentTimeLabel.setText(dateFormat.format(calendar.getTime()) + " " + am_pm + " "
							+ timeFormat.format(calendar.getTime()));
				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	});

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		timeStart = true;
		mTimeThread.start();

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

		menulist.setItems(mMenuListObservableList);
		menulist.setCellFactory(new Callback<ListView<ListItem>, ListCell<ListItem>>() {
			@Override
			public ListCell<ListItem> call(ListView<ListItem> parms) {
				return new MenuListViewCell();
			}
		});
		menulist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ListItem>() {
			@Override
			public void changed(ObservableValue<? extends ListItem> observable, ListItem oldValue, ListItem newValue) {
				if (newValue != null) {
					currentListItem = newValue;
					System.out.println("Selected = " + newValue.getMenuBean().getName());
				}
			}
		});

	}

	private EventHandler<ActionEvent> menuButtonEventHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			String currentMealId = ((Button) e.getSource()).getId();
			ListItem listItem = new ListItem(MenuBuilder.getMealById(currentMealId));

			if (currentListItem != null
					&& listItem.getMenuBean().getName() == currentListItem.getMenuBean().getName()) {
				try {
					PlusButtonAction(null);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				passing_list.add(listItem);

				updateMenuList();
				menulist.getSelectionModel().select(passing_list.indexOf(listItem));
			}
		}
	};

	private EventHandler<ActionEvent> alacarteEventHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			String currentMealId = ((Button) e.getSource()).getId();
			MenuBean chosenMeal = MenuBuilder.getMealById(currentMealId);

			MenuBean alacarteMeal = new MenuBean(currentMealId);
			alacarteMeal.setName(chosenMeal.getName() + "(單點)");
			alacarteMeal.setSet("特餐單點");
			alacarteMeal.setPrice(chosenMeal.getPrice() - 30);
			alacarteMeal.setMeatClass(chosenMeal.getMeatClass());

			ListItem listItem = new ListItem(alacarteMeal);
			passing_list.add(listItem);

			updateMenuList();
			menulist.getSelectionModel().select(passing_list.indexOf(listItem));
		}

	};

	private void clearAllItem() {
		passing_list.clear();
		mMeatCostMap.clear();
		currentListItem = null;
		updateMenuList();
	}

	private void updateMenuList() {
		mTotalMoney = 0;
		for (ListItem item : passing_list) {
			mTotalMoney += item.getMenuBean().getPrice() * item.getNumber();
		}
		mMenuListObservableList.setAll(passing_list);

		total_money_Label.setText(Integer.toString(mTotalMoney));
	}

	private void disableMode(boolean disable) {
		typeHereButton.setDisable(disable);
		typeTakeOutButton.setDisable(disable);
		typeDeliverButton.setDisable(disable);

		peopleComboBox.setDisable(disable);

		otherButton.setDisable(disable);
		plusButton.setDisable(disable);
		minusButton.setDisable(disable);

		printButton.setDisable(disable);
		backButton.setDisable(disable);

		menu1.setMouseTransparent(disable);
		menu1.setFocusTraversable(!disable);
		menu2.setMouseTransparent(disable);
		menu2.setFocusTraversable(!disable);
		menu3.setMouseTransparent(disable);
		menu3.setFocusTraversable(!disable);
	}

	private void menu_content_intialize() {
		myProgressIndicator.setVisible(false);
		num_people = 1;

		String lastSetName = "";
		Map<String, MenuBean> mealMap = MenuBuilder.getMenuMap();

		int eachNum = 18;
		// System.out.println("eachNum = " + eachNum);

		URL url = null;
		try {
			url = new File("src/css/button.css").toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		VBox currentVBox;
		FlowPane flowPane = null;
		for (int i = 1; i <= mealMap.size(); i++) {
			String id = "meal" + String.format("%03d", i);

			MenuBean meal = mealMap.get(id);
			String setName = meal.getSet();

			if (setName.equals("風雨餐") || setName.equals("主廚套餐") || setName.equals("多蝦蟹套餐") || setName.equals("海鮮套餐")
					|| setName.equals("靈魂昇華套餐") || setName.equals("潛能解放套餐")) {
				setName = "其他套餐";
			}

			if (setName.equals("精緻特餐")) {
				currentVBox = menu3;
			} else if (setName.equals("單點")) {
				currentVBox = menu2;
			} else if (setName.equals("呷霸套餐")) {
				currentVBox = menu1;
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
			currentVBox.getStylesheets().add(url.toExternalForm());

			Button menuButton = new Button(meal.getName());
			menuButton.setOnAction(menuButtonEventHandler);
			menuButton.setId(id);
			menuButton.setPrefWidth(90);
			menuButton.setPrefHeight(40);
			menuButton.getStyleClass().add("menuButton");
			if (!setName.equals("單點") && !setName.equals("其他套餐")) {
				if (meal.getName().contains("牛") || meal.getName().contains("翼板") || meal.getName().contains("沙朗")
						|| meal.getName().contains("朗心") || meal.getName().contains("雪花")
						|| meal.getName().contains("紐約") || meal.getName().contains("菲力")
						|| meal.getName().contains("肋眼")) {
					menuButton.setStyle("-fx-background-color: #FF5151;");

				} else if (meal.getName().contains("雞")) {
					menuButton.setStyle("-fx-background-color: #FFEEDD; -fx-text-fill: black;");

				} else if (meal.getName().contains("豬") || meal.getName().contains("松阪")) {
					menuButton.setStyle("-fx-background-color: #EAC100; -fx-text-fill: black;");

				} else if (meal.getName().contains("魚") || meal.getName().contains("魷") || meal.getName().contains("蝦")
						|| meal.getName().contains("蟹") || meal.getName().contains("海總")
						|| meal.getName().contains("蚵")) {
					menuButton.setStyle("-fx-background-color: #0066CC;");

				} else if (meal.getName().contains("羊")) {
					menuButton.setStyle("-fx-background-color: #9F5000;");

				}
			}
			menuButtonGroup.put(id, menuButton);

			if (!setName.equals(lastSetName)) {
				lastSetName = setName;
				Label setLabel = new Label(setName);
				setLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;"); // -fx-border-color:
				setLabel.setPadding(new Insets(0, 0, -16, 0));
				// black;
				currentVBox.getChildren().add(setLabel);
				currentVBox.setAlignment(Pos.TOP_LEFT);
				flowPane = new FlowPane(4, 6);
				flowPane.setAlignment(Pos.CENTER_LEFT);
				currentVBox.getChildren().add(flowPane);
			}

			if (setName.equals("精緻特餐")) {
				HBox hBox = new HBox(0);
				hBox.setAlignment(Pos.CENTER_LEFT);
				hBox.getChildren().add(menuButton);

				Button alacarteButton = new Button("單點");
				alacarteButton.setOnAction(alacarteEventHandler);
				alacarteButton.setId(id);
				alacarteButton.getStyleClass().add("singleButton");
				alacarteButton.setPrefHeight(40);

				hBox.getChildren().add(alacarteButton);

				flowPane.getChildren().add(hBox);
			} else {
				flowPane.getChildren().add(menuButton);
			}
		}
	}

	@FXML
	protected void PlusButtonAction(ActionEvent event) throws IOException {
		if (currentListItem != null) {
			int currentNum = currentListItem.getNumber();
			currentNum++;
			currentListItem.setNumber(currentNum);
			int index = passing_list.indexOf(currentListItem);
			passing_list.remove(currentListItem);
			passing_list.add(index, currentListItem);
			updateMenuList();
			menulist.getSelectionModel().select(passing_list.indexOf(currentListItem));
		}
	}

	@FXML
	protected void MinusButtonAction(ActionEvent event) throws IOException {
		if (currentListItem != null) {
			int currentNum = currentListItem.getNumber();
			if (currentNum > 1) {
				currentNum--;
				currentListItem.setNumber(currentNum);
				int index = passing_list.indexOf(currentListItem);
				passing_list.remove(currentListItem);
				passing_list.add(index, currentListItem);
				updateMenuList();
				menulist.getSelectionModel().select(passing_list.indexOf(currentListItem));
			} else {
				passing_list.remove(currentListItem);
				currentListItem = null;
				updateMenuList();
				int lastIndex = passing_list.size();
				if (lastIndex != 0) {
					lastIndex -= 1;
					menulist.getSelectionModel().select(lastIndex);
				}
			}
		}
	}

	@FXML
	protected void OtherButtonAction(ActionEvent event) throws IOException {

		if (currentListItem != null) {
			MenuBean menu = currentListItem.getMenuBean();
			if (menu.getSet().equals("精緻特餐") && !menu.getName().contains("(加點)")) {
				String otherMealId = menu.getId();
				MenuBean otherMeal = new MenuBean(otherMealId);

				otherMeal.setSet("精緻特餐");
				otherMeal.setName(menu.getName() + "(加點)");
				otherMeal.setPrice(menu.getPrice() + 150);
				otherMeal.setMeatClass(menu.getMeatClass() + "_16*3_19");
				ListItem newListItem = new ListItem(otherMeal);

				int index = passing_list.indexOf(currentListItem);
				passing_list.remove(currentListItem);
				passing_list.add(index, newListItem);
				updateMenuList();
				menulist.getSelectionModel().select(index);
			}
		}
	}

	@FXML
	protected void PreviousPageButtonAction(ActionEvent event) throws IOException {
		timeStart = false;
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));
		MainScene.changeScene(mainstage);
	}

	@FXML
	protected void PrintButtonAction(ActionEvent event) {

		if (passing_list.size() != 0 && mTotalMoney != 0) {

			myProgressIndicator.setVisible(false);
			disableMode(true);
			String ListContent = "";

			String meals = "";
			for (ListItem listItem : passing_list) {
				MenuBean menuBean = listItem.getMenuBean();

				for (int i = 0; i < listItem.getNumber(); i++) {
					calMeatCost(menuBean.getMeatClass());
					meals += menuBean.getId() + ",";
					ListContent += menuBean.getName() + "\t" + listItem.getNumber()+ "\n";
				}
			}

			System.out.println("用餐型態:" + mConsumptionType + " 人數:" + num_people + " 金額:" + mTotalMoney);
			ListContent += mConsumptionType + "\t" + String.valueOf(num_people) + "\t" + String.valueOf(mTotalMoney) + "\n";
			dataBaseConnection(mConsumptionType, num_people, mTotalMoney, meals, ListContent);			

		} else {
			System.out.println("print failed, list size = " + passing_list.size());
		}
	}

	// TODO
	private void printWork(String ListContent) {
		System.out.println("print work");
		PrinterService printerService = new PrinterService();
		
		System.out.println(printerService.getPrinters());
 
		//printerService.printBytes("BP-T3", ListContent.getBytes());
		printerService.printBytes("BP-T3", ListContent);
	
	}

	private void dataBaseConnection(String type, int peopleNum, int totalMoney, String meals, String ListContent) {
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
				System.out.println("orderList Insert Done!");
				myProgressIndicator.setVisible(false);
				disableMode(false);

				clearAllItem();
				printWork(ListContent);
			}

			@Override
			protected void failed() {
				super.failed();
				System.out.println("orderList Insert Failed!");
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

}