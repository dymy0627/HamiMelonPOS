package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainScene extends Application {

	static Stage stage_tmp;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("HamiMelonPOS");

		// mainstage fxml load
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));

		Scene main_scene = new Scene(mainstage, 1024, 720);

		primaryStage.setScene(main_scene);
		// give stage switch tmp object
		stage_tmp = primaryStage;
		primaryStage.show();

	}

}