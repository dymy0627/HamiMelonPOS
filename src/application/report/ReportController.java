package application.report;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

import application.MainScene;
import db.MySqlConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class ReportController implements Initializable{
	
	@FXML
	private ToggleButton DailyButton,MonthlyButton;
	private ToggleGroup mTypeGroup;
	private String mReportType;
	
	@FXML
	private ProgressIndicator myProgressIndicator;
	@FXML 
	private Pane ReportContainer;
	
	MySqlConnection mySqlConnection = new MySqlConnection();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		myProgressIndicator.setVisible(false);
		mTypeGroup = new ToggleGroup();
		mTypeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_select, Toggle new_select) {

				if (new_select != null) {
					((ToggleButton) new_select).setSelected(true);
					System.out.println("new_select = " + new_select.toString());
				} else {
					((ToggleButton) old_select).setSelected(true);
					System.out.println("new_select = " + old_select.toString());
					return;
				}

				if (old_select != null) {
					((ToggleButton) old_select).setSelected(false);
					System.out.println("old_select = " + old_select.toString());
				} else {
					System.out.println("old_select == null");
				}

				if (new_select == DailyButton) {
					mReportType = "日報";
					
					try {
						FXMLLoader DailyViewLoad = new FXMLLoader(
								  getClass().getResource(
										    "/fxml/DailyView.fxml"
										  )
						);
						ReportContainer.getChildren().clear();
						ReportContainer.getChildren().add(DailyViewLoad.load());
						DailyViewLoad = null;

					} catch (IOException e) {
						e.printStackTrace();
					  }
				} else if (new_select == MonthlyButton) {
					mReportType = "月報";
					try {
						FXMLLoader MonthlyViewLoad = new FXMLLoader(
								  getClass().getResource(
										    "/fxml/MonthlyView.fxml"
										  )
						);
						ReportContainer.getChildren().clear();
						ReportContainer.getChildren().add(MonthlyViewLoad.load());
						MonthlyViewLoad = null;
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				} 

			}
		});
		
		DailyButton.setToggleGroup(mTypeGroup);
		MonthlyButton.setToggleGroup(mTypeGroup);
		MonthlyButton.setSelected(true);
		
	}
	
	@FXML
	protected void BackButtonAction(ActionEvent event) throws IOException {
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));

		MainScene.changeScene(mainstage);
	}
}
