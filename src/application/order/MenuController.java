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
import javafx.geometry.Pos;
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
	private VBox menu1, menu2, menu3, menu4;

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

	private int money = 0;
	
	private Map<String, Integer> MealClassMap = new HashMap<>();
	private int beef;//牛肉片
	private int sharon;//沙朗
	private int sharon_core;//沙朗心
	private int wing_plate;//翼板
	private int snow;//雪花
	private int philp;//菲力
	private int newyork;//紐約克
	private int ribeye;//肋眼
	private int tendon;//牛筋
	private int ribs;//牛小排
	
	private int pork;//豬肉片
	private int pork_chop;//豬排
	private int matsusaka;//松阪
	
	private int chicken;//雞腿
	
	private int lamb;//羊肉片
	
	private int red_shrimp;//花蝦
	private int white_shrimp;//鮮蝦
	private int oyster;//蚵
	private int salman;//鮭魚
	private int cod;//鱈魚
	private int mackerel;//鯖魚
	private int snapper;//鯛魚
	private int salad;//沙拉
	private int scallops;//干貝
	private int abalone;//鮑魚
	private int huge_oyster;//大蚵
	private int smoke_salmon;//煄鮭
	private int perch;//鱸魚
	private int huge_shrimp;//大蝦
	
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
		
		for(Entry<String, Integer> id:MealClassMap.entrySet()){
			MealClassMap.replace(id.getKey(), 0);
		}
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

		int eachNum = 18;
		System.out.println("eachNum = " + eachNum);

		VBox currentVBox;
		for (int i = 1; i <= mealMap.size(); i++) {
			String id = "meal" + i;
			Meal meal = mealMap.get(id);
			//System.out.println(i + " " +mealMap.size());

			CheckBox chBox = new CheckBox();
			chBox.setId(id);
			checkBoxGroup.put(id, chBox);

			Label nameLabel = new Label(meal.getName());
			nameLabel.setId(id);
			nameLabel.setStyle("-fx-font-size: 18");

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
				setLabel.setStyle("-fx-border-color: black");
				setLabel.setStyle("-fx-font-size: 22");
				currentVBox.getChildren().add(setLabel);
			}

			currentVBox.getChildren().add(hBox);
			
			MealClassMap.put("beef", 0); MealClassMap.put("sharon", 0); MealClassMap.put("saron_core", 0);
			MealClassMap.put("wing_plate", 0); MealClassMap.put("snow", 0); MealClassMap.put("philp", 0);
			MealClassMap.put("newyork", 0); MealClassMap.put("ribeye", 0); MealClassMap.put("tendon", 0);
			MealClassMap.put("ribs", 0); MealClassMap.put("pork", 0); MealClassMap.put("pork_chop", 0);
			MealClassMap.put("matsusaka", 0); MealClassMap.put("chicken", 0); MealClassMap.put("lamb", 0);
			MealClassMap.put("red_shrimp", 0); MealClassMap.put("white_shrimp", 0); MealClassMap.put("oyster", 0);
			MealClassMap.put("salmon", 0); MealClassMap.put("cod", 0); MealClassMap.put("mackerel", 0);
			MealClassMap.put("snapper", 0); MealClassMap.put("salad", 0); MealClassMap.put("scallops", 0);
			MealClassMap.put("abalone", 0); MealClassMap.put("huge_oyster", 0); MealClassMap.put("smoke_salmon", 0);
			MealClassMap.put("perch", 0); MealClassMap.put("huge_shrimp", 0);
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

	private void checkMeatClass(String id,String mealClass) {
		System.out.println("checkMealClass " + mealClass);
		
		if (mealClass.contains("牛肉"))
			MealClassMap.replace("beef", MealClassMap.get("beef")+listItemMap.get(id).getNumber());
			//beef += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("沙朗"))
			MealClassMap.replace("sharon", MealClassMap.get("sharon")+listItemMap.get(id).getNumber());
			//sharon += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("朗心"))
			MealClassMap.replace("sharon_core", MealClassMap.get("sharon_core")+listItemMap.get(id).getNumber());
			//sharon_core += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("翼板"))
			MealClassMap.replace("wing_plate", MealClassMap.get("wing_plate")+listItemMap.get(id).getNumber());
			//wing_plate += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("雪花"))
			MealClassMap.replace("snow", MealClassMap.get("snow")+listItemMap.get(id).getNumber());
			//snow += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("菲力"))
			MealClassMap.replace("philp", MealClassMap.get("philp")+listItemMap.get(id).getNumber());
			//philp += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("紐約"))
			MealClassMap.replace("newyork", MealClassMap.get("newyork")+listItemMap.get(id).getNumber());
			//newyork += listItemMap.get(id).getNumber();

		if (mealClass.contains("肋眼"))
			MealClassMap.replace("ribeye", MealClassMap.get("ribeye")+listItemMap.get(id).getNumber());
			//ribeye += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("牛筋"))
			MealClassMap.replace("tendon", MealClassMap.get("tendon")+listItemMap.get(id).getNumber());
			//tendon += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("牛小"))
			MealClassMap.replace("ribs", MealClassMap.get("ribs")+listItemMap.get(id).getNumber());
			//ribs += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("豬肉"))
			MealClassMap.replace("pork", MealClassMap.get("pork")+listItemMap.get(id).getNumber());
			//pork += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("豬排"))
			MealClassMap.replace("pork_chop", MealClassMap.get("pork_chop")+listItemMap.get(id).getNumber());
			//pork_chop += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("松板"))
			MealClassMap.replace("matsusaka", MealClassMap.get("matsusaka")+listItemMap.get(id).getNumber());
			//matsusaka += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("雞"))
			MealClassMap.replace("chicken", MealClassMap.get("chicken")+listItemMap.get(id).getNumber());
			//chicken += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("羊肉"))
			MealClassMap.replace("lamb", MealClassMap.get("lamb")+listItemMap.get(id).getNumber());
			//lamb += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("花蝦"))
			MealClassMap.replace("red_shrimp", MealClassMap.get("red_shrimp")+listItemMap.get(id).getNumber());
			//red_shrimp += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("鮮蝦"))
			MealClassMap.replace("white_shrimp", MealClassMap.get("white_shrimp")+listItemMap.get(id).getNumber());
			//white_shrimp += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("蚵"))
			MealClassMap.replace("oyster", MealClassMap.get("oyster")+listItemMap.get(id).getNumber());
			//oyster += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("鮭"))
			MealClassMap.replace("salmon", MealClassMap.get("salmon")+listItemMap.get(id).getNumber());
			//salman += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("鱈"))
			MealClassMap.replace("cod", MealClassMap.get("cod")+listItemMap.get(id).getNumber());
			//cod += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("鯖"))
			MealClassMap.replace("mackerel", MealClassMap.get("mackerel")+listItemMap.get(id).getNumber());
			//mackerel += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("鯛"))
			MealClassMap.replace("snapper", MealClassMap.get("snapper")+listItemMap.get(id).getNumber());
			//snapper += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("沙拉"))
			MealClassMap.replace("salad", MealClassMap.get("salad")+listItemMap.get(id).getNumber());
			//salad += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("干貝"))
			MealClassMap.replace("scallops", MealClassMap.get("scallops")+listItemMap.get(id).getNumber());
			//scallops += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("鮑魚"))
			MealClassMap.replace("abalone", MealClassMap.get("abalone")+listItemMap.get(id).getNumber());
			//abalone += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("大可"))
			MealClassMap.replace("huge_oyster", MealClassMap.get("huge_oyster")+listItemMap.get(id).getNumber());
			//huge_oyster += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("燻"))
			MealClassMap.replace("smoke_salmon", MealClassMap.get("smoke_salmon")+listItemMap.get(id).getNumber());
			//smoke_salmon += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("鱸"))
			MealClassMap.replace("perch", MealClassMap.get("perch")+listItemMap.get(id).getNumber());
			//perch += listItemMap.get(id).getNumber();
		
		if (mealClass.contains("大叚"))
			MealClassMap.replace("huge_shrimp", MealClassMap.get("huge_shrimp")+listItemMap.get(id).getNumber());
			//huge_shrimp += listItemMap.get(id).getNumber();
		
		
		
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
		List<ListItem> passing_menu = new ArrayList<ListItem>();
		for (String id : listItemMap.keySet()) {
			ListItem list_meal = listItemMap.get(id);
			passing_menu.add(list_meal);
			
			checkMeatClass(id,list_meal.getMeal().getMeatClass());
			
		}
		controller.setMenuList(passing_menu);
		System.out.println("money " + money);
		for(Entry<String,Integer> id:MealClassMap.entrySet()){
			if(id.getValue() != 0)
				System.out.print(id.getKey() + " " + id.getValue() + " ");
		}
		System.out.println();

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