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

	private Stage stage;
	private Scene scene;

	@FXML
	protected void OrderButtonAction(ActionEvent event) throws IOException {
		// def fxml loader
		Parent orderstage = FXMLLoader.load(getClass().getResource("/application/OrderStage.fxml"));

		// ref fxml to stage
		scene = new Scene(orderstage, 1024, 720);
		stage = MainScene.stage_tmp;
		
		// change scene to main scene
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	protected void PurchaseButtonAction(ActionEvent event) throws IOException {
		// def fxml loader
		Parent stockStage = FXMLLoader.load(getClass().getResource("/application/StockStage.fxml"));

		// ref fxml to stage
		scene = new Scene(stockStage, 1024, 720);
		stage = MainScene.stage_tmp;
		// change scene to main scene
		
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	protected void DailyButtonAction(ActionEvent event) {
		daily.setText("Sign in button pressed3");
	}

	@FXML
	protected void MonthlyButtonAction(ActionEvent event) {
		monthly.setText("Sign in button pressed4");
	}
}
