package application.stock;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.MainScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StockController implements Initializable {

	@FXML
	private ListView<String> stockListView;

	@FXML
	private TextField manufacturerTextField;
	@FXML
	private TextField stockNameTextField;
	@FXML
	private TextField amountTextField;

	@FXML
	protected void AddButtonAction(ActionEvent event) {
		String manufacturerText = manufacturerTextField.getText();
		String stockNameText = stockNameTextField.getText();
		String amountText = amountTextField.getText();
		if (!manufacturerText.isEmpty() && !stockNameText.isEmpty() && !amountText.isEmpty()) {
			stockListView.getItems().add(manufacturerText + ", " + stockNameText + ", " + amountText);
			manufacturerTextField.setText("");
			stockNameTextField.setText("");
			amountTextField.setText("");
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
