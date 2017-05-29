package application.report;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

import application.GenerateDailyTask;
import application.MainScene;
import db.MySqlConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class DailyViewController implements Initializable{
	
	//FXML Object Def.
	@FXML
	private Label daily_sales, daily_lunch_sales, daily_dinner_sales;
	@FXML
	private Label daily_inside_sales, daily_outside_sales, daily_deliver_sales, daily_total_num, daily_avg_sales;
	@FXML
	private Label daily_double_num, daily_special_num, daily_wind_rain_num, daily_luxury_num;
	
	@FXML
	private ComboBox<String> weather_combobox;
	private String daily_weather = "";
	
	@FXML
	private Pane pieChartPane;

	@FXML
	private RadioButton turnoverRadioButton;
	@FXML
	private RadioButton typeRadioButton;
	@FXML
	private RadioButton setRadioButton;
	@FXML
	private Button CheckButton;
	
	@FXML
	protected void CheckButtonAction(ActionEvent event) throws IOException {
		mySqlConnection.updateDailyWeather(daily_weather);
		//Report Updated Sql Connect Close
		mySqlConnection.disconnectSql();
	}
	
	//Controll Object Def.
	private DailyReportBean dayBean;
	private MySqlConnection mySqlConnection;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Connect Sql
		mySqlConnection = new MySqlConnection();
		mySqlConnection.connectSql();

		
		//DailyReportContent Controll
		DailyWeatherReport();
		
		DailyBusinessTable();
		
		DailyBusinessGraphic();
		turnoverRadioButton.setSelected(true);
	}

	private void DailyWeatherReport() {
		
		CheckButton.setDisable(true);
		weather_combobox.getItems().setAll("晴天", "雨天");
		weather_combobox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> selected, String oldSelected, String newSelected) {
				daily_weather = newSelected;
				System.out.println("weatherComboBox newSelected = " + daily_weather);
				CheckButton.setDisable(false);
			}
		});
		
	}
	
	private void DailyBusinessTable() {
		
		dayBean = mySqlConnection.getDailyReport();
		daily_sales.setText(String.valueOf(dayBean.getDailyTurnover()));
		daily_lunch_sales.setText(String.valueOf(dayBean.getLunchTurnover()));
		daily_dinner_sales.setText(String.valueOf(dayBean.getDinnerTurnover()));

		daily_inside_sales.setText(String.valueOf(dayBean.getInsideSales()));
		daily_outside_sales.setText(String.valueOf(dayBean.getOutsideSales()));
		daily_deliver_sales.setText(String.valueOf(dayBean.getDeliverSales()));

		daily_total_num.setText(String.valueOf(dayBean.getTotalNum()));
		daily_avg_sales.setText(String.valueOf(dayBean.getAvgSales()));

		daily_double_num.setText(String.valueOf(dayBean.getDoubleNum()));
		daily_special_num.setText(String.valueOf(dayBean.getSpecialNum()));
		daily_wind_rain_num.setText(String.valueOf(dayBean.getWindAndRainNum()));
		daily_luxury_num.setText(String.valueOf(dayBean.getLuxuryNum()));

	}

	private void DailyBusinessGraphic() {
		
		final ToggleGroup group = new ToggleGroup();
		turnoverRadioButton.setToggleGroup(group);
		turnoverRadioButton.setUserData("1");
		typeRadioButton.setToggleGroup(group);
		typeRadioButton.setUserData("2");
		setRadioButton.setToggleGroup(group);
		setRadioButton.setUserData("3");
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {
					if (group.getSelectedToggle().getUserData().toString().equals("1")) {
						creatTurnOverPieChart();
					} else if (group.getSelectedToggle().getUserData().toString().equals("2")) {
						creatTypePieChart();
					} else if (group.getSelectedToggle().getUserData().toString().equals("3")) {
						creatSetPieChart();
					}
				}
			}

			private void creatTurnOverPieChart() {
				
				pieChartPane.getChildren().clear();

				final LabelPieChart chart = new LabelPieChart();
				addPieChartData(chart, "午餐", dayBean.getLunchTurnover());
				addPieChartData(chart, "晚餐", dayBean.getDinnerTurnover());
				chart.setTitle("營業額時段比");

				pieChartPane.getChildren().add(chart);
			}
			
			private void creatTypePieChart() {
				
				pieChartPane.getChildren().clear();

				final LabelPieChart chart = new LabelPieChart();
				addPieChartData(chart, "內用", dayBean.getInsideSales());
				addPieChartData(chart, "外帶", dayBean.getOutsideSales());
				addPieChartData(chart, "外送", dayBean.getDeliverSales());
				chart.setTitle("用餐型態比");

				pieChartPane.getChildren().add(chart);
			}
			
			private void creatSetPieChart() {
				
				pieChartPane.getChildren().clear();

				final LabelPieChart chart = new LabelPieChart();
				addPieChartData(chart, "雙人", dayBean.getDoubleNum());
				addPieChartData(chart, "特餐", dayBean.getSpecialNum());
				addPieChartData(chart, "風雨", dayBean.getWindAndRainNum());
				chart.setTitle("套餐比");

				pieChartPane.getChildren().add(chart);
			}
			
			public void addPieChartData(LabelPieChart pChart, String name, double value) {
				final Data data = new Data(name, value);
				pChart.getData().add(data);
			}
		});		
	}
}
