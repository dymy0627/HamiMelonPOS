package application.stock;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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

	private ObservableList<StockBean> stockObservableList = FXCollections.observableArrayList();

	private Map<Integer, StockBean> mAddHashMap = new HashMap<>();
	private Map<Integer, StockBean> mDeleteHashMap = new HashMap<>();

	private int tempId = 0;

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
		stockObservableList.setAll(mySqlConnection.selectAllStock());
		manufacturerComboBox.getItems().setAll(mySqlConnection.selectManufacturer());
		mySqlConnection.disconnectSql();
		manufacturerComboBox.getSelectionModel().selectFirst();
	}

	private void updateListView() {
		stockListView.setItems(null);
		stockListView.setItems(stockObservableList);
	}

	private boolean validAmount(int amountValue) {
		return amountValue > 0;
	}

	private void clearTextField() {
		stockNameTextField.setText("");
		amountTextField.setText("");
	}

	@Override
	public void delete() {
		deleteStockFromList(stockListView.getSelectionModel().getSelectedItem());
		stockListView.getSelectionModel().clearSelection();
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
			stock.setId(tempId++);
			stock.setManufacturer(manufacturerText);
			stock.setName(stockNameText);
			stock.setAmount(amountValue);
			addStockToList(stock);
		}
	}

	private void deleteStockFromList(StockBean stock) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (mAddHashMap.containsKey(stock.getId())) {
					mAddHashMap.remove(stock.getId());
				} else {
					mDeleteHashMap.put(stock.getId(), stock);
				}
				stockObservableList.remove(stock);
				System.out.println("remove from List =" + stock.getId());
			}
		});
	}

	private void addStockToList(StockBean stock) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mAddHashMap.put(stock.getId(), stock);
				stockObservableList.add(stock);
				System.out.println("add to List =" + stock.getId());
			}
		});
	}

	@FXML
	protected void SaveButtonAction(ActionEvent event) {
		MySqlConnection mySqlConnection = new MySqlConnection();
		mySqlConnection.connectSql();
		for (Map.Entry<Integer, StockBean> entry : mAddHashMap.entrySet()) {
			addToDb(mySqlConnection, entry.getValue());
		}
		for (Map.Entry<Integer, StockBean> entry : mDeleteHashMap.entrySet()) {
			deleteFromDb(mySqlConnection, entry.getValue());
		}
		mySqlConnection.disconnectSql();
	}

	private boolean deleteFromDb(MySqlConnection mySqlConnection, StockBean stock) {
		boolean deleteResult = mySqlConnection.deleteShippingDate(stock);
		System.out.println("刪除一筆" + (deleteResult ? "成功" : "失敗"));
		return deleteResult;
	}

	private boolean addToDb(MySqlConnection mySqlConnection, StockBean stock) {
		boolean addResult = mySqlConnection.insertShippingData(stock);
		System.out.println("新增一筆" + (addResult ? "成功" : "失敗"));
		return addResult;
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
