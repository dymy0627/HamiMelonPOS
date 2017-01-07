package application.order;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

import application.MainScene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuController implements Initializable {

	@FXML
	private Label order_time;

	@FXML
	private CheckBox meal1;
	@FXML
	private CheckBox meal2;
	@FXML
	private CheckBox meal3;
	@FXML
	private CheckBox meal4;
	@FXML
	private CheckBox meal5;
	@FXML
	private CheckBox meal6;
	@FXML
	private CheckBox meal7;
	@FXML
	private CheckBox meal8;
	@FXML
	private CheckBox meal9;
	@FXML
	private CheckBox meal10;

	@FXML
	private ComboBox<String> peopleComboBox;

	@FXML
	private Button p_page;
	@FXML
	private Button n_page;

	private Stage stage;
	private Scene scene;
	private Timer timer;

	public static boolean pause;

	private List<String> passing_menu = new ArrayList<String>();
	private List<CheckBox> checkBoxGroup = new ArrayList<CheckBox>();

	private int num_people;
	private int money;
	private int pork;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// timer method
		order_time();

		// populate the fruit combo box with item choices.
		peopleComboBox.getItems().setAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

		// listen for changes to the fruit combo box selection and update the
		// displayed fruit image accordingly.
		peopleComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldSelected, String newSelected) {
				num_people = Integer.parseInt(newSelected);
				System.out.println("num_people: " + num_people);
			}
		});

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

		for (CheckBox checkbox : checkBoxGroup) {
			checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> observable, Boolean wasSelected,
						Boolean isSelected) {
					System.out.println(checkbox.getText() + " checked=" + isSelected);
					if (isSelected) {
						passing_menu.add(checkbox.getText());
					} else {
						passing_menu.remove(passing_menu.indexOf(checkbox.getText()));
					}
				}
			});
		}
	}

	private void order_time() {
		pause = false;
		timer = new Timer();
		// schedule(TimerTask task, long delay, long period)
		timer.schedule(new OrderTimeTask(), 3000, 2000);
	}

	@FXML
	protected void PreviousPageButtonAction(ActionEvent event) throws IOException {
		// def fxml loader
		Parent orderstage = FXMLLoader.load(getClass().getResource("/fxml/OrderStage.fxml"));

		// ref fxml to stage
		stage = MainScene.stage_tmp;
		scene = new Scene(orderstage, 1024, 720);

		// change scene to main scene
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	protected void NextPageButtonAction(ActionEvent event) throws IOException {
		pause = true;

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ListStage.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		ListController controller = fxmlLoader.<ListController>getController();
		controller.setMoney(6666);
		controller.setPeople(num_people);
		controller.setMenuList(passing_menu);

		scene = new Scene(root, 1024, 720);
		stage = MainScene.stage_tmp;
		// change scene to main scene
		stage.setScene(scene);
		stage.show();
	}
}
