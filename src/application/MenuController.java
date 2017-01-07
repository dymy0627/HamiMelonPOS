package application;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
	Parent menustage;
	Parent liststage;
	
	@FXML Label order_time;
	Date now_time; Timer timer; static boolean pause;

	@FXML private CheckBox meal1; @FXML private CheckBox meal2;
	@FXML private CheckBox meal3; @FXML private CheckBox meal4;
	@FXML private CheckBox meal5; @FXML private CheckBox meal6;
	@FXML private CheckBox meal7; @FXML private CheckBox meal8;
	@FXML private CheckBox meal9; @FXML private CheckBox meal10;
	ArrayList<String> passing_menu;
	
	int num_people; int money; int pork;
	
	@FXML private ComboBox<String> PeopleCombo; // fx:id="fruitCombo"
	
	@FXML private Button p_page; @FXML private Button n_page;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{	
		//timer method
		order_time();
		
		passing_menu = new ArrayList<String>();
		
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
		
		meal1.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{
	        public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val)
	        {
	        	if(new_val == true)
	        	{
	        		passing_menu.add(meal1.getText());	
			    	System.out.println(passing_menu.size());
	        	}
	        	else
	        	{
	        		passing_menu.remove(passing_menu.indexOf(meal1.getText()));
	        	}
	        	
	        }
	    });
		
		meal2.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{
	        public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val)
	        {
	        	if(new_val == true)
	        	{
	        		passing_menu.add(meal2.getText());	
			    	System.out.println(passing_menu.size());
	        	}
	        	else
	        	{
	        		passing_menu.remove(passing_menu.indexOf(meal2.getText()));
	        	}
	        }
	    });
		
		meal3.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{
	        public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val)
	        {
	        	if(new_val == true)
	        	{
	        		passing_menu.add(meal3.getText());	
			    	System.out.println(passing_menu.size());
	        	}
	        	else
	        	{
	        		passing_menu.remove(passing_menu.indexOf(meal3.getText()));
	        	}
	        }
	    });
		
		meal4.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{
	        public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val)
	        {
	        	if(new_val == true)
	        	{
	        		passing_menu.add(meal4.getText());	
			    	System.out.println(passing_menu.size());
	        	}
	        	else
	        	{
	        		passing_menu.remove(passing_menu.indexOf(meal4.getText()));
	        	}
	        }
	    });
		
		meal5.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{
	        public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val)
	        {
	        	if(new_val == true)
	        	{
	        		passing_menu.add(meal5.getText());	
			    	System.out.println(passing_menu.size());
	        	}
	        	else
	        	{
	        		passing_menu.remove(passing_menu.indexOf(meal5.getText()));
	        	}
	        }
	    });
		
		meal6.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{
	        public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val)
	        {
	        	if(new_val == true)
	        	{
	        		passing_menu.add(meal6.getText());	
			    	System.out.println(passing_menu.size());
	        	}
	        	else
	        	{
	        		passing_menu.remove(passing_menu.indexOf(meal6.getText()));
	        	}
	        }
	    });
		
		meal7.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{
	        public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val)
	        {
	        	if(new_val == true)
	        	{
	        		passing_menu.add(meal7.getText());	
			    	System.out.println(passing_menu.size());
	        	}
	        	else
	        	{
	        		passing_menu.remove(passing_menu.indexOf(meal7.getText()));
	        	}
	        }
	    });
		
		meal8.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{
	        public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val)
	        {
	        	if(new_val == true)
	        	{
	        		passing_menu.add(meal8.getText());	
			    	System.out.println(passing_menu.size());
	        	}
	        	else
	        	{
	        		passing_menu.remove(passing_menu.indexOf(meal8.getText()));
	        	}
	        }
	    });
		
		meal9.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{
	        public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val)
	        {
	        	if(new_val == true)
	        	{
	        		passing_menu.add(meal9.getText());	
			    	System.out.println(passing_menu.size());
	        	}
	        	else
	        	{
	        		passing_menu.remove(passing_menu.indexOf(meal9.getText()));
	        	}
	        }
	    });
		
		meal10.selectedProperty().addListener(new ChangeListener<Boolean>() 
		{
	        public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val)
	        {
	        	if(new_val == true)
	        	{
	        		passing_menu.add(meal10.getText());	
			    	System.out.println(passing_menu.size());
	        	}
	        	else
	        	{
	        		passing_menu.remove(passing_menu.indexOf(meal10.getText()));
	        	}
	        }
	    });
		//meal1.setOnAction(e1 -> handleButtonAction1(e1));

	}
	
	private void order_time() 
	{
		pause = false;
		timer = new Timer();
		// schedule(TimerTask task, long delay, long period)
		timer.schedule(new OrderTimeTask(), 3000, 2000);
	}

	private void handleButtonAction1(ActionEvent e) 
	{
	    if(meal1.isPressed())
	    {
	    	passing_menu.add(meal1.getText());	
	    	System.out.println(passing_menu.size());
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
	
	@FXML protected void NextPageButtonAction(ActionEvent event) 
	{
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/ListStage.fxml")); 
			Parent root = (Parent)fxmlLoader.load();          
			ListController controller = fxmlLoader.<ListController>getController();
			controller.setMoney(6666);
			controller.setPeople(6);
			
			System.out.println(passing_menu.size());
			controller.setMenuList(passing_menu);
			scene = new Scene(root,1024,720);
			//liststage = FXMLLoader.load(getClass().getResource("/application/ListStage.fxml"));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		pause = true;

		stage = MainScene.stage_tmp;
		//scene = new Scene(liststage,1024,720);
		
		
		//change scene to main scene
	    stage.setScene(scene);
	    stage.show();
    }
}
