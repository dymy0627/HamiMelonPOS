package application;

import application.order.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Calendar;  
import java.util.Date;  
import java.util.Timer;  
public class MainScene extends Application { 
	public static Stage stage_tmp;

	public static void main(String[] args) {

		new TimerManager();
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

	@Override
	public void stop() throws Exception {
		super.stop();
		MenuController.pause = true;
	}
}

