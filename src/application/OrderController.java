package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OrderController {
	@FXML
	private Button forhere;
	@FXML
	private Button togo;
	@FXML
	private Button delivery;

	@FXML
	protected void ForHereButtonAction(ActionEvent event) throws IOException {
		// def fxml loader
		Parent menustage = FXMLLoader.load(getClass().getResource("/fxml/MenuStage.fxml"));

		// ref fxml to stage
		Stage stage = MainScene.stage_tmp;
		Scene scene = new Scene(menustage, 1024, 720);

		// change scene to main scene
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	protected void ToGoButtonAction(ActionEvent event) {
		togo.setText("Sign in button pressed2");
	}

	@FXML
	protected void DeliveryButtonAction(ActionEvent event) {
		delivery.setText("Sign in button pressed3");
	}
}
