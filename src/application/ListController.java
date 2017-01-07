package application;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javax.security.auth.callback.Callback;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;

public class ListController implements Initializable
{
	Stage stage;
	Scene scene;
	
	//def fxml loader
	Parent orderstage;
	Parent mainstage;
	Parent menustage;
	Parent liststage;
	
	@FXML Label order_time;
	Date now_time; Timer timer;
	
	@FXML private ComboBox<String> DiscountCombo; // fx:id="fruitCombo"
	
	@FXML private Button p_page; @FXML private Button n_page;
	
	@FXML private Label num_people;
	@FXML private Label total_money;
	
	@FXML private ListView<String> menulist;
	ArrayList<String> passing_list;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{	
		//menulist = new ListView<String>();
		//ObservableList observableList = FXCollections.observableArrayList();
		//observableList.setAll(passing_list);
		//menulist.setItems(FXCollections.observableList(passing_list));
	
		

		
		// populate the fruit combo box with item choices.
		DiscountCombo.getItems().setAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

	    // listen for changes to the fruit combo box selection and update the displayed fruit image accordingly.
		DiscountCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
		{
		      public void changed(ObservableValue<? extends String> selected, String oldPeople, String newPeople)
		      {
			        if (oldPeople != null) 
			        {
				          switch(oldPeople) 
				          {
				                case "1":  System.out.print("0"); break;
				                case "2":  System.out.print("0"); break;
				                case "3":  System.out.print("0"); break;
				                case "4":  System.out.print("0"); break;
				                case "5":  System.out.print("0"); break;
				                case "6":  System.out.print("0"); break;
				                case "7":  System.out.print("0"); break;
				                case "8":  System.out.print("0"); break;
				                case "9":  System.out.print("0"); break;
				                case "10": System.out.print("0"); break;
				          }
			        }
			        
			        if (newPeople != null) 
			        {
				          switch(newPeople) 
				          {
				          		case "1":  System.out.print("1"); break;
				          		case "2":  System.out.print("2"); break;
				          		case "3":  System.out.print("3"); break;
				                case "4":  System.out.print("4"); break;
				                case "5":  System.out.print("5"); break;
				                case "6":  System.out.print("6"); break;
				                case "7":  System.out.print("7"); break;
				                case "8":  System.out.print("8"); break;
				                case "9":  System.out.print("9"); break;
				                case "10": System.out.print("10"); break;
				          }
			        }
			      }
	    });	
		

	}
	
	@FXML protected void PreviousPageButtonAction(ActionEvent event) 
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
	
	@FXML protected void NextPageButtonAction(ActionEvent event) 
	{
		try 
		{
			liststage = FXMLLoader.load(getClass().getResource("/application/ListStage.fxml"));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		stage = MainScene.stage_tmp;
		scene = new Scene(liststage,1024,720);
		
		//change scene to main scene
	    stage.setScene(scene);
	    stage.show();
    }
	
	public void setMoney(int money)
	{
		total_money.setText(Integer.toString(money));
	}
	
	public void setPeople(int people)
	{
		num_people.setText(Integer.toString(people));
	}
	
	public void setMenuList(ArrayList<String> passing_menu)
	{
		int cursor = 0;
		while(cursor != passing_menu.size())
		{
			System.out.print(passing_menu.get(cursor));
			passing_list.add(passing_menu.get(cursor++));
		}
			
	}
	

}
