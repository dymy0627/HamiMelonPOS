package application.stock;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.MainScene;
import db.MySqlConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class StockController implements Initializable, StockListViewListener {
	@FXML
	private ListView<StockBean> stockListView;

	@FXML
	private ComboBox<String> manufacturerComboBox;

	@FXML
	private TextField stockNameTextField;
	@FXML
	private TextField amountTextField;

	private List<StockBean> stockList = new ArrayList<StockBean>();

	private ObservableList<StockBean> stockObservableList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stockListView.setItems(stockObservableList);
		stockListView.setCellFactory(new Callback<ListView<StockBean>, ListCell<StockBean>>() {
			@Override
			public ListCell<StockBean> call(ListView<StockBean> parms) {
				return new StockListViewCell(StockController.this);
			}
		});
		stockListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StockBean>() {
			@Override
			public void changed(ObservableValue<? extends StockBean> observable, StockBean oldValue,
					StockBean newValue) {
				if (observable.getValue() != null) {
					updateListView();
				}
			}
		});

		MySqlConnection mySqlConnection = new MySqlConnection();
		mySqlConnection.connectSql();
		// stockObservableList.setAll(mySqlConnection.selectAllStock());
		manufacturerComboBox.getItems().setAll(mySqlConnection.selectManufacturer());
		mySqlConnection.disconnectSql();
		manufacturerComboBox.getSelectionModel().selectFirst();
	}

	private void updateListView() {
		stockListView.setItems(null);
		stockListView.setItems(stockObservableList);
	}

	@Override
	public void delete() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				StockBean selectedStock = stockListView.getSelectionModel().getSelectedItem();
				stockListView.getSelectionModel().clearSelection();
				stockList.remove(selectedStock);
				stockObservableList.remove(selectedStock);
				System.out.println("remove " + selectedStock.toString());
			}
		});
	}

	private boolean validAmount(int amountValue) {
		return amountValue > 0;
	}

	private void clearTextField() {
		stockNameTextField.setText("");
		amountTextField.setText("");
	}

	@FXML
	protected void AddButtonAction(ActionEvent event) {
		String manufacturerText = manufacturerComboBox.getSelectionModel().getSelectedItem();
		String stockNameText = stockNameTextField.getText();
		int amountValue = 0;
		try {
			amountValue = Integer.parseInt(amountTextField.getText());
		} catch (NumberFormatException e) {
			System.out.println("Amount cannot be String.");
		}
		if (manufacturerText != null && !stockNameText.isEmpty() && validAmount(amountValue)) {
			clearTextField();
			StockBean stock = new StockBean();
			stock.setManufacturer(manufacturerText);
			stock.setName(stockNameText);
			stock.setAmount(amountValue);
			stockList.add(stock);
			stockObservableList.add(stock);
			System.out.println("add " + stock.toString());
		}
	}

	@FXML
	protected void SendButtonAction(ActionEvent event) {
		if (!stockList.isEmpty()) {
			stockListView.getItems().clear();
			clearTextField();

			MySqlConnection mySqlConnection = new MySqlConnection();
			mySqlConnection.connectSql();
			for (StockBean stock : stockList) {
				mySqlConnection.insertShippingData(//
						stock.getManufacturer(), // Manufacturer
						stock.getName(), // Name
						stock.getAmount() // Amount
				);
			}
			mySqlConnection.disconnectSql();
			stockList.clear();
		}
	}

	@FXML
	protected void LeaveButtonAction(ActionEvent event) throws IOException {
		// def fxml loader
		Parent stockStage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));

		// ref fxml to stage
		Scene scene = new Scene(stockStage, 1024, 720);
		Stage stage = MainScene.stage_tmp;
		// change scene to main scene

		stage.setScene(scene);
		stage.show();
	}
}
