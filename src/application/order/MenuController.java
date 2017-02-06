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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController implements Initializable {

	@FXML
	private ComboBox<String> peopleComboBox;

	@FXML
	private Button p_page, n_page;

	@FXML
	private VBox menu1, menu2, menu3;

	@FXML
	private Label typeLabel;
	@FXML
	private ToggleButton typeHereButton, typeTakeOutButton, typeDeliverButton;

	private Stage stage;
	private static Stage Pop_Stage;
	private Scene scene;

	public static boolean pause;
	private ToggleGroup mTypeGroup;

	private Map<String, ListItem> listItemMap = new HashMap<>();

	private Map<String, CheckBox> checkBoxGroup = new HashMap<>();
	private Map<String, Button> plusButtonGroup = new HashMap<>();
	private Map<String, Label> numberLabelGroup = new HashMap<>();
	private Map<String, Button> minusButtonGroup = new HashMap<>();

	private String Consumption_type = "內用";
	private int num_people;

	private int money = 0;;
	private int pork;
	private int beef;
	private int chicken;
	private int lamb;
	private int seafood;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		menu_content_intialize();

		// Toggle button listener
		mTypeGroup = new ToggleGroup();
		mTypeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_select, Toggle new_select) {

				((ToggleButton) new_select).setDisable(true);
				System.out.println("new_select = " + new_select.toString());

				if (old_select != null) {
					((ToggleButton) old_select).setDisable(false);
					System.out.println("old_select = " + old_select.toString());
				} else {
					System.out.println("old_select == null");
				}

				if (new_select == typeHereButton) {
					Consumption_type = "內用";
					peopleComboBox.setValue("1");
					peopleComboBox.setDisable(false);
				} else if (new_select == typeTakeOutButton) {
					Consumption_type = "外帶";
					peopleComboBox.setDisable(true);
				} else if (new_select == typeDeliverButton) {
					Consumption_type = "外送";
					peopleComboBox.setDisable(true);
				}

				typeLabel.setText(Consumption_type);
				clearAllItem();
			}
		});

		typeHereButton.setToggleGroup(mTypeGroup);
		typeTakeOutButton.setToggleGroup(mTypeGroup);
		typeDeliverButton.setToggleGroup(mTypeGroup);

		typeHereButton.setSelected(true);

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
						money += currentMeal.getPrice();
						System.out.println("+金額 = " + currentMeal.getPrice());
						listItemMap.put(currentMealId, new ListItem(currentMeal));

					} else {
						currentNumLabel.setText(String.valueOf(0));
						int listItemNum = listItemMap.get(currentMealId).getNumber();
						money -= currentMeal.getPrice() * listItemNum;
						System.out.println("-金額 = " + currentMeal.getPrice() * listItemNum);
						if (listItemMap.containsKey(currentMealId)) {
							listItemMap.remove(currentMealId);
						}
					}
					System.out.println("總金額 = " + money);
				}
			});
		}

	}

	private void clearAllItem() {
		for (Entry<String, CheckBox> inti : checkBoxGroup.entrySet()) {
			inti.getValue().setSelected(false);
			numberLabelGroup.get(inti.getValue().getId()).setText("0");
		}
		listItemMap.clear();
		money = 0;
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
				money += currentMeal.getPrice();
				System.out.println("總金額= " + money);
			}
		}
	};

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
					money -= currentMeal.getPrice();
					System.out.println("總金額 = " + money);
				}
			}
		}
	};

	private void menu_content_intialize() {
		num_people = 1;

		String lastSetName = "";
		Map<String, Meal> mealMap = MenuBuilder.getMenuMap();

		int menuSize = mealMap.size();
		System.out.println("menuSize = " + menuSize);
		int eachNum = (int) ((int) menuSize / 3.0);
		System.out.println("eachNum = " + eachNum);

		VBox currentVBox;
		for (int i = 1; i <= menuSize; i++) {
			String id = "meal" + i;
			Meal meal = mealMap.get(id);
			HBox hBox = new HBox();
			CheckBox chBox = new CheckBox();
			chBox.setId(id);
			checkBoxGroup.put(id, chBox);
			Label nameLabel = new Label(meal.getName() + i);
			nameLabel.setId(id);
			Button plusButton = new Button("+");
			plusButton.setOnAction(plusEventHandler);
			plusButton.setId(id);
			plusButtonGroup.put(id, plusButton);
			Label numLabel = new Label("0");
			numLabel.setId(id);
			numberLabelGroup.put(id, numLabel);
			Button minusButton = new Button("-");
			minusButton.setOnAction(minusEventHandler);
			minusButton.setId(id);
			minusButtonGroup.put(id, minusButton);

			hBox.getChildren().add(chBox);
			hBox.getChildren().add(nameLabel);
			hBox.getChildren().add(plusButton);
			hBox.getChildren().add(numLabel);
			hBox.getChildren().add(minusButton);

			if (i <= eachNum) {
				currentVBox = menu1;
			} else if (i > eachNum && i <= eachNum * 2) {
				if (i == eachNum + 1) {
					lastSetName = "";
				}
				currentVBox = menu2;
			} else {
				if (i == eachNum * 2 + 1) {
					lastSetName = "";
				}
				currentVBox = menu3;
			}

			String setName = meal.getSet();
			if (!setName.equals(lastSetName)) {
				lastSetName = setName;
				Label setLabel = new Label(setName);
				currentVBox.getChildren().add(setLabel);
			}

			currentVBox.getChildren().add(hBox);
		}
	}

	@FXML
	protected void PreviousPageButtonAction(ActionEvent event) throws IOException {
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));
		stage = MainScene.stage_tmp;
		scene = new Scene(mainstage, 1024, 720);
		stage.setScene(scene);
		stage.show();
	}

	private boolean checkIsBeef(String mealName) {
		System.out.println("checkIsBeef " + mealName);
		if (mealName.contains("牛") || mealName.contains("菲力") || mealName.contains("肋眼") || mealName.contains("紐約克")
				|| mealName.contains("雪花") || mealName.contains("牛小排") || mealName.contains("牛筋")
				|| mealName.contains("牛肉片") || mealName.contains("沙朗")) {
			return true;
		}
		return false;
	}

	@FXML
	protected void NextPageButtonAction(ActionEvent event) throws IOException {
		pause = true;

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ListStage.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		ListController controller = fxmlLoader.<ListController>getController();
		controller.setMoney(money);
		controller.setPeople(num_people);
		controller.setType(Consumption_type);
		List<Meal> passing_menu = new ArrayList<Meal>();
		for (String id : listItemMap.keySet()) {
			Meal meal = listItemMap.get(id).getMeal();
			passing_menu.add(meal);
			if (checkIsBeef(meal.getName())) {
				beef += listItemMap.get(id).getNumber();
			}
		}
		controller.setMenuList(passing_menu);
		System.out.println("money " + money);
		System.out.println("beef number " + beef);

		scene = new Scene(root, 800, 600);

		Pop_Stage = new Stage();
		Pop_Stage.setScene(scene);
		Pop_Stage.initModality(Modality.APPLICATION_MODAL);
		Pop_Stage.setTitle("清單");
		Pop_Stage.showAndWait();
	}

	public void setClosePop(boolean b) {
		Pop_Stage.close();
	}

}