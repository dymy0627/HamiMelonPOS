package application.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.ResourceBundle;

import application.MainScene;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportController implements Initializable {

	@FXML
	private ToggleButton DailyButton, MonthlyButton;
	@FXML
	private ProgressIndicator myProgressIndicator;
	@FXML
	private Pane ReportContainer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		myProgressIndicator.setVisible(false);

		ToggleGroup typeGroup = new ToggleGroup();
		typeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
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
					try {
						FXMLLoader DailyViewLoad = new FXMLLoader(getClass().getResource("/fxml/DailyView.fxml"));
						ReportContainer.getChildren().clear();
						ReportContainer.getChildren().add(DailyViewLoad.load());
						DailyViewLoad = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (new_select == MonthlyButton) {
					try {
						FXMLLoader MonthlyViewLoad = new FXMLLoader(getClass().getResource("/fxml/MonthlyView.fxml"));
						ReportContainer.getChildren().clear();
						ReportContainer.getChildren().add(MonthlyViewLoad.load());
						MonthlyViewLoad = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		});
		DailyButton.setToggleGroup(typeGroup);
		MonthlyButton.setToggleGroup(typeGroup);
		DailyButton.setSelected(true);
	}

	@FXML
	protected void BackButtonAction(ActionEvent event) throws IOException {
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));
		MainScene.changeScene(mainstage);
	}
	
	@FXML
	protected void ExportButtonAction(ActionEvent event) throws IOException {
		Calendar T_Calendar = Calendar.getInstance();
		Alert ExCSV_Alert = new Alert(AlertType.INFORMATION);
		String systemtime = LocalDateTime.now().toString();
		String[] timeArray = systemtime.split("-");
		String time = timeArray[0]+"-"+timeArray[1];
		
		//export csv file 
		PrintWriter Ex_CSV = new PrintWriter(new File(time +"報表.csv"));
		StringBuilder CSV_Builder = new StringBuilder();
		
		CSV_Builder.append("");
		CSV_Builder.append(',');
		CSV_Builder.append("午餐");
		CSV_Builder.append(',');
		CSV_Builder.append("晚餐");
		CSV_Builder.append(',');
		CSV_Builder.append("日總");
		CSV_Builder.append('\n');
		//Report Format: 日期,午餐營業額,晚餐營業額,日總營業額
		for(int CursorDay = 1 ; CursorDay <= 31 ; CursorDay++){
			
			T_Calendar.set(Integer.parseInt(timeArray[0]),Integer.parseInt(timeArray[1]),CursorDay);
        	int day = T_Calendar.getTime().getDay()==0?7:T_Calendar.getTime().getDay();
        	int lunch_total = 66;
        	int dinner_total = 66;
        	int day_total = lunch_total + dinner_total;
			
        	CSV_Builder.append(timeArray[0]+"-"+timeArray[1]+"-"+String.valueOf(CursorDay));
        	CSV_Builder.append(',');
        	CSV_Builder.append(String.valueOf(lunch_total));
        	CSV_Builder.append(',');
        	CSV_Builder.append(String.valueOf(dinner_total));
        	CSV_Builder.append(',');
        	CSV_Builder.append(String.valueOf(day_total));
        	
        	CSV_Builder.append("\n");	
		}
		
        Ex_CSV.write(CSV_Builder.toString());
        Ex_CSV.close();
        
        ExCSV_Alert.setTitle("通知");
        ExCSV_Alert.setHeaderText(null);
        ExCSV_Alert.setContentText("報表輸出成功");
        ExCSV_Alert.showAndWait();
        
	}
}
