package application.order;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
	private Label num_people;
	@FXML
	private Label total_money;

	@FXML
	private ListView<Meal> menulist;

	@FXML
	private Label type;

	private ListProperty<Meal> listProperty = new SimpleListProperty<>();
	private List<Meal> passing_list = new ArrayList<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// populate the fruit combo box with item choices.
		DiscountCombo.getItems().setAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

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
	protected void NextPageButtonAction(ActionEvent event) {
		System.out.println("print work");
	}

	public void setMoney(int money) {
		total_money.setText("總價 " + Integer.toString(money));
	}

	public void setPeople(int people) {
		num_people.setText(Integer.toString(people));
	}

	public void setMenuList(List<Meal> passing_menu) {
		int cursor = 0;
		System.out.println("passing_menu.size() = " + passing_menu.size());
		while (cursor != passing_menu.size()) {
			System.out.println(passing_menu.get(cursor));
			passing_list.add(passing_menu.get(cursor++));
		}

		menulist.itemsProperty().bind(listProperty);
		// ObservableList observableList = FXCollections.observableArrayList();
		// observableList.setAll(passing_list);
		listProperty.set(FXCollections.observableArrayList(passing_list));
	}

	public void setType(String consumption_type) {
		type.setText(consumption_type);
	}

}
