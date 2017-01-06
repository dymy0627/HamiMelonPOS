package application;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OrderController 
{
	@FXML private Button forhere;
	@FXML private Button togo;
	@FXML private Button delivery;
	
	Stage stage;
	Scene scene;
	
	//def fxml loader
	Parent orderstage;
	Parent mainstage;
	Parent menustage;
	
	@FXML protected void ForHereButtonAction(ActionEvent event) 
	{
		try 
		{
			menustage = FXMLLoader.load(getClass().getResource("/application/MenuStage.fxml"));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//想用static可試Singleton
		//OO可試interface callback
		//ref fxml to stage 
		stage = MainScene.stage_tmp;
		scene = new Scene(menustage,1024,720);
		
		//change scene to main scene
	    stage.setScene(scene);
	    stage.show();
    }
	
	@FXML protected void ToGoButtonAction(ActionEvent event) 
	{
        forhere.setText("Sign in button pressed2");
    }
	
	@FXML protected void DeliveryButtonAction(ActionEvent event) 
	{
        forhere.setText("Sign in button pressed3");
    }
}
