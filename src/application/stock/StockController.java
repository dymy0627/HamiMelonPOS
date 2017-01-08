package application.stock;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.MainScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class StockController implements Initializable {

	@FXML
	private ListView<StockBean> stockListView;

	@FXML
	private TextField manufacturerTextField;
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
				return new StockListViewCell();
			}
		});
	}

	@FXML
	protected void AddButtonAction(ActionEvent event) {
		String manufacturerText = manufacturerTextField.getText();
		String stockNameText = stockNameTextField.getText();
		String amountText = amountTextField.getText();
		if (!manufacturerText.isEmpty() && !stockNameText.isEmpty() && !amountText.isEmpty()) {
			StockBean stock = new StockBean();
			stock.setManufacturer(manufacturerText);
			stock.setName(stockNameText);
			stock.setAmount(amountText);
			stockList.add(stock);
			stockObservableList.add(stock);
			clearTextField();
		}
	}

	private void clearTextField() {
		manufacturerTextField.setText("");
		stockNameTextField.setText("");
		amountTextField.setText("");
	}

	@FXML
	protected void SendButtonAction(ActionEvent event) {
		stockListView.getItems().clear();
		clearTextField();
		for (StockBean stock : stockList) {
			System.out.println(stock.getManufacturer() + ", " + stock.getName() + ", " + stock.getAmount());
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
