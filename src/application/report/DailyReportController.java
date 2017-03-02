package application.report;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.GenerateDailyTask;
import application.MainScene;
import db.MySqlConnection;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class DailyReportController implements Initializable {

	@FXML
	private Label daily_sales, daily_lunch_sales, daily_dinner_sales;
	@FXML
	private Label daily_inside_sales, daily_outside_sales, daily_deliver_sales, daily_total_num, daily_avg_sales;
	@FXML
	private Label daily_double_num, daily_special_num, daily_wind_rain_num, daily_luxury_num;

	@FXML
	private Label label_back_door;

	@FXML
	private ProgressIndicator myProgressIndicator;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getData();
	}

	private void getData() {
		myProgressIndicator.setVisible(false);
		new Thread(new Task<Boolean>() {
			private DailyReportBean day;

			@Override
			protected Boolean call() throws Exception {
				myProgressIndicator.setVisible(true);

				MySqlConnection mySqlConnection = new MySqlConnection();
				mySqlConnection.connectSql();
				day = mySqlConnection.getDailyReport();
				mySqlConnection.disconnectSql();
				return true;
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				myProgressIndicator.setVisible(false);
				System.out.println("Load from DB Done!");

				// L_Average_consumption int(11)
				// D_Average_consumption int(11)

				daily_sales.setText(String.valueOf(day.getDailySales()));
				daily_lunch_sales.setText(String.valueOf(day.getLunchSales()));
				daily_dinner_sales.setText(String.valueOf(day.getDinnerSales()));

				daily_inside_sales.setText(String.valueOf(day.getInsideSales()));
				daily_outside_sales.setText(String.valueOf(day.getOutsideSales()));
				daily_deliver_sales.setText(String.valueOf(day.getDeliverSales()));

				daily_total_num.setText(String.valueOf(day.getTotalNum()));
				daily_avg_sales.setText(String.valueOf(day.getAvgSales()));

				daily_double_num.setText(String.valueOf(day.getDoubleNum()));
				daily_special_num.setText(String.valueOf(day.getSpecialNum()));
				daily_wind_rain_num.setText(String.valueOf(day.getWindAndRainNum()));
				daily_luxury_num.setText(String.valueOf(day.getLuxuryNum()));
			}

			@Override
			protected void failed() {
				super.failed();
				myProgressIndicator.setVisible(false);
				System.out.println("Load from DB Failed!");
			}
		}).start();
	}

	private int hackClick = 0;

	@FXML
	protected void BackDoorAction() throws IOException {
		hackClick++;
		if (hackClick > 6) {
			System.out.println("BD不說");
			new GenerateDailyTask().run();
			getData();
			hackClick = 0;
		}
	}

	@FXML
	protected void BackButtonAction(ActionEvent event) throws IOException {
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));

		MainScene.changeScene(mainstage);
	}

}
