package application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class MenuController implements Initializable
{
	Stage stage;
	Scene scene;
	
	//def fxml loader
	Parent orderstage;
	Parent mainstage;

	@FXML private CheckBox meal1; @FXML private CheckBox meal2;
	@FXML private CheckBox meal3; @FXML private CheckBox meal4;
	@FXML private CheckBox meal5; @FXML private CheckBox meal6;
	@FXML private CheckBox meal7; @FXML private CheckBox meal8;
	@FXML private CheckBox meal9; @FXML private CheckBox meal10;
	
	int num_people; int pork; int num_beef; int num_chicken; int num_lamb;
	
	@FXML private ComboBox<String> PeopleCombo; // fx:id="fruitCombo"
	@FXML private Button p_page;
	
	
	
	private void handleButtonAction(ActionEvent e) 
	{
	    int count=0;
	    String choices="";
	    if(meal1.isSelected())
	    {
	       System.out.print("66");
	     }
	    if(meal2.isSelected())
	    {
	        
	     }
	    if(meal3.isSelected())
	    {
	       
	     }
	 }
	
	@FXML protected void PreviousPageButtonAction(ActionEvent event) 
	{
		try 
		{
			orderstage = FXMLLoader.load(getClass().getResource("/application/OrderStage.fxml"));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//想用static可試Singleton
		//OO可試interface callback
		//ref fxml to stage 
		stage = MainScene.stage_tmp;
		scene = new Scene(orderstage,1024,720);
		
		//change scene to main scene
	    stage.setScene(scene);
	    stage.show();
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{	
		meal1.setOnAction(e -> handleButtonAction(e));
		
		// populate the fruit combo box with item choices.
		PeopleCombo.getItems().setAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

	    // listen for changes to the fruit combo box selection and update the displayed fruit image accordingly.
		PeopleCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
		{
		      public void changed(ObservableValue<? extends String> selected, String oldPeople, String newPeople)
		      {
			        if (oldPeople != null) 
			        {
				          switch(oldPeople) 
				          {
				                case "1":  num_people=0; System.out.print("0"); break;
				                case "2":  num_people=0; System.out.print("0"); break;
				                case "3":  num_people=0; System.out.print("0"); break;
				                case "4":  num_people=0; System.out.print("0"); break;
				                case "5":  num_people=0; System.out.print("0"); break;
				                case "6":  num_people=0; System.out.print("0"); break;
				                case "7":  num_people=0; System.out.print("0"); break;
				                case "8":  num_people=0; System.out.print("0"); break;
				                case "9":  num_people=0; System.out.print("0"); break;
				                case "10": num_people=0; System.out.print("0"); break;
				          }
			        }
			        
			        if (newPeople != null) 
			        {
				          switch(newPeople) 
				          {
				          		case "1":  num_people=1; System.out.print("1"); break;
				          		case "2":  num_people=2; System.out.print("2"); break;
				          		case "3":  num_people=3; System.out.print("3"); break;
				                case "4":  num_people=4; System.out.print("4"); break;
				                case "5":  num_people=5; System.out.print("5"); break;
				                case "6":  num_people=6; System.out.print("6"); break;
				                case "7":  num_people=7; System.out.print("7"); break;
				                case "8":  num_people=8; System.out.print("8"); break;
				                case "9":  num_people=9; System.out.print("9"); break;
				                case "10": num_people=10; System.out.print("10"); break;
				          }
			        }
			      }
	    });		
	}
    
	
	
}
