package application.order;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

import application.MainScene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MenuController implements Initializable {

	@FXML
	private CheckBox meal1, meal2, meal3, meal4, meal5, meal6, meal7, meal8, meal9, meal10, meal11, meal12, meal13,
			meal14, meal15, meal16, meal17, meal18, meal19, meal20, meal21, meal22, meal23, meal24, meal25, meal26,
			meal27, meal28, meal29, meal30, meal31, meal32, meal33, meal34, meal35, meal36, meal37, meal38, meal39,
			meal40, meal41, meal42, meal43, meal44, meal45, meal46, meal47, meal48, meal49, meal50, meal51, meal52,
			meal53, meal54, meal55, meal56, meal57, meal58, meal59, meal60, meal61, meal62, meal63, meal64, meal65,
			meal66, meal67, meal68, meal69, meal70, meal71, meal72, meal73, meal74;

	@FXML
	private Button pbtn_meal1, pbtn_meal2, pbtn_meal3, pbtn_meal4, pbtn_meal5, pbtn_meal6, pbtn_meal7, pbtn_meal8,
			pbtn_meal9, pbtn_meal10, pbtn_meal11, pbtn_meal12, pbtn_meal13, pbtn_meal14, pbtn_meal15, pbtn_meal16,
			pbtn_meal17, pbtn_meal18, pbtn_meal19, pbtn_meal20, pbtn_meal21, pbtn_meal22, pbtn_meal23, pbtn_meal24,
			pbtn_meal25, pbtn_meal26, pbtn_meal27, pbtn_meal28, pbtn_meal29, pbtn_meal30, pbtn_meal31, pbtn_meal32,
			pbtn_meal33, pbtn_meal34, pbtn_meal35, pbtn_meal36, pbtn_meal37, pbtn_meal38, pbtn_meal39, pbtn_meal40,
			pbtn_meal41, pbtn_meal42, pbtn_meal43, pbtn_meal44, pbtn_meal45, pbtn_meal46, pbtn_meal47, pbtn_meal48,
			pbtn_meal49, pbtn_meal50, pbtn_meal51, pbtn_meal52, pbtn_meal53, pbtn_meal54, pbtn_meal55, pbtn_meal56,
			pbtn_meal57, pbtn_meal58, pbtn_meal59, pbtn_meal60, pbtn_meal61, pbtn_meal62, pbtn_meal63, pbtn_meal64,
			pbtn_meal65, pbtn_meal66, pbtn_meal67, pbtn_meal68, pbtn_meal69, pbtn_meal70, pbtn_meal71, pbtn_meal72,
			pbtn_meal73, pbtn_meal74;

	@FXML
	private Button mbtn_meal1, mbtn_meal2, mbtn_meal3, mbtn_meal4, mbtn_meal5, mbtn_meal6, mbtn_meal7, mbtn_meal8,
			mbtn_meal9, mbtn_meal10, mbtn_meal11, mbtn_meal12, mbtn_meal13, mbtn_meal14, mbtn_meal15, mbtn_meal16,
			mbtn_meal17, mbtn_meal18, mbtn_meal19, mbtn_meal20, mbtn_meal21, mbtn_meal22, mbtn_meal23, mbtn_meal24,
			mbtn_meal25, mbtn_meal26, mbtn_meal27, mbtn_meal28, mbtn_meal29, mbtn_meal30, mbtn_meal31, mbtn_meal32,
			mbtn_meal33, mbtn_meal34, mbtn_meal35, mbtn_meal36, mbtn_meal37, mbtn_meal38, mbtn_meal39, mbtn_meal40,
			mbtn_meal41, mbtn_meal42, mbtn_meal43, mbtn_meal44, mbtn_meal45, mbtn_meal46, mbtn_meal47, mbtn_meal48,
			mbtn_meal49, mbtn_meal50, mbtn_meal51, mbtn_meal52, mbtn_meal53, mbtn_meal54, mbtn_meal55, mbtn_meal56,
			mbtn_meal57, mbtn_meal58, mbtn_meal59, mbtn_meal60, mbtn_meal61, mbtn_meal62, mbtn_meal63, mbtn_meal64,
			mbtn_meal65, mbtn_meal66, mbtn_meal67, mbtn_meal68, mbtn_meal69, mbtn_meal70, mbtn_meal71, mbtn_meal72,
			mbtn_meal73, mbtn_meal74;

	@FXML
	private Label num_meal1, num_meal2, num_meal3, num_meal4, num_meal5, num_meal6, num_meal7, num_meal8, num_meal9,
			num_meal10, num_meal11, num_meal12, num_meal13, num_meal14, num_meal15, num_meal16, num_meal17, num_meal18,
			num_meal19, num_meal20, num_meal21, num_meal22, num_meal23, num_meal24, num_meal25, num_meal26, num_meal27,
			num_meal28, num_meal29, num_meal30, num_meal31, num_meal32, num_meal33, num_meal34, num_meal35, num_meal36,
			num_meal37, num_meal38, num_meal39, num_meal40, num_meal41, num_meal42, num_meal43, num_meal44, num_meal45,
			num_meal46, num_meal47, num_meal48, num_meal49, num_meal50, num_meal51, num_meal52, num_meal53, num_meal54,
			num_meal55, num_meal56, num_meal57, num_meal58, num_meal59, num_meal60, num_meal61, num_meal62, num_meal63,
			num_meal64, num_meal65, num_meal66, num_meal67, num_meal68, num_meal69, num_meal70, num_meal71, num_meal72,
			num_meal73, num_meal74;

	private int price_meal1, price_meal2, price_meal3, price_meal4, price_meal5, price_meal6, price_meal7, price_meal8, price_meal9,
			price_meal10, price_meal11, price_meal12, price_meal13, price_meal14, price_meal15, price_meal16, price_meal17, price_meal18,
			price_meal19, price_meal20, price_meal21, price_meal22, price_meal23, price_meal24, price_meal25, price_meal26, price_meal27,
			price_meal28, price_meal29, price_meal30, price_meal31, price_meal32, price_meal33, price_meal34, price_meal35, price_meal36,
			price_meal37, price_meal38, price_meal39, price_meal40, price_meal41, price_meal42, price_meal43, price_meal44, price_meal45,
			price_meal46, price_meal47, price_meal48, price_meal49, price_meal50, price_meal51, price_meal52, price_meal53, price_meal54,
			price_meal55, price_meal56, price_meal57, price_meal58, price_meal59, price_meal60, price_meal61, price_meal62, price_meal63,
			price_meal64, price_meal65, price_meal66, price_meal67, price_meal68, price_meal69, price_meal70, price_meal71, price_meal72,
			price_meal73, price_meal74;

	private Stage stage;
	static Stage Pop_Stage;
	private Scene scene;

	public static boolean pause;
	
	@FXML
	private ComboBox<String> peopleComboBox;

	@FXML
	private Button p_page;
	@FXML
	private Button n_page;

	@FXML
	private VBox menu1;
	
	@FXML
	private Label type;
	
	@FXML
	private ToggleButton btn_type1,btn_type2,btn_type3;
	ToggleGroup TypeGroup;

	private List<String> passing_menu = new ArrayList<String>();
	private List<CheckBox> checkBoxGroup = new ArrayList<CheckBox>();
	private List<Button> PButtonGroup = new ArrayList<Button>();
	private List<Button> MButtonGroup = new ArrayList<Button>();
	private List<Label> LabelGroup = new ArrayList<Label>();
	private List<Integer> PriceGroup = new ArrayList<Integer>();

	private String Consumption_type = "內用";
	private int num_people;
	private int now_people;

	private int money = 0;;
	private int pork;
	private int beef;
	private int chicken;
	private int lamb;
	private int seafood;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//assign price to variable
		assign_price();
		
		//togglebutton listener 
		TypeGroup = new ToggleGroup();
		btn_type1.setToggleGroup(TypeGroup);
		btn_type2.setToggleGroup(TypeGroup);
		btn_type3.setToggleGroup(TypeGroup);
		btn_type1.setSelected(true);
		TypeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
		      public void changed(ObservableValue<? extends Toggle> ov, Toggle old_select,Toggle new_select) {
		    	  
		    	  if(!btn_type1.isSelected() && old_select == btn_type1 && new_select == null)
		    		  btn_type1.setSelected(true);
		    	  if(!btn_type2.isSelected() && old_select == btn_type2 && new_select == null)
		    		  btn_type2.setSelected(true);
		    	  if(!btn_type3.isSelected() && old_select == btn_type3 && new_select == null)
		    		  btn_type3.setSelected(true);
		    	  
		    	  //System.out.println(old_select.toString() + " " + new_select.toString());
		    	  
		    	  for (CheckBox inti : checkBoxGroup) {
							inti.setSelected(false);
							LabelGroup.get(checkBoxGroup.indexOf(inti)).setText("0");
							passing_menu.clear();

						}
		    	  now_people = 0;
			    	  
			      if(TypeGroup.getToggles().get(0).isSelected()){
			    		 TypeGroup.getToggles().get(1).setSelected(false);
			    		 TypeGroup.getToggles().get(2).setSelected(false);
			    		 Consumption_type = "內用";
			    		 type.setText(Consumption_type);
			    		 peopleComboBox.setDisable(false);
			    		 peopleComboBox.setValue("1");

			      }
			      else if(TypeGroup.getToggles().get(1).isSelected()){
			    		 	 TypeGroup.getToggles().get(0).setSelected(false);
				    		 TypeGroup.getToggles().get(2).setSelected(false);
				    		 Consumption_type = "外帶";
				    		 type.setText(Consumption_type);
				    		 peopleComboBox.setDisable(true); 
				    		 peopleComboBox.setValue("1");
			      }
			      else if(TypeGroup.getToggles().get(2).isSelected()){
					    		 TypeGroup.getToggles().get(0).setSelected(false);
					    		 TypeGroup.getToggles().get(1).setSelected(false);
					    		 Consumption_type = "外送";
					    		 type.setText(Consumption_type);
					    		 peopleComboBox.setDisable(true);
					    		 peopleComboBox.setValue("1");
			      }
		      }
		    });

		// populate the fruit combo box with item choices.
		peopleComboBox.getItems().setAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
				"15");

		// listen for changes to the fruit combo box selection and update the
		// displayed fruit image accordingly.
		peopleComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldSelected, String newSelected) {
				num_people = Integer.parseInt(newSelected);
				System.out.println("num_people: " + num_people);

				for (CheckBox inti : checkBoxGroup) {
					inti.setSelected(false);
					LabelGroup.get(checkBoxGroup.indexOf(inti)).setText("0");
					passing_menu.clear();

				}
				now_people = 0;
				money = 0;

			}
		});

		menu_content_intialize();

		for (CheckBox checkbox : checkBoxGroup) {
			checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> observable, Boolean wasSelected,
						Boolean isSelected) {
					System.out.println(checkbox.getText() + " checked=" + isSelected);

					if (isSelected) {

						LabelGroup.get(checkBoxGroup.indexOf(checkbox)).setText(String.valueOf(
								Integer.parseInt(LabelGroup.get(checkBoxGroup.indexOf(checkbox)).getText()) + 1));
						now_people += 1;

						
						if ((checkbox.getText().contains("沙朗") || checkbox.getText().contains("菲力")
								|| checkbox.getText().contains("肋眼") || checkbox.getText().contains("紐約克")
								|| checkbox.getText().contains("雪花") || checkbox.getText().contains("牛小排")
								|| checkbox.getText().contains("牛筋") || checkbox.getText().contains("牛肉片")
								|| checkbox.getText().contains("牛"))) {
							beef++;
							System.out.println("beef number " + beef);
							
						}
						passing_menu.add(checkbox.getText());
						money += PriceGroup.get(checkBoxGroup.indexOf(checkbox));
						System.out.println("此單人數 " + num_people + " 已點客數" + now_people + " 金額" + money);
						
					} else {

						now_people = now_people
								- Integer.parseInt(LabelGroup.get(checkBoxGroup.indexOf(checkbox)).getText());
						//System.out.println("此單人數 " + num_people + " 已點客數" + now_people);
						if ((checkbox.getText().contains("牛") || checkbox.getText().contains("菲力")
								|| checkbox.getText().contains("肋眼") || checkbox.getText().contains("紐約克")
								|| checkbox.getText().contains("雪花") || checkbox.getText().contains("牛小排")
								|| checkbox.getText().contains("牛筋") || checkbox.getText().contains("牛肉片")
								|| checkbox.getText().contains("沙朗")) && beef >0) {
							beef = beef - Integer.parseInt(LabelGroup.get(checkBoxGroup.indexOf(checkbox)).getText());
							System.out.println("beef number2 " + beef );
							
						}
						money -= PriceGroup.get(checkBoxGroup.indexOf(checkbox))*
								Integer.parseInt(LabelGroup.get(checkBoxGroup.indexOf(checkbox)).getText());
						LabelGroup.get(checkBoxGroup.indexOf(checkbox)).setText("0");
						//if(money > 0)
							
						if(passing_menu.contains(checkbox.getText()))
							passing_menu.remove(checkbox.getText());
						System.out.println("此單人數 " + num_people + " 已點客數" + now_people + " 金額" + money);
					}

				}
			});
		}

		for (Button btn : PButtonGroup) {
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {

					if (Integer.parseInt(LabelGroup.get(PButtonGroup.indexOf(btn)).getText()) == 0) {
						checkBoxGroup.get(PButtonGroup.indexOf(btn)).setSelected(true);
					} else {
						LabelGroup.get(PButtonGroup.indexOf(btn)).setText(String
								.valueOf(Integer.parseInt(LabelGroup.get(PButtonGroup.indexOf(btn)).getText()) + 1));
						now_people++;
						passing_menu.add(checkBoxGroup.get(PButtonGroup.indexOf(btn)).getText());
						money += PriceGroup.get(PButtonGroup.indexOf(btn));
						System.out.println("此單人數 " + num_people + " 已點客數" + now_people + " 金額" + money);
						if (checkBoxGroup.get(PButtonGroup.indexOf(btn)).getText().contains("牛") || checkBoxGroup.get(PButtonGroup.indexOf(btn)).getText().contains("菲力")
								|| checkBoxGroup.get(PButtonGroup.indexOf(btn)).getText().contains("肋眼") || checkBoxGroup.get(PButtonGroup.indexOf(btn)).getText().contains("紐約克")
								|| checkBoxGroup.get(PButtonGroup.indexOf(btn)).getText().contains("雪花") || checkBoxGroup.get(PButtonGroup.indexOf(btn)).getText().contains("牛小排")
								|| checkBoxGroup.get(PButtonGroup.indexOf(btn)).getText().contains("牛筋") || checkBoxGroup.get(PButtonGroup.indexOf(btn)).getText().contains("牛肉片")
								|| checkBoxGroup.get(PButtonGroup.indexOf(btn)).getText().contains("沙朗")) {
							beef++;
							System.out.println("beef number " + beef);
							
						}
					}

				}
			});
		}

		for (Button btn : MButtonGroup) {
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {

					if (Integer.parseInt(LabelGroup.get(MButtonGroup.indexOf(btn)).getText()) > 0) {
						LabelGroup.get(MButtonGroup.indexOf(btn)).setText(String
								.valueOf(Integer.parseInt(LabelGroup.get(MButtonGroup.indexOf(btn)).getText()) - 1));
						now_people--;
						//System.out.println("此單人數 " + num_people + " 已點客數" + now_people);
						if (checkBoxGroup.get(MButtonGroup.indexOf(btn)).getText().contains("牛") || checkBoxGroup.get(MButtonGroup.indexOf(btn)).getText().contains("菲力")
								|| checkBoxGroup.get(MButtonGroup.indexOf(btn)).getText().contains("肋眼") || checkBoxGroup.get(MButtonGroup.indexOf(btn)).getText().contains("紐約克")
								|| checkBoxGroup.get(MButtonGroup.indexOf(btn)).getText().contains("雪花") || checkBoxGroup.get(MButtonGroup.indexOf(btn)).getText().contains("牛小排")
								|| checkBoxGroup.get(MButtonGroup.indexOf(btn)).getText().contains("牛筋") || checkBoxGroup.get(MButtonGroup.indexOf(btn)).getText().contains("牛肉片")
								|| checkBoxGroup.get(MButtonGroup.indexOf(btn)).getText().contains("沙朗")) {
							beef--;
							System.out.println("beef number " + beef);
							
						}
						//if(money > 0 && Integer.parseInt(LabelGroup.get(MButtonGroup.indexOf(btn)).getText()) > 0)
						money -= PriceGroup.get(MButtonGroup.indexOf(btn));
						if(passing_menu.contains(checkBoxGroup.get(MButtonGroup.indexOf(btn)).getText()))
							passing_menu.remove(checkBoxGroup.get(MButtonGroup.indexOf(btn)).getText());
						if (Integer.parseInt(LabelGroup.get(MButtonGroup.indexOf(btn)).getText()) == 0)
							checkBoxGroup.get(MButtonGroup.indexOf(btn)).setSelected(false);
						System.out.println("此單人數 " + num_people + " 已點客數" + now_people + " 金額" + money);
					}

				}
			});
		}

	}

	private void assign_price() 
	{
		price_meal1 = 720; price_meal2 = 720; price_meal3 = 620; price_meal4 = 620; price_meal5 = 520; price_meal6 = 520;
		price_meal7 = 420; price_meal8 = 420; price_meal9 = 420; price_meal10 = 480; price_meal11 = 480; price_meal12 = 480; 
		price_meal13 = 399; price_meal14 = 200; price_meal15 = 200; price_meal16 = 200; price_meal17 = 230; price_meal18 = 230;
		price_meal19 = 230; price_meal20 = 170; price_meal21 = 170; price_meal22 = 170; price_meal23 = 350; price_meal24 = 350;
		price_meal25 = 300; price_meal26 = 280; price_meal27 = 260; price_meal28 = 140; price_meal29 = 180; price_meal30 = 180;
		price_meal31 = 180; price_meal32 = 150; price_meal33 = 150; price_meal34 = 120; price_meal35 = 120; price_meal36 = 120;
		price_meal37 = 150; price_meal38 = 180; price_meal39 = 180; price_meal40 = 180; price_meal41 = 180; price_meal42 = 180;
		price_meal43 = 180; price_meal44 = 150; price_meal45 = 150; price_meal46 = 180; price_meal47 = 180; price_meal48 = 150;
		price_meal49 = 490; price_meal50 = 450; price_meal51 = 350; price_meal52 = 300; price_meal53 = 280; price_meal54 = 200;
		price_meal55 = 0; price_meal56 = 250; price_meal57 = 200; 
		price_meal58 = 10; price_meal59 = 20; price_meal60 = 30; price_meal61 = 50; price_meal62 = 50; price_meal63 = 60;
		price_meal64 = 40; price_meal65 = 60; price_meal66 = 80; price_meal67 = 60; price_meal68 = 50;
		price_meal69 = 99; 
		price_meal70 = 100; price_meal71 = 150; price_meal72 = 99; price_meal73 = 99;
		//remove 羊小排 new 鮮綠時蔬 雙人豪華 單人豪華 鳳梨蝦球
		
	}

	private void menu_content_intialize() {
		num_people = 1;
		now_people = 0;

		checkBoxGroup.add(meal1);
		checkBoxGroup.add(meal2);
		checkBoxGroup.add(meal3);
		checkBoxGroup.add(meal4);
		checkBoxGroup.add(meal5);
		checkBoxGroup.add(meal6);
		checkBoxGroup.add(meal7);
		checkBoxGroup.add(meal8);
		checkBoxGroup.add(meal9);
		checkBoxGroup.add(meal10);
		checkBoxGroup.add(meal11);
		checkBoxGroup.add(meal12);
		checkBoxGroup.add(meal13);
		checkBoxGroup.add(meal14);
		checkBoxGroup.add(meal15);
		checkBoxGroup.add(meal16);
		checkBoxGroup.add(meal17);
		checkBoxGroup.add(meal18);
		checkBoxGroup.add(meal19);
		checkBoxGroup.add(meal20);
		checkBoxGroup.add(meal21);
		checkBoxGroup.add(meal22);
		checkBoxGroup.add(meal23);
		checkBoxGroup.add(meal24);
		checkBoxGroup.add(meal25);
		checkBoxGroup.add(meal26);
		checkBoxGroup.add(meal27);
		checkBoxGroup.add(meal28);
		checkBoxGroup.add(meal29);
		checkBoxGroup.add(meal30);
		checkBoxGroup.add(meal31);
		checkBoxGroup.add(meal32);
		checkBoxGroup.add(meal33);
		checkBoxGroup.add(meal34);
		checkBoxGroup.add(meal35);
		checkBoxGroup.add(meal36);
		checkBoxGroup.add(meal37);
		checkBoxGroup.add(meal38);
		checkBoxGroup.add(meal39);
		checkBoxGroup.add(meal40);
		checkBoxGroup.add(meal41);
		checkBoxGroup.add(meal42);
		checkBoxGroup.add(meal43);
		checkBoxGroup.add(meal44);
		checkBoxGroup.add(meal45);
		checkBoxGroup.add(meal46);
		checkBoxGroup.add(meal47);
		checkBoxGroup.add(meal48);
		checkBoxGroup.add(meal49);
		checkBoxGroup.add(meal50);
		checkBoxGroup.add(meal51);
		checkBoxGroup.add(meal52);
		checkBoxGroup.add(meal53);
		checkBoxGroup.add(meal54);
		checkBoxGroup.add(meal55);
		checkBoxGroup.add(meal56);
		checkBoxGroup.add(meal57);
		checkBoxGroup.add(meal58);
		checkBoxGroup.add(meal59);
		checkBoxGroup.add(meal60);
		checkBoxGroup.add(meal61);
		checkBoxGroup.add(meal62);
		checkBoxGroup.add(meal63);
		checkBoxGroup.add(meal64);
		checkBoxGroup.add(meal65);
		checkBoxGroup.add(meal66);
		checkBoxGroup.add(meal67);
		checkBoxGroup.add(meal68);
		checkBoxGroup.add(meal69);
		checkBoxGroup.add(meal70);
		checkBoxGroup.add(meal71);
		checkBoxGroup.add(meal72);
		checkBoxGroup.add(meal73);
		//checkBoxGroup.add(meal74);

		PButtonGroup.add(pbtn_meal1);
		PButtonGroup.add(pbtn_meal2);
		PButtonGroup.add(pbtn_meal3);
		PButtonGroup.add(pbtn_meal4);
		PButtonGroup.add(pbtn_meal5);
		PButtonGroup.add(pbtn_meal6);
		PButtonGroup.add(pbtn_meal7);
		PButtonGroup.add(pbtn_meal8);
		PButtonGroup.add(pbtn_meal9);
		PButtonGroup.add(pbtn_meal10);
		PButtonGroup.add(pbtn_meal11);
		PButtonGroup.add(pbtn_meal12);
		PButtonGroup.add(pbtn_meal13);
		PButtonGroup.add(pbtn_meal14);
		PButtonGroup.add(pbtn_meal15);
		PButtonGroup.add(pbtn_meal16);
		PButtonGroup.add(pbtn_meal17);
		PButtonGroup.add(pbtn_meal18);
		PButtonGroup.add(pbtn_meal19);
		PButtonGroup.add(pbtn_meal20);
		PButtonGroup.add(pbtn_meal21);
		PButtonGroup.add(pbtn_meal22);
		PButtonGroup.add(pbtn_meal23);
		PButtonGroup.add(pbtn_meal24);
		PButtonGroup.add(pbtn_meal25);
		PButtonGroup.add(pbtn_meal26);
		PButtonGroup.add(pbtn_meal27);
		PButtonGroup.add(pbtn_meal28);
		PButtonGroup.add(pbtn_meal29);
		PButtonGroup.add(pbtn_meal30);
		PButtonGroup.add(pbtn_meal31);
		PButtonGroup.add(pbtn_meal32);
		PButtonGroup.add(pbtn_meal33);
		PButtonGroup.add(pbtn_meal34);
		PButtonGroup.add(pbtn_meal35);
		PButtonGroup.add(pbtn_meal36);
		PButtonGroup.add(pbtn_meal37);
		PButtonGroup.add(pbtn_meal38);
		PButtonGroup.add(pbtn_meal39);
		PButtonGroup.add(pbtn_meal40);
		PButtonGroup.add(pbtn_meal41);
		PButtonGroup.add(pbtn_meal42);
		PButtonGroup.add(pbtn_meal43);
		PButtonGroup.add(pbtn_meal44);
		PButtonGroup.add(pbtn_meal45);
		PButtonGroup.add(pbtn_meal46);
		PButtonGroup.add(pbtn_meal47);
		PButtonGroup.add(pbtn_meal48);
		PButtonGroup.add(pbtn_meal49);
		PButtonGroup.add(pbtn_meal50);
		PButtonGroup.add(pbtn_meal51);
		PButtonGroup.add(pbtn_meal52);
		PButtonGroup.add(pbtn_meal53);
		PButtonGroup.add(pbtn_meal54);
		PButtonGroup.add(pbtn_meal55);
		PButtonGroup.add(pbtn_meal56);
		PButtonGroup.add(pbtn_meal57);
		PButtonGroup.add(pbtn_meal58);
		PButtonGroup.add(pbtn_meal59);
		PButtonGroup.add(pbtn_meal60);
		PButtonGroup.add(pbtn_meal61);
		PButtonGroup.add(pbtn_meal62);
		PButtonGroup.add(pbtn_meal63);
		PButtonGroup.add(pbtn_meal64);
		PButtonGroup.add(pbtn_meal65);
		PButtonGroup.add(pbtn_meal66);
		PButtonGroup.add(pbtn_meal67);
		PButtonGroup.add(pbtn_meal68);
		PButtonGroup.add(pbtn_meal69);
		PButtonGroup.add(pbtn_meal70);
		PButtonGroup.add(pbtn_meal71);
		PButtonGroup.add(pbtn_meal72);
		PButtonGroup.add(pbtn_meal73);
		//PButtonGroup.add(pbtn_meal74);   

		MButtonGroup.add(mbtn_meal1);
		MButtonGroup.add(mbtn_meal2);
		MButtonGroup.add(mbtn_meal3);
		MButtonGroup.add(mbtn_meal4);
		MButtonGroup.add(mbtn_meal5);
		MButtonGroup.add(mbtn_meal6);
		MButtonGroup.add(mbtn_meal7);
		MButtonGroup.add(mbtn_meal8);
		MButtonGroup.add(mbtn_meal9);
		MButtonGroup.add(mbtn_meal10);
		MButtonGroup.add(mbtn_meal11);
		MButtonGroup.add(mbtn_meal12);
		MButtonGroup.add(mbtn_meal13);
		MButtonGroup.add(mbtn_meal14);
		MButtonGroup.add(mbtn_meal15);
		MButtonGroup.add(mbtn_meal16);
		MButtonGroup.add(mbtn_meal17);
		MButtonGroup.add(mbtn_meal18);
		MButtonGroup.add(mbtn_meal19);
		MButtonGroup.add(mbtn_meal20);
		MButtonGroup.add(mbtn_meal21);
		MButtonGroup.add(mbtn_meal22);
		MButtonGroup.add(mbtn_meal23);
		MButtonGroup.add(mbtn_meal24);
		MButtonGroup.add(mbtn_meal25);
		MButtonGroup.add(mbtn_meal26);
		MButtonGroup.add(mbtn_meal27);
		MButtonGroup.add(mbtn_meal28);
		MButtonGroup.add(mbtn_meal29);
		MButtonGroup.add(mbtn_meal30);
		MButtonGroup.add(mbtn_meal31);
		MButtonGroup.add(mbtn_meal32);
		MButtonGroup.add(mbtn_meal33);
		MButtonGroup.add(mbtn_meal34);
		MButtonGroup.add(mbtn_meal35);
		MButtonGroup.add(mbtn_meal36);
		MButtonGroup.add(mbtn_meal37);
		MButtonGroup.add(mbtn_meal38);
		MButtonGroup.add(mbtn_meal39);
		MButtonGroup.add(mbtn_meal40);
		MButtonGroup.add(mbtn_meal41);
		MButtonGroup.add(mbtn_meal42);
		MButtonGroup.add(mbtn_meal43);
		MButtonGroup.add(mbtn_meal44);
		MButtonGroup.add(mbtn_meal45);
		MButtonGroup.add(mbtn_meal46);
		MButtonGroup.add(mbtn_meal47);
		MButtonGroup.add(mbtn_meal48);
		MButtonGroup.add(mbtn_meal49);
		MButtonGroup.add(mbtn_meal50);
		MButtonGroup.add(mbtn_meal51);
		MButtonGroup.add(mbtn_meal52);
		MButtonGroup.add(mbtn_meal53);
		MButtonGroup.add(mbtn_meal54);
		MButtonGroup.add(mbtn_meal55);
		MButtonGroup.add(mbtn_meal56);
		MButtonGroup.add(mbtn_meal57);
		MButtonGroup.add(mbtn_meal58);
		MButtonGroup.add(mbtn_meal59);
		MButtonGroup.add(mbtn_meal60);
		MButtonGroup.add(mbtn_meal61);
		MButtonGroup.add(mbtn_meal62);
		MButtonGroup.add(mbtn_meal63);
		MButtonGroup.add(mbtn_meal64);
		MButtonGroup.add(mbtn_meal65);
		MButtonGroup.add(mbtn_meal66);
		MButtonGroup.add(mbtn_meal67);
		MButtonGroup.add(mbtn_meal68);
		MButtonGroup.add(mbtn_meal69);
		MButtonGroup.add(mbtn_meal70);
		MButtonGroup.add(mbtn_meal71);
		MButtonGroup.add(mbtn_meal72);
		MButtonGroup.add(mbtn_meal73);
		//MButtonGroup.add(mbtn_meal74);

		LabelGroup.add(num_meal1);
		LabelGroup.add(num_meal2);
		LabelGroup.add(num_meal3);
		LabelGroup.add(num_meal4);
		LabelGroup.add(num_meal5);
		LabelGroup.add(num_meal6);
		LabelGroup.add(num_meal7);
		LabelGroup.add(num_meal8);
		LabelGroup.add(num_meal9);
		LabelGroup.add(num_meal10);
		LabelGroup.add(num_meal11);
		LabelGroup.add(num_meal12);
		LabelGroup.add(num_meal13);
		LabelGroup.add(num_meal14);
		LabelGroup.add(num_meal15);
		LabelGroup.add(num_meal16);
		LabelGroup.add(num_meal17);
		LabelGroup.add(num_meal18);
		LabelGroup.add(num_meal19);
		LabelGroup.add(num_meal20);
		LabelGroup.add(num_meal21);
		LabelGroup.add(num_meal22);
		LabelGroup.add(num_meal23);
		LabelGroup.add(num_meal24);
		LabelGroup.add(num_meal25);
		LabelGroup.add(num_meal26);
		LabelGroup.add(num_meal27);
		LabelGroup.add(num_meal28);
		LabelGroup.add(num_meal29);
		LabelGroup.add(num_meal30);
		LabelGroup.add(num_meal31);
		LabelGroup.add(num_meal32);
		LabelGroup.add(num_meal33);
		LabelGroup.add(num_meal34);
		LabelGroup.add(num_meal35);
		LabelGroup.add(num_meal36);
		LabelGroup.add(num_meal37);
		LabelGroup.add(num_meal38);
		LabelGroup.add(num_meal39);
		LabelGroup.add(num_meal40);
		LabelGroup.add(num_meal41);
		LabelGroup.add(num_meal42);
		LabelGroup.add(num_meal43);
		LabelGroup.add(num_meal44);
		LabelGroup.add(num_meal45);
		LabelGroup.add(num_meal46);
		LabelGroup.add(num_meal47);
		LabelGroup.add(num_meal48);
		LabelGroup.add(num_meal49);
		LabelGroup.add(num_meal50);
		LabelGroup.add(num_meal51);
		LabelGroup.add(num_meal52);
		LabelGroup.add(num_meal53);
		LabelGroup.add(num_meal54);
		LabelGroup.add(num_meal55);
		LabelGroup.add(num_meal56);
		LabelGroup.add(num_meal57);
		LabelGroup.add(num_meal58);
		LabelGroup.add(num_meal59);
		LabelGroup.add(num_meal60);
		LabelGroup.add(num_meal61);
		LabelGroup.add(num_meal62);
		LabelGroup.add(num_meal63);
		LabelGroup.add(num_meal64);
		LabelGroup.add(num_meal65);
		LabelGroup.add(num_meal66);
		LabelGroup.add(num_meal67);
		LabelGroup.add(num_meal68);
		LabelGroup.add(num_meal69);
		LabelGroup.add(num_meal70);
		LabelGroup.add(num_meal71);
		LabelGroup.add(num_meal72);
		LabelGroup.add(num_meal73);
		//LabelGroup.add(num_meal74);
		
		PriceGroup.add(price_meal1);
		PriceGroup.add(price_meal2);
		PriceGroup.add(price_meal3);
		PriceGroup.add(price_meal4);
		PriceGroup.add(price_meal5);
		PriceGroup.add(price_meal6);
		PriceGroup.add(price_meal7);
		PriceGroup.add(price_meal8);
		PriceGroup.add(price_meal9);
		PriceGroup.add(price_meal10);
		PriceGroup.add(price_meal11);
		PriceGroup.add(price_meal12);
		PriceGroup.add(price_meal13);
		PriceGroup.add(price_meal14);
		PriceGroup.add(price_meal15);
		PriceGroup.add(price_meal16);
		PriceGroup.add(price_meal17);
		PriceGroup.add(price_meal18);
		PriceGroup.add(price_meal19);
		PriceGroup.add(price_meal20);
		PriceGroup.add(price_meal21);
		PriceGroup.add(price_meal22);
		PriceGroup.add(price_meal23);
		PriceGroup.add(price_meal24);
		PriceGroup.add(price_meal25);
		PriceGroup.add(price_meal26);
		PriceGroup.add(price_meal27);
		PriceGroup.add(price_meal28);
		PriceGroup.add(price_meal29);
		PriceGroup.add(price_meal30);
		PriceGroup.add(price_meal31);
		PriceGroup.add(price_meal32);
		PriceGroup.add(price_meal33);
		PriceGroup.add(price_meal34);
		PriceGroup.add(price_meal35);
		PriceGroup.add(price_meal36);
		PriceGroup.add(price_meal37);
		PriceGroup.add(price_meal38);
		PriceGroup.add(price_meal39);
		PriceGroup.add(price_meal40);
		PriceGroup.add(price_meal41);
		PriceGroup.add(price_meal42);
		PriceGroup.add(price_meal43);
		PriceGroup.add(price_meal44);
		PriceGroup.add(price_meal45);
		PriceGroup.add(price_meal46);
		PriceGroup.add(price_meal47);
		PriceGroup.add(price_meal48);
		PriceGroup.add(price_meal49);
		PriceGroup.add(price_meal50);
		PriceGroup.add(price_meal51);
		PriceGroup.add(price_meal52);
		PriceGroup.add(price_meal53);
		PriceGroup.add(price_meal54);
		PriceGroup.add(price_meal55);
		PriceGroup.add(price_meal56);
		PriceGroup.add(price_meal57);
		PriceGroup.add(price_meal58);
		PriceGroup.add(price_meal59);
		PriceGroup.add(price_meal60);
		PriceGroup.add(price_meal61);
		PriceGroup.add(price_meal62);
		PriceGroup.add(price_meal63);
		PriceGroup.add(price_meal64);
		PriceGroup.add(price_meal65);
		PriceGroup.add(price_meal66);
		PriceGroup.add(price_meal67);
		PriceGroup.add(price_meal68);
		PriceGroup.add(price_meal69);
		PriceGroup.add(price_meal70);
		PriceGroup.add(price_meal71);
		PriceGroup.add(price_meal72);
		PriceGroup.add(price_meal73);
		//PriceGroup.add(price_meal74);

	}

	@FXML
	protected void PreviousPageButtonAction(ActionEvent event) throws IOException {
		// def fxml loader
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));

		// ref fxml to stage
		stage = MainScene.stage_tmp;
		scene = new Scene(mainstage, 1024, 720);

		// change scene to main scene
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	protected void NextPageButtonAction(ActionEvent event) throws IOException {
		pause = true;

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ListStage.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		ListController controller = fxmlLoader.<ListController>getController();
		controller.setMoney(money);
		controller.setPeople(num_people);
		controller.setType(Consumption_type);
		controller.setMenuList(passing_menu);
		scene = new Scene(root, 800, 600);
		//Pop_Stage = MainScene.stage_tmp;
		
		// change scene to main scene
		Pop_Stage = new Stage();
		Pop_Stage.setScene(scene);
		Pop_Stage.initModality(Modality.APPLICATION_MODAL);
		Pop_Stage.setTitle("清單");
		Pop_Stage.showAndWait();
		
		//Pop_Stage.setOnHide(event -> Platform.exit());
		//stage.show();
	}

	public void setClosePop(boolean b) {

		Pop_Stage.close();
		
	}
	
}
