package application.report;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.MainScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DailyReportController implements Initializable {

	@FXML
	private Label daily_sales, daily_lunch_sales, daily_dinner_sales;
	@FXML
	private Label daily_inside_sales, daily_outside_sales, daily_deliver_sales, daily_total_num, daily_avg_sales;
	@FXML
	private Label daily_double_num, daily_special_num, daily_wind_rain_num, daily_luxury_num;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		int dailyLunchSales = 1699;
		int dailyDinnerSales = 2699;
		daily_lunch_sales.setText(String.valueOf(dailyLunchSales));
		daily_dinner_sales.setText(String.valueOf(dailyDinnerSales));

		int dailyInsideSales = dailyLunchSales + dailyDinnerSales;
		daily_inside_sales.setText(String.valueOf(dailyInsideSales));

		int dailyOutsideSales = 999;
		int dailyDeliverSales = 999;
		daily_outside_sales.setText(String.valueOf(dailyOutsideSales));
		daily_deliver_sales.setText(String.valueOf(dailyDeliverSales));

		int dailyTotalNum = 5;
		daily_total_num.setText(String.valueOf(dailyTotalNum));

		int avgSales = dailyInsideSales / 2;
		daily_avg_sales.setText(String.valueOf(avgSales));

		int dailySales = dailyInsideSales + dailyOutsideSales + dailyDeliverSales;
		daily_sales.setText(String.valueOf(dailySales));

		daily_double_num.setText("1");
		daily_special_num.setText("1");
		daily_wind_rain_num.setText("1");
		daily_luxury_num.setText("1");

	}

	@FXML
	protected void BackButtonAction(ActionEvent event) throws IOException {
		Parent mainstage = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));
		Stage stage = MainScene.stage_tmp;
		Scene scene = new Scene(mainstage, 1024, 720);
		stage.setScene(scene);
		stage.show();
	}

}
