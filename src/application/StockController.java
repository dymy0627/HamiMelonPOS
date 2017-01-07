package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StockController {
	@FXML
	private Button add;

	@FXML
	protected void AddButtonAction(ActionEvent event) {
		System.out.println("Add clicked");
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
