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
	private Button monthly;

	@FXML
	protected void OrderButtonAction(ActionEvent event) {

		MenuBuilder.load();

		showStage("/fxml/MenuStage.fxml");
	}

	@FXML
	protected void PurchaseButtonAction(ActionEvent event) {
		showStage("/fxml/StockStage.fxml");
	}

	@FXML
	protected void DailyButtonAction(ActionEvent event) {
		showStage("/fxml/DailyReportStage.fxml");
	}

	@FXML
	protected void MonthlyButtonAction(ActionEvent event) {
		monthly.setText("Sign in button pressed4");
	}

	private void showStage(String filePath) {
		try {
			// def fxml loader
			Parent root = FXMLLoader.load(getClass().getResource(filePath));
			
			// ref fxml to stage
			Stage stage = MainScene.stage_tmp;
			Scene scene = new Scene(root, 1024, 720);

			// change scene to main scene
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
