package application.report;

import java.net.URL;
import java.util.ResourceBundle;

import application.GenerateDailyTask;
import db.MySqlConnection;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;

public class ReportController implements Initializable{
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		getData();
		
	}
	
	private void getData() {
		
	}

}
