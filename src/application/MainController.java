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

public class MainController 
{
	@FXML private Button order;
	@FXML private Button purchase;
	@FXML private Button daily;
	@FXML private Button monthly;
	
	Stage stage;
	Scene scene;
	
	//def fxml loader
	Parent orderstage;
	//Parent mainstage;
	
	@FXML protected void OrderButtonAction(ActionEvent event) 
	{
		try 
		{
			orderstage = FXMLLoader.load(getClass().getResource("/application/OrderStage.fxml"));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		//ref fxml to stage 
		stage = MainScene.stage_tmp;
		scene = new Scene(orderstage,1024,720);
		
		//change scene to main scene
	    stage.setScene(scene);
	    stage.show();
    }
	
	@FXML protected void PurchaseButtonAction(ActionEvent event) 
	{
        purchase.setText("Sign in button pressed2");
    }
	
	@FXML protected void DailyButtonAction(ActionEvent event) 
	{
        daily.setText("Sign in button pressed3");
    }
	
	@FXML protected void MonthlyButtonAction(ActionEvent event) 
	{
        monthly.setText("Sign in button pressed4");
    }
}
