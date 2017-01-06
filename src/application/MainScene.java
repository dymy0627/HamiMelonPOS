package application;

import java.awt.Component;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainScene extends Application
{
	Button orderbtn; Button purchasebtn; Button dailybtn; Button monthlybtn;
	static Stage stage_tmp;
	static VBox mainstage;
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		primaryStage.setTitle("Post1");
		
		//mainstage and orderstage fxml load
		Parent mainstage = FXMLLoader.load(getClass().getResource("/application/MainStage.fxml"));
		Parent orderstage = FXMLLoader.load(getClass().getResource("/application/OrderStage.fxml"));
		Parent menustage = FXMLLoader.load(getClass().getResource("/application/MenuStage.fxml"));
		
		// Adding VBox to the scene
		Scene main_scene = new Scene(mainstage,1024,720);
		
		Scene order_scene = new Scene(orderstage,1024,720);
		
		Scene menu_scene = new Scene(menustage,1024,720);
		
		primaryStage.setScene(main_scene);
		//give stage switch tmp object
		stage_tmp = primaryStage;
		primaryStage.show(); 	
		
		// main scene VBox
		/**
		mainstage = new VBox();
		mainstage.setAlignment(Pos.TOP_CENTER);
		mainstage.setPadding(new Insets(100, 50, 50, 50));
		mainstage.setSpacing(100);
   
		// Buttons
		orderbtn = new Button();
		orderbtn.setText("點餐");
		orderbtn.setMinSize(200, 30);
		mainstage.getChildren().add(orderbtn);
    
		purchasebtn = new Button();
		purchasebtn.setMinSize(200, 30);
		purchasebtn.setText("進出貨");
		mainstage.getChildren().add(purchasebtn);

		dailybtn = new Button();
		dailybtn.setText("日報表");
		dailybtn.setMinSize(200, 30);
		mainstage.getChildren().add(dailybtn);

		monthlybtn = new Button();
		monthlybtn.setText("月報表");
		monthlybtn.setMinSize(200, 30);
		mainstage.getChildren().add(monthlybtn);
		**/
		
		//add listener switch to order scene
		//orderbtn.setOnAction(e-> ButtonClicked(e));
		
		/**
		orderbtn.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event)
			{
				//change scene to order scene
				primaryStage.setScene(order_scene);
				//give stage switch tmp object
				stage_tmp = primaryStage;
			}
		});
		**/

  }

/**
  private Object ButtonClicked(ActionEvent e)
  {
	  if (e.getSource()==orderbtn)
		  		  return 0;
	  else
		  if(e.getSource()==purchasebtn)
	    		  return 0;
		  else
			  if(e.getSource()==dailybtn)
				  return 0;
			  else
				  return 0;
	  	
  }
  **/
}