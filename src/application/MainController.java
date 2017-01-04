package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private Button order;
	@FXML
	private Button purchase;
	@FXML
	private Button daily;
	@FXML
	private Button monthly;

	@FXML
	protected void OrderButtonAction(ActionEvent event) throws IOException {
		// def fxml loader
		Parent orderstage = FXMLLoader.load(getClass().getResource("/application/OrderStage.fxml"));
		// ref fxml to stage
		Stage stage = MainScene.stage_tmp;
		Scene scene = new Scene(orderstage, 1024, 720);
		// change scene to order stage
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	protected void PurchaseButtonAction(ActionEvent event) {
		purchase.setText("purchase clicked");
	}

	@FXML
	protected void DailyButtonAction(ActionEvent event) {
		daily.setText("daily clicked");
	}

	@FXML
	protected void MonthlyButtonAction(ActionEvent event) {
		monthly.setText("monthly clicked");
	}
}
