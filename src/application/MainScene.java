package application;

import application.order.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class MainScene extends Application {

	private static final String POS_NAME = "HamiMelonPOS";
	private static final boolean ENABLE_FULLSCREEN = false;

	private static Stage mainStage;
	private static boolean firstIn = true;

	public static void main(String[] args) {
		new TimerManager();
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainStage = primaryStage;

		Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));

		changeScene(root);

		firstIn = false;
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		MenuController.pause = true;
	}

	public static void changeScene(Parent root) {
		
		Scene mainScene = new Scene(root, 1024, 720);

		mainStage.setScene(mainScene);
		mainStage.setTitle(POS_NAME);
		
		mainStage.setFullScreen(ENABLE_FULLSCREEN);
		mainStage.setFullScreenExitHint(firstIn ? "千萬不要按 ESC 以離開全螢幕" : "");
		mainStage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.ESCAPE, KeyCombination.CONTROL_ANY));
		
		mainStage.show();
	}
}
