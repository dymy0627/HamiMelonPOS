package application.stock;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import application.MainScene;
import db.MySqlConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class StockController implements Initializable, StockListViewListener {
	@FXML
	private ListView<StockBean> stockListView;

	@FXML
	private ComboBox<String> stockNameComboBox;
	@FXML
	private TextField stockNameTextField;
	@FXML
	private TextField quantityTextField;
	@FXML
	private TextField amountTextField;
	@FXML
	private TextField manufacturerTextField;

	@FXML
	private ProgressIndicator myProgressIndicator;
	@FXML
	private Button saveButton;

	@FXML
	private Button stockNameButton;

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

		myProgressIndicator.setVisible(false);
		new Thread(new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				myProgressIndicator.setVisible(true);
				MySqlConnection mySqlConnection = new MySqlConnection();
				mySqlConnection.connectSql();
				stockObservableList.setAll(mySqlConnection.selectAllStock());
				List<String> stockNameList = mySqlConnection.selectManufacturer();
				stockNameList.add("其他");
				stockNameComboBox.getItems().setAll(stockNameList);
				mySqlConnection.disconnectSql();
				return true;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				stockNameComboBox.getSelectionModel().selectFirst();
				myProgressIndicator.setVisible(false);
				System.out.println("Load from DB Done!");
			}

			@Override
			protected void failed() {
				super.failed();
				myProgressIndicator.setVisible(false);
				System.out.println("Load from DB Failed!");
			}
		}).start();

		stockNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldSelected, String newSelected) {
				if (newSelected.equals("其他")) {
					stockNameComboBox.setVisible(false);
					stockNameTextField.setVisible(true);
					stockNameButton.setVisible(true);
				}
			}
		});
	}

	private void updateListView() {
		stockListView.setItems(null);
		stockListView.setItems(stockObservableList);
	}

	private boolean validCheck(int value) {
		return value > 0;
	}

	private void clearTextField() {
		quantityTextField.setText("");
		amountTextField.setText("");
		manufacturerTextField.setText("");
	}

	@Override
	public void delete() {
		deleteStockFromList(stockListView.getSelectionModel().getSelectedItem());
		stockListView.getSelectionModel().clearSelection();
	}

	@FXML
	protected void AddButtonAction(ActionEvent event) {

		String stockNameText;

		if (stockNameComboBox.isVisible()) {
			stockNameText = stockNameComboBox.getSelectionModel().getSelectedItem();
		} else {
			stockNameText = stockNameTextField.getText();
		}

		int quantityValue = 0;
		int amountValue = 0;
		try {
			quantityValue = Integer.parseInt(quantityTextField.getText());
			amountValue = Integer.parseInt(amountTextField.getText());
		} catch (NumberFormatException e) {
			System.out.println("Quantity and Amount cannot be String.");
		}

		String manufacturerText = manufacturerTextField.getText();

		if (!stockNameText.isEmpty() && validCheck(amountValue) && validCheck(quantityValue)
				&& manufacturerText != null) {
			clearTextField();
			StockBean stock = new StockBean();
			stock.setId(tempId++);
			stock.setName(stockNameText);
			stock.setQuantity(quantityValue);
			stock.setUnit("份");
			stock.setAmount(amountValue);
			stock.setManufacturer(manufacturerText);
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
	protected void ShowComboBoxAction(ActionEvent event) {
		stockNameComboBox.setVisible(true);
		stockNameComboBox.getSelectionModel().selectFirst();
		stockNameTextField.setVisible(false);
		stockNameTextField.setText("");
		stockNameButton.setVisible(false);
	}

	@FXML
	protected void SaveButtonAction(ActionEvent event) {
		if (mAddHashMap.isEmpty() && mDeleteHashMap.isEmpty())
			return;
		new Thread(new Task<Boolean>() {

			@Override
			protected Boolean call() throws Exception {
				myProgressIndicator.setVisible(true);
				saveButton.setDisable(true);
				MySqlConnection mySqlConnection = new MySqlConnection();
				mySqlConnection.connectSql();
				for (Map.Entry<Integer, StockBean> entry : mAddHashMap.entrySet()) {
					addToDb(mySqlConnection, entry.getValue());
				}
				for (Map.Entry<Integer, StockBean> entry : mDeleteHashMap.entrySet()) {
					deleteFromDb(mySqlConnection, entry.getValue());
				}
				stockObservableList.setAll(mySqlConnection.selectAllStock());
				mySqlConnection.disconnectSql();
				return true;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				myProgressIndicator.setVisible(false);
				saveButton.setDisable(false);
				mAddHashMap.clear();
				mDeleteHashMap.clear();
				System.out.println("Save Done!");
			}

			@Override
			protected void failed() {
				super.failed();
				myProgressIndicator.setVisible(false);
				saveButton.setDisable(false);
				System.out.println("Save Failed!");
			}
		}).start();
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
		Parent mainStage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));

		MainScene.changeScene(mainStage);
	}
}
