package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MainController {

	@FXML
	protected void OrderButtonAction(ActionEvent event) {
		showStage("/fxml/MenuStage.fxml");
	}

	@FXML
	protected void PurchaseButtonAction(ActionEvent event) {
		showStage("/fxml/PurchaseAndStockStage.fxml");
	}

	@FXML
	protected void DailyButtonAction(ActionEvent event) {
		showStage("/fxml/DailyReportStage.fxml");
	}

	@FXML
	protected void MonthlyButtonAction(ActionEvent event) {
		showStage("/fxml/MonthlyReportStage.fxml");
		//showStage("/fxml/ReportStage.fxml");
	}

	private void showStage(String filePath) {
		try {
			// def fxml loader
			Parent root = FXMLLoader.load(getClass().getResource(filePath));

			MainScene.changeScene(root);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
