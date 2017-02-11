package application.order;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import application.stock.StockBean;
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
	private Label num_people;//來客數
	@FXML
	private Label total_money;//金額
	private int list_money;
	
	@FXML
	private ListView<String> menulist;

	@FXML
	private Label type;//消費型態

	private ListProperty<String> listProperty = new SimpleListProperty<>();
	private List<String> passing_list = new ArrayList<>();
	
	private int s_rain, s_pair, s_deluxe, s_chef;

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
	protected void PrintButtonAction(ActionEvent event) {
		System.out.println("print work");
		System.out.println("用餐型態:" + type.getText() +" 人數:" + num_people.getText() + " 金額:" + list_money + " 餐點數量:" + passing_list.size());
		System.out.println("風雨:" + s_rain + " 雙人:"+ s_pair + " 豪華:" + s_deluxe + " 特餐:" + s_chef);
		
		for(Entry<String, Integer> id : MenuController.MealClassMap.entrySet())
			if(id.getValue() != 0)
				System.out.print(id.getKey() + id.getValue());
		
		System.out.println("");
		
		DataBaseConnection(type.getText(), num_people.getText(), list_money);
	}

	private void DataBaseConnection(String type, String people_num, int list_money) {
		
		new Thread(new Task<Boolean>() {
			
			@Override
			protected Boolean call() throws Exception {
				
				MySqlConnection mySqlConnection = new MySqlConnection();
				mySqlConnection.connectSql();

				//addtoDB(mySqlConnection, type, Integer.parseInt(people_num), Integer.parseInt(list_money));
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
		total_money.setText("總價 " + Integer.toString(money));
		list_money = money;
	}

	public void setPeople(int people) {
		num_people.setText(Integer.toString(people));
	}

	public void setMenuList(List<ListItem> passing_menu) {
		int cursor = 0;
		System.out.println("passing_menu.size() = " + passing_menu.size());
		while (cursor != passing_menu.size()) {
			System.out.println(passing_menu.get(cursor).getMeal().getSet() + passing_menu.get(cursor).getMeal().getName() + passing_menu.get(cursor).getNumber() + passing_menu.get(cursor).getMeal().getPrice());
			String set_name = passing_menu.get(cursor).getMeal().getSet();
			String meal_name = passing_menu.get(cursor).getMeal().getName();
			String meal_number = Integer.toString(passing_menu.get(cursor).getNumber());
			String meal_price = Integer.toString(passing_menu.get(cursor).getNumber()*passing_menu.get(cursor).getMeal().getPrice());
			passing_list.add(set_name+"\t"+ meal_name +"\t" + meal_number +"\t" + meal_price);
			cursor++;
		}

		menulist.itemsProperty().bind(listProperty);
		// ObservableList observableList = FXCollections.observableArrayList();
		// observableList.setAll(passing_list);
		listProperty.set(FXCollections.observableArrayList(passing_list));
	}

	public void setType(String consumption_type) {
		type.setText(consumption_type);
	}

	public void setSpecialNum(int rain_special, int pair_special, int deluxe_special, int chef_special) {
		s_rain = rain_special; s_pair = pair_special; s_deluxe = deluxe_special;  s_chef = chef_special;
		
	}

}
