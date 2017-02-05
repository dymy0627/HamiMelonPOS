package application;

import java.io.IOException;

import application.order.MenuBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private Button daily;
	@FXML
	private Button monthly;

	private Stage stage;
	private Scene scene;

	@FXML
	protected void OrderButtonAction(ActionEvent event) throws IOException {

		MenuBuilder.load();
		// def fxml loader
		Parent menuStage = FXMLLoader.load(getClass().getResource("/fxml/MenuStage.fxml"));

		// ref fxml to stage
		stage = MainScene.stage_tmp;
		scene = new Scene(menuStage, 1024, 720);

		// change scene to main scene
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	protected void PurchaseButtonAction(ActionEvent event) throws IOException {
		// def fxml loader
		Parent stockStage = FXMLLoader.load(getClass().getResource("/fxml/StockStage.fxml"));

		// ref fxml to stage
		stage = MainScene.stage_tmp;
		scene = new Scene(stockStage, 1024, 720);

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
