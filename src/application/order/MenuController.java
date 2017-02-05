package application.order;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController implements Initializable {

	@FXML
	private CheckBox meal1, meal2, meal3, meal4, meal5, meal6, meal7, meal8, meal9, meal10, meal11, meal12, meal13,
			meal14, meal15, meal16, meal17, meal18, meal19, meal20, meal21, meal22, meal23, meal24, meal25, meal26,
			meal27, meal28, meal29, meal30, meal31, meal32, meal33, meal34, meal35, meal36, meal37, meal38, meal39,
			meal40, meal41, meal42, meal43, meal44, meal45, meal46, meal47, meal48, meal49, meal50, meal51, meal52,
			meal53, meal54, meal55, meal56, meal57, meal58, meal59, meal60, meal61, meal62, meal63, meal64, meal65,
			meal66, meal67, meal68, meal69, meal70, meal71, meal72, meal73, meal74;

	@FXML
	private Button pbtn_meal1, pbtn_meal2, pbtn_meal3, pbtn_meal4, pbtn_meal5, pbtn_meal6, pbtn_meal7, pbtn_meal8,
			pbtn_meal9, pbtn_meal10, pbtn_meal11, pbtn_meal12, pbtn_meal13, pbtn_meal14, pbtn_meal15, pbtn_meal16,
			pbtn_meal17, pbtn_meal18, pbtn_meal19, pbtn_meal20, pbtn_meal21, pbtn_meal22, pbtn_meal23, pbtn_meal24,
			pbtn_meal25, pbtn_meal26, pbtn_meal27, pbtn_meal28, pbtn_meal29, pbtn_meal30, pbtn_meal31, pbtn_meal32,
			pbtn_meal33, pbtn_meal34, pbtn_meal35, pbtn_meal36, pbtn_meal37, pbtn_meal38, pbtn_meal39, pbtn_meal40,
			pbtn_meal41, pbtn_meal42, pbtn_meal43, pbtn_meal44, pbtn_meal45, pbtn_meal46, pbtn_meal47, pbtn_meal48,
			pbtn_meal49, pbtn_meal50, pbtn_meal51, pbtn_meal52, pbtn_meal53, pbtn_meal54, pbtn_meal55, pbtn_meal56,
			pbtn_meal57, pbtn_meal58, pbtn_meal59, pbtn_meal60, pbtn_meal61, pbtn_meal62, pbtn_meal63, pbtn_meal64,
			pbtn_meal65, pbtn_meal66, pbtn_meal67, pbtn_meal68, pbtn_meal69, pbtn_meal70, pbtn_meal71, pbtn_meal72,
			pbtn_meal73, pbtn_meal74;

	@FXML
	private Button mbtn_meal1, mbtn_meal2, mbtn_meal3, mbtn_meal4, mbtn_meal5, mbtn_meal6, mbtn_meal7, mbtn_meal8,
			mbtn_meal9, mbtn_meal10, mbtn_meal11, mbtn_meal12, mbtn_meal13, mbtn_meal14, mbtn_meal15, mbtn_meal16,
			mbtn_meal17, mbtn_meal18, mbtn_meal19, mbtn_meal20, mbtn_meal21, mbtn_meal22, mbtn_meal23, mbtn_meal24,
			mbtn_meal25, mbtn_meal26, mbtn_meal27, mbtn_meal28, mbtn_meal29, mbtn_meal30, mbtn_meal31, mbtn_meal32,
			mbtn_meal33, mbtn_meal34, mbtn_meal35, mbtn_meal36, mbtn_meal37, mbtn_meal38, mbtn_meal39, mbtn_meal40,
			mbtn_meal41, mbtn_meal42, mbtn_meal43, mbtn_meal44, mbtn_meal45, mbtn_meal46, mbtn_meal47, mbtn_meal48,
			mbtn_meal49, mbtn_meal50, mbtn_meal51, mbtn_meal52, mbtn_meal53, mbtn_meal54, mbtn_meal55, mbtn_meal56,
			mbtn_meal57, mbtn_meal58, mbtn_meal59, mbtn_meal60, mbtn_meal61, mbtn_meal62, mbtn_meal63, mbtn_meal64,
			mbtn_meal65, mbtn_meal66, mbtn_meal67, mbtn_meal68, mbtn_meal69, mbtn_meal70, mbtn_meal71, mbtn_meal72,
			mbtn_meal73, mbtn_meal74;

	@FXML
	private Label num_meal1, num_meal2, num_meal3, num_meal4, num_meal5, num_meal6, num_meal7, num_meal8, num_meal9,
			num_meal10, num_meal11, num_meal12, num_meal13, num_meal14, num_meal15, num_meal16, num_meal17, num_meal18,
			num_meal19, num_meal20, num_meal21, num_meal22, num_meal23, num_meal24, num_meal25, num_meal26, num_meal27,
			num_meal28, num_meal29, num_meal30, num_meal31, num_meal32, num_meal33, num_meal34, num_meal35, num_meal36,
			num_meal37, num_meal38, num_meal39, num_meal40, num_meal41, num_meal42, num_meal43, num_meal44, num_meal45,
			num_meal46, num_meal47, num_meal48, num_meal49, num_meal50, num_meal51, num_meal52, num_meal53, num_meal54,
			num_meal55, num_meal56, num_meal57, num_meal58, num_meal59, num_meal60, num_meal61, num_meal62, num_meal63,
			num_meal64, num_meal65, num_meal66, num_meal67, num_meal68, num_meal69, num_meal70, num_meal71, num_meal72,
			num_meal73, num_meal74;

	@FXML
	private ComboBox<String> peopleComboBox;

	@FXML
	private Button p_page;
	@FXML
	private Button n_page;

	@FXML
	private VBox menu1;
	@FXML
	private Label type;
	@FXML
	private ToggleButton typeHereButton, typeTakeOutButton, typeDeliverButton;

	private Stage stage;
	private static Stage Pop_Stage;
	private Scene scene;

	public static boolean pause;
	private ToggleGroup mTypeGroup;

	private Map<String, ListItem> listItemMap = new HashMap<>();

	private List<CheckBox> checkBoxGroup = new ArrayList<CheckBox>();
	private List<Button> plusButtonGroup = new ArrayList<Button>();
	private List<Button> minusButtonGroup = new ArrayList<Button>();
	private List<Label> LabelGroup = new ArrayList<Label>();

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

				type.setText(Consumption_type);

				for (CheckBox inti : checkBoxGroup) {
					inti.setSelected(false);
					LabelGroup.get(checkBoxGroup.indexOf(inti)).setText("0");
				}
				listItemMap.clear();

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
				for (CheckBox inti : checkBoxGroup) {
					inti.setSelected(false);
					LabelGroup.get(checkBoxGroup.indexOf(inti)).setText("0");
				}

				listItemMap.clear();
				money = 0;

			}
		});

		menu_content_intialize();

		for (CheckBox checkbox : checkBoxGroup) {
			checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> observable, Boolean wasSelected,
						Boolean isSelected) {

					int currentIndex = checkBoxGroup.indexOf(checkbox);
					Label currentNumLabel = LabelGroup.get(currentIndex);
					String currentMealId = checkbox.getId();
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

		for (Button btn : plusButtonGroup) {
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					int currentButtonIndex = plusButtonGroup.indexOf(btn);
					Label currentNumLabel = LabelGroup.get(currentButtonIndex);
					CheckBox currentCheckBox = checkBoxGroup.get(currentButtonIndex);
					String currentMealId = currentCheckBox.getId();

					int currentNum = Integer.parseInt(currentNumLabel.getText());
					if (currentNum == 0) {
						currentCheckBox.setSelected(true);
					} else {
						currentNum = listItemMap.get(currentMealId).getNumber();
						currentNum++;
						currentNumLabel.setText(String.valueOf(currentNum));

						Meal currentMeal = MenuBuilder.getMealById(currentMealId);
						ListItem listItem = new ListItem(currentMeal);
						listItem.setNumber(currentNum);
						listItemMap.replace(currentMealId, listItem);
						System.out.println("+金額 = " + currentMeal.getPrice());
						money += currentMeal.getPrice();
						System.out.println("總金額= " + money);
					}
				}
			});
		}

		for (Button btn : minusButtonGroup) {
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					int currentButtonIndex = minusButtonGroup.indexOf(btn);
					Label currentNumLabel = LabelGroup.get(currentButtonIndex);
					CheckBox currentCheckBox = checkBoxGroup.get(currentButtonIndex);
					String currentMealId = currentCheckBox.getId();

					int currentNum = Integer.parseInt(currentNumLabel.getText());
					if (currentNum > 0) {
						if (currentNum == 1) {
							currentCheckBox.setSelected(false);
						} else {
							currentNum = listItemMap.get(currentMealId).getNumber();
							currentNum--;
							currentNumLabel.setText(String.valueOf(currentNum));

							Meal currentMeal = MenuBuilder.getMealById(currentMealId);
							ListItem listItem = new ListItem(currentMeal);
							listItem.setNumber(currentNum);
							listItemMap.replace(currentMealId, listItem);
							System.out.println("-金額 = " + currentMeal.getPrice());
							money -= currentMeal.getPrice();
							System.out.println("總金額 = " + money);
						}
					}
				}
			});
		}

	}

	private void menu_content_intialize() {
		num_people = 1;

		checkBoxGroup.add(meal1);
		checkBoxGroup.add(meal2);
		checkBoxGroup.add(meal3);
		checkBoxGroup.add(meal4);
		checkBoxGroup.add(meal5);
		checkBoxGroup.add(meal6);
		checkBoxGroup.add(meal7);
		checkBoxGroup.add(meal8);
		checkBoxGroup.add(meal9);
		checkBoxGroup.add(meal10);
		checkBoxGroup.add(meal11);
		checkBoxGroup.add(meal12);
		checkBoxGroup.add(meal13);
		checkBoxGroup.add(meal14);
		checkBoxGroup.add(meal15);
		checkBoxGroup.add(meal16);
		checkBoxGroup.add(meal17);
		checkBoxGroup.add(meal18);
		checkBoxGroup.add(meal19);
		checkBoxGroup.add(meal20);
		checkBoxGroup.add(meal21);
		checkBoxGroup.add(meal22);
		checkBoxGroup.add(meal23);
		checkBoxGroup.add(meal24);
		checkBoxGroup.add(meal25);
		checkBoxGroup.add(meal26);
		checkBoxGroup.add(meal27);
		checkBoxGroup.add(meal28);
		checkBoxGroup.add(meal29);
		checkBoxGroup.add(meal30);
		checkBoxGroup.add(meal31);
		checkBoxGroup.add(meal32);
		checkBoxGroup.add(meal33);
		checkBoxGroup.add(meal34);
		checkBoxGroup.add(meal35);
		checkBoxGroup.add(meal36);
		checkBoxGroup.add(meal37);
		checkBoxGroup.add(meal38);
		checkBoxGroup.add(meal39);
		checkBoxGroup.add(meal40);
		checkBoxGroup.add(meal41);
		checkBoxGroup.add(meal42);
		checkBoxGroup.add(meal43);
		checkBoxGroup.add(meal44);
		checkBoxGroup.add(meal45);
		checkBoxGroup.add(meal46);
		checkBoxGroup.add(meal47);
		checkBoxGroup.add(meal48);
		checkBoxGroup.add(meal49);
		checkBoxGroup.add(meal50);
		checkBoxGroup.add(meal51);
		checkBoxGroup.add(meal52);
		checkBoxGroup.add(meal53);
		checkBoxGroup.add(meal54);
		checkBoxGroup.add(meal55);
		checkBoxGroup.add(meal56);
		checkBoxGroup.add(meal57);
		checkBoxGroup.add(meal58);
		checkBoxGroup.add(meal59);
		checkBoxGroup.add(meal60);
		checkBoxGroup.add(meal61);
		checkBoxGroup.add(meal62);
		checkBoxGroup.add(meal63);
		checkBoxGroup.add(meal64);
		checkBoxGroup.add(meal65);
		checkBoxGroup.add(meal66);
		checkBoxGroup.add(meal67);
		checkBoxGroup.add(meal68);
		checkBoxGroup.add(meal69);
		checkBoxGroup.add(meal70);
		checkBoxGroup.add(meal71);
		checkBoxGroup.add(meal72);
		checkBoxGroup.add(meal73);
		// checkBoxGroup.add(meal74);

		plusButtonGroup.add(pbtn_meal1);
		plusButtonGroup.add(pbtn_meal2);
		plusButtonGroup.add(pbtn_meal3);
		plusButtonGroup.add(pbtn_meal4);
		plusButtonGroup.add(pbtn_meal5);
		plusButtonGroup.add(pbtn_meal6);
		plusButtonGroup.add(pbtn_meal7);
		plusButtonGroup.add(pbtn_meal8);
		plusButtonGroup.add(pbtn_meal9);
		plusButtonGroup.add(pbtn_meal10);
		plusButtonGroup.add(pbtn_meal11);
		plusButtonGroup.add(pbtn_meal12);
		plusButtonGroup.add(pbtn_meal13);
		plusButtonGroup.add(pbtn_meal14);
		plusButtonGroup.add(pbtn_meal15);
		plusButtonGroup.add(pbtn_meal16);
		plusButtonGroup.add(pbtn_meal17);
		plusButtonGroup.add(pbtn_meal18);
		plusButtonGroup.add(pbtn_meal19);
		plusButtonGroup.add(pbtn_meal20);
		plusButtonGroup.add(pbtn_meal21);
		plusButtonGroup.add(pbtn_meal22);
		plusButtonGroup.add(pbtn_meal23);
		plusButtonGroup.add(pbtn_meal24);
		plusButtonGroup.add(pbtn_meal25);
		plusButtonGroup.add(pbtn_meal26);
		plusButtonGroup.add(pbtn_meal27);
		plusButtonGroup.add(pbtn_meal28);
		plusButtonGroup.add(pbtn_meal29);
		plusButtonGroup.add(pbtn_meal30);
		plusButtonGroup.add(pbtn_meal31);
		plusButtonGroup.add(pbtn_meal32);
		plusButtonGroup.add(pbtn_meal33);
		plusButtonGroup.add(pbtn_meal34);
		plusButtonGroup.add(pbtn_meal35);
		plusButtonGroup.add(pbtn_meal36);
		plusButtonGroup.add(pbtn_meal37);
		plusButtonGroup.add(pbtn_meal38);
		plusButtonGroup.add(pbtn_meal39);
		plusButtonGroup.add(pbtn_meal40);
		plusButtonGroup.add(pbtn_meal41);
		plusButtonGroup.add(pbtn_meal42);
		plusButtonGroup.add(pbtn_meal43);
		plusButtonGroup.add(pbtn_meal44);
		plusButtonGroup.add(pbtn_meal45);
		plusButtonGroup.add(pbtn_meal46);
		plusButtonGroup.add(pbtn_meal47);
		plusButtonGroup.add(pbtn_meal48);
		plusButtonGroup.add(pbtn_meal49);
		plusButtonGroup.add(pbtn_meal50);
		plusButtonGroup.add(pbtn_meal51);
		plusButtonGroup.add(pbtn_meal52);
		plusButtonGroup.add(pbtn_meal53);
		plusButtonGroup.add(pbtn_meal54);
		plusButtonGroup.add(pbtn_meal55);
		plusButtonGroup.add(pbtn_meal56);
		plusButtonGroup.add(pbtn_meal57);
		plusButtonGroup.add(pbtn_meal58);
		plusButtonGroup.add(pbtn_meal59);
		plusButtonGroup.add(pbtn_meal60);
		plusButtonGroup.add(pbtn_meal61);
		plusButtonGroup.add(pbtn_meal62);
		plusButtonGroup.add(pbtn_meal63);
		plusButtonGroup.add(pbtn_meal64);
		plusButtonGroup.add(pbtn_meal65);
		plusButtonGroup.add(pbtn_meal66);
		plusButtonGroup.add(pbtn_meal67);
		plusButtonGroup.add(pbtn_meal68);
		plusButtonGroup.add(pbtn_meal69);
		plusButtonGroup.add(pbtn_meal70);
		plusButtonGroup.add(pbtn_meal71);
		plusButtonGroup.add(pbtn_meal72);
		plusButtonGroup.add(pbtn_meal73);
		// plusButtonGroup.add(pbtn_meal74);

		minusButtonGroup.add(mbtn_meal1);
		minusButtonGroup.add(mbtn_meal2);
		minusButtonGroup.add(mbtn_meal3);
		minusButtonGroup.add(mbtn_meal4);
		minusButtonGroup.add(mbtn_meal5);
		minusButtonGroup.add(mbtn_meal6);
		minusButtonGroup.add(mbtn_meal7);
		minusButtonGroup.add(mbtn_meal8);
		minusButtonGroup.add(mbtn_meal9);
		minusButtonGroup.add(mbtn_meal10);
		minusButtonGroup.add(mbtn_meal11);
		minusButtonGroup.add(mbtn_meal12);
		minusButtonGroup.add(mbtn_meal13);
		minusButtonGroup.add(mbtn_meal14);
		minusButtonGroup.add(mbtn_meal15);
		minusButtonGroup.add(mbtn_meal16);
		minusButtonGroup.add(mbtn_meal17);
		minusButtonGroup.add(mbtn_meal18);
		minusButtonGroup.add(mbtn_meal19);
		minusButtonGroup.add(mbtn_meal20);
		minusButtonGroup.add(mbtn_meal21);
		minusButtonGroup.add(mbtn_meal22);
		minusButtonGroup.add(mbtn_meal23);
		minusButtonGroup.add(mbtn_meal24);
		minusButtonGroup.add(mbtn_meal25);
		minusButtonGroup.add(mbtn_meal26);
		minusButtonGroup.add(mbtn_meal27);
		minusButtonGroup.add(mbtn_meal28);
		minusButtonGroup.add(mbtn_meal29);
		minusButtonGroup.add(mbtn_meal30);
		minusButtonGroup.add(mbtn_meal31);
		minusButtonGroup.add(mbtn_meal32);
		minusButtonGroup.add(mbtn_meal33);
		minusButtonGroup.add(mbtn_meal34);
		minusButtonGroup.add(mbtn_meal35);
		minusButtonGroup.add(mbtn_meal36);
		minusButtonGroup.add(mbtn_meal37);
		minusButtonGroup.add(mbtn_meal38);
		minusButtonGroup.add(mbtn_meal39);
		minusButtonGroup.add(mbtn_meal40);
		minusButtonGroup.add(mbtn_meal41);
		minusButtonGroup.add(mbtn_meal42);
		minusButtonGroup.add(mbtn_meal43);
		minusButtonGroup.add(mbtn_meal44);
		minusButtonGroup.add(mbtn_meal45);
		minusButtonGroup.add(mbtn_meal46);
		minusButtonGroup.add(mbtn_meal47);
		minusButtonGroup.add(mbtn_meal48);
		minusButtonGroup.add(mbtn_meal49);
		minusButtonGroup.add(mbtn_meal50);
		minusButtonGroup.add(mbtn_meal51);
		minusButtonGroup.add(mbtn_meal52);
		minusButtonGroup.add(mbtn_meal53);
		minusButtonGroup.add(mbtn_meal54);
		minusButtonGroup.add(mbtn_meal55);
		minusButtonGroup.add(mbtn_meal56);
		minusButtonGroup.add(mbtn_meal57);
		minusButtonGroup.add(mbtn_meal58);
		minusButtonGroup.add(mbtn_meal59);
		minusButtonGroup.add(mbtn_meal60);
		minusButtonGroup.add(mbtn_meal61);
		minusButtonGroup.add(mbtn_meal62);
		minusButtonGroup.add(mbtn_meal63);
		minusButtonGroup.add(mbtn_meal64);
		minusButtonGroup.add(mbtn_meal65);
		minusButtonGroup.add(mbtn_meal66);
		minusButtonGroup.add(mbtn_meal67);
		minusButtonGroup.add(mbtn_meal68);
		minusButtonGroup.add(mbtn_meal69);
		minusButtonGroup.add(mbtn_meal70);
		minusButtonGroup.add(mbtn_meal71);
		minusButtonGroup.add(mbtn_meal72);
		minusButtonGroup.add(mbtn_meal73);
		// minusButtonGroup.add(mbtn_meal74);

		LabelGroup.add(num_meal1);
		LabelGroup.add(num_meal2);
		LabelGroup.add(num_meal3);
		LabelGroup.add(num_meal4);
		LabelGroup.add(num_meal5);
		LabelGroup.add(num_meal6);
		LabelGroup.add(num_meal7);
		LabelGroup.add(num_meal8);
		LabelGroup.add(num_meal9);
		LabelGroup.add(num_meal10);
		LabelGroup.add(num_meal11);
		LabelGroup.add(num_meal12);
		LabelGroup.add(num_meal13);
		LabelGroup.add(num_meal14);
		LabelGroup.add(num_meal15);
		LabelGroup.add(num_meal16);
		LabelGroup.add(num_meal17);
		LabelGroup.add(num_meal18);
		LabelGroup.add(num_meal19);
		LabelGroup.add(num_meal20);
		LabelGroup.add(num_meal21);
		LabelGroup.add(num_meal22);
		LabelGroup.add(num_meal23);
		LabelGroup.add(num_meal24);
		LabelGroup.add(num_meal25);
		LabelGroup.add(num_meal26);
		LabelGroup.add(num_meal27);
		LabelGroup.add(num_meal28);
		LabelGroup.add(num_meal29);
		LabelGroup.add(num_meal30);
		LabelGroup.add(num_meal31);
		LabelGroup.add(num_meal32);
		LabelGroup.add(num_meal33);
		LabelGroup.add(num_meal34);
		LabelGroup.add(num_meal35);
		LabelGroup.add(num_meal36);
		LabelGroup.add(num_meal37);
		LabelGroup.add(num_meal38);
		LabelGroup.add(num_meal39);
		LabelGroup.add(num_meal40);
		LabelGroup.add(num_meal41);
		LabelGroup.add(num_meal42);
		LabelGroup.add(num_meal43);
		LabelGroup.add(num_meal44);
		LabelGroup.add(num_meal45);
		LabelGroup.add(num_meal46);
		LabelGroup.add(num_meal47);
		LabelGroup.add(num_meal48);
		LabelGroup.add(num_meal49);
		LabelGroup.add(num_meal50);
		LabelGroup.add(num_meal51);
		LabelGroup.add(num_meal52);
		LabelGroup.add(num_meal53);
		LabelGroup.add(num_meal54);
		LabelGroup.add(num_meal55);
		LabelGroup.add(num_meal56);
		LabelGroup.add(num_meal57);
		LabelGroup.add(num_meal58);
		LabelGroup.add(num_meal59);
		LabelGroup.add(num_meal60);
		LabelGroup.add(num_meal61);
		LabelGroup.add(num_meal62);
		LabelGroup.add(num_meal63);
		LabelGroup.add(num_meal64);
		LabelGroup.add(num_meal65);
		LabelGroup.add(num_meal66);
		LabelGroup.add(num_meal67);
		LabelGroup.add(num_meal68);
		LabelGroup.add(num_meal69);
		LabelGroup.add(num_meal70);
		LabelGroup.add(num_meal71);
		LabelGroup.add(num_meal72);
		LabelGroup.add(num_meal73);
		// LabelGroup.add(num_meal74);
	}

	@FXML
	protected void PreviousPageButtonAction(ActionEvent event) throws IOException {
		// def fxml loader
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));

		// ref fxml to stage
		stage = MainScene.stage_tmp;
		scene = new Scene(mainstage, 1024, 720);

		// change scene to main scene
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
		// Pop_Stage = MainScene.stage_tmp;

		// change scene to main scene
		Pop_Stage = new Stage();
		Pop_Stage.setScene(scene);
		Pop_Stage.initModality(Modality.APPLICATION_MODAL);
		Pop_Stage.setTitle("清單");
		Pop_Stage.showAndWait();

		// Pop_Stage.setOnHide(event -> Platform.exit());
		// stage.show();
	}

	public void setClosePop(boolean b) {
		Pop_Stage.close();
	}

}